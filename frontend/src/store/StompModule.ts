import {
  VuexModule, Module, Action, Mutation,
} from 'vuex-class-modules';
import { Client, IFrame } from '@stomp/stompjs';
import StompFutureSubscription from '../model/StompFutureSubscription';
import store from './store';

@Module({ generateMutationSetters: true })
class StompModule extends VuexModule {
  subscriptions: StompFutureSubscription[] = [];

  connected = false;

  subscribed = false;

  loading = false;

  stompClient = new Client({
    brokerURL: 'ws://127.0.0.1:9090/stomp', // spring-boot-backend (local)
    // brokerURL: 'ws://35.158.11.245:9090/stomp', // spring-boot-backend-service (aws)
    // brokerURL: 'ws://localhost:61614/stomp', // node-backend
    connectHeaders: {
      Authorization: `Bearer ${localStorage.getItem('id_token')}`, // 'Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik1EWkdOVEJETVVReFJERTNRVEpCUlVVME56VXpORU5HTWprMU9URTRPRUUxTVRZeU9VVkJRUSJ9.eyJnaXZlbl9uYW1lIjoiSm9uYXMiLCJmYW1pbHlfbmFtZSI6IkVpY2hlciIsIm5pY2tuYW1lIjoiam9uYXMubWF4aW1pbGlhbi5laWNoZXIiLCJuYW1lIjoiSm9uYXMgRWljaGVyIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hLS9BT2gxNEdnd2RsRUtsMUJ5Nm5QVldfRUQ0X3FodldCWlRZOTlPMFp5ckU1NnJnIiwibG9jYWxlIjoiZW4iLCJ1cGRhdGVkX2F0IjoiMjAyMC0wNC0xNFQxNTo1NTo0NS4yNjhaIiwiaXNzIjoiaHR0cHM6Ly9kZXYtM2xudi12cnIuZXUuYXV0aDAuY29tLyIsInN1YiI6Imdvb2dsZS1vYXV0aDJ8MTExMzEwMDU2NzM2NjMyODU1NzcyIiwiYXVkIjoiOXdRRGZsckhHY3VqNUt1THZqMktNbmRhdG16N2JwTGMiLCJpYXQiOjE1ODY4Nzk3NDUsImV4cCI6MTU4NjkxNTc0NSwiYXRfaGFzaCI6IlduSlNrZ29RM3pMTTdpTXJhUFRUbHciLCJub25jZSI6IjEyMzQ1Nzg5In0.i_3wbvtYvQ1lFp-un4QGnn_Cexw1ErSJ_O0Gi1fMuWc2kdSy1Z8pf_YazPO0ZnCMC_XdTypwt1eou0nazmYSwjf8-enUQ60VHejIPzb6-YV2AhgdWdsoxVR09T9N2Ry98-7wWO3YS3D8pA8cjH-cougU_f6rcGNYy6nhQqspq_JAC6TwaQ_DJ51Jjm8EyTuFX3fRdT2KELdzTFpghMf8y8Mna-A0jwKZaX8HBz3MkFve_MYsy8gCJ4ap-PW8zjgfQcWZMNRaGz4atsWibzIUIBSMfXzJF0rP5Fy6e8h1ch47NpK7ni2sCxskBn0F58b6Q43w-VX_5aLTwyzapg6vP',
    },
    debug(str) {
      console.log(str);
    },
    reconnectDelay: 0, // 0 = disabled
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  @Mutation
  setDisconnectHandler() {
    this.stompClient.onWebSocketClose = () => {
      this.loading = false;
      this.connected = false;
      this.subscribed = false;
    };
  }

  @Mutation
  setConnectHandler() {
    this.stompClient.onConnect = (receipt: IFrame) => {
      this.connected = true;
      this.loading = false;
    };
  }

  @Action
  connect() {
    this.setConnectHandler();
    this.setDisconnectHandler();
    this.stompClient.connectHeaders = {
      Authorization: `Bearer ${localStorage.getItem('id_token')}`,
    };
    this.stompClient.activate();
  }

  @Action
  disconnect() {
    this.stompClient.deactivate();
  }

  publish(destination: string, body: any) {
    this.stompClient.publish({ destination, body: JSON.stringify(body) });
  }

  @Action
  subscribe(subscription: StompFutureSubscription) {
    if (this.stompClient.connected) {
      console.log('client is connected, subscribing now');
      this.stompClient.subscribe(subscription.destination, subscription.callback);
    } else {
      console.log('client not yet connected, adding subscription to array');
      this.subscriptions.push(subscription);
    }
  }
}

const stompModule = new StompModule({ store, name: 'stomp' });
export default stompModule;

import {
  VuexModule, Module, Action, Mutation,
} from 'vuex-class-modules';
import { Client, IFrame } from '@stomp/stompjs';
import ChatMessage from '@/model/ChatMessage';
import store from './store';

@Module({ generateMutationSetters: true })
class PokerModule extends VuexModule {
  players: any = [];

  messages: any[] = [];

  connected = false;

  subscribed = false;

  loading = false;

  chatInputMessage = '';

  player = { name: `Player-${Math.floor(Math.random() * 1000)}`, id: Math.floor(Math.random() * 100000000) };

  // disconnectHandler = () => {
  //   console.log('disconnect handler');
  //   this.loading = false;
  //   this.connected = false;
  //   this.subscribed = false;
  //   console.log('subscribed = ', this.subscribed);
  //   this.addMessage({ user: 'Server', text: 'Disconnected' });
  // }

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

  // @Mutation
  // setClientState(state: any) {
  //   this.connected = state.connected?state.connected:this.connected;
  //   this.loading = state.loading?state.loading:this.loading;
  //   this.subscribed = state.subscribed?state.subscribed:this.subscribed;
  // }

  @Mutation
  setDisconnectHandler() {
    this.stompClient.onWebSocketClose = () => {
      this.loading = false;
      this.connected = false;
      this.subscribed = false;
      this.messages.push({ user: 'Server', text: 'Disconnected' });
      if (this.messages.length > 10) this.messages.shift();
      // this.addMessage({ user: 'Server', text: 'Disconnected' });
    };
  }

  @Mutation
  setConnectHandler() {
    this.stompClient.onConnect = (receipt: IFrame) => {
      this.connected = true;
      this.messages.push({ user: 'Server', text: 'Connected to the Server.' });
      if (this.messages.length > 10) this.messages.shift();
      // this.addMessage({ user: 'Server', text: 'Connected to the Server.' });
      this.loading = false;
      // do this on the backend, together with user disconnected: this.publish('/chat',  { user: 'Server', text: `User ${this.player.name} connected to the server.` })
    };
  }

  @Action
  connect() {
    this.setConnectHandler();
    this.setDisconnectHandler();
    this.stompClient.activate();
  }

  @Action
  disconnect() {
    this.stompClient.deactivate();
  }

  @Action
  sendChatMessage() {
    this.publish('/chat', { user: this.player.name, text: this.chatInputMessage, timestamp: Date.now() });
    this.chatInputMessage = '';
  }

  publish(destination: string, body: any) {
    this.stompClient.publish({ destination, body: JSON.stringify(body) });
  }

  @Action
  changeName() {
    this.stompClient.publish({ destination: '/app/lobby/player/changed', body: JSON.stringify(this.player) });
  }


  @Mutation
  addMessage(message?: ChatMessage) {
    if (!message) this.messages.push({ user: this.player.name, text: this.chatInputMessage });
    else { this.messages.push(message); }
    if (this.messages.length > 10) this.messages.shift();
  }

  @Action
  subscribe() {
    this.stompClient.subscribe('/lobby/players', (message) => {
      console.log(message);
      this.players = JSON.parse(message.body);
    });
    this.stompClient.subscribe('/chat', (message) => this.addMessage(JSON.parse(message.body)));
    this.stompClient.subscribe('/lobby/tables', (message) => console.log(message));

    this.addMessage({ user: 'Lobby', text: `Welcome to the Lobby, ${this.player.name}!` });

    this.stompClient.publish({ destination: '/app/lobby/player/new', body: JSON.stringify(this.player) });

    window.document.addEventListener('beforeunload', () => {
      console.log('beforeDestroy called');
      this.stompClient.publish({ destination: '/app/lobby/player/deleted', body: JSON.stringify(this.player) });
    });

    this.subscribed = true;

    // trying the node broker
    // this.stompClient.subscribe('/*', (x) => console.log(x));
    // this.stompClient.subscribe('/test', (x) => console.log(x));

    // this works with the spring boot broker
    this.stompClient.subscribe('/join/table', (message) => console.log(message));
    this.stompClient.subscribe('/user/queue/error', (message) => console.log(message));
    this.stompClient.subscribe('/user/queue/karten', (message) => console.log(message));
    this.stompClient.subscribe('/user/queue/cards', (message) => console.log(message));

    // old testing stuff
    // this.stompClient.subscribe('/topic/greetings', (message) => console.log(message));
    // this.stompClient.subscribe('/user/queue/humba', (message) => console.log(message));
    // this.stompClient.subscribe('/user/jonas/humba', (message) => console.log(message));
    // this.stompClient.subscribe('/user/queue/karten', (message) => console.log(message));
    // this.stompClient.subscribe('/user/jonas/karten', (message) => console.log(message));
  }
}

const pokerModule = new PokerModule({ store, name: 'poker' });
export default pokerModule;

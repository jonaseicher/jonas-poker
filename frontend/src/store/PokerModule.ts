import {
  VuexModule, Module, Action, Mutation,
} from 'vuex-class-modules';
import { Client, IFrame } from '@stomp/stompjs';
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
  //   this.messages.push({ user: 'Server', text: 'Disconnected' });
  // }

  stompClient = new Client({
    // brokerURL: 'ws://127.0.0.1:9090/stomp', // spring-boot-backend (local)
    brokerURL: 'ws://35.158.11.245:9090/stomp', // spring-boot-backend-service (aws)
    // brokerURL: 'ws://localhost:61614/stomp', // node-backend
    connectHeaders: {
      sessionId: 'really',
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
    };
  }

  @Mutation
  setConnectHandler() {
    this.stompClient.onConnect = (receipt: IFrame) => {
      this.connected = true;
      this.messages.push({ user: 'Server', text: 'Connected to the Server.' });
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
    this.publish('/chat', { user: this.player.name, text: this.chatInputMessage });
    this.chatInputMessage = '';
  }

  publish(destination: string, body: any) {
    this.stompClient.publish({ destination, body: JSON.stringify(body) });
  }

  @Action
  changeName() {
    this.stompClient.publish({ destination: '/app/lobby/player/changed', body: JSON.stringify(this.player) });
  }

  @Action
  subscribe() {
    this.stompClient.subscribe('/lobby/players', (message) => {
      console.log(message);
      this.players = JSON.parse(message.body);
    });
    this.stompClient.subscribe('/chat', (message) => this.messages.push(JSON.parse(message.body)));
    this.stompClient.subscribe('/lobby/tables', (message) => console.log(message));

    this.messages.push({ user: 'Lobby', text: `Welcome to the Lobby, ${this.player.name}!` });

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
    // this.stompClient.subscribe('/join/table', (message) => console.log(message));
    // this.stompClient.subscribe('/user/queue/error', (message) => console.log(message));
    // this.stompClient.subscribe('/user/queue/karten', (message) => {
    //   console.log('SOMETHING');
    //   console.log(message);
    // });

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

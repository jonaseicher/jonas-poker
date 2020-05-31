import {
  VuexModule, Module, Action, Mutation,
} from 'vuex-class-modules';
import ChatMessage from '@/model/ChatMessage';
import { IMessage } from '@stomp/stompjs';
import stompModule from './StompModule';
import store from './store';

@Module({ generateMutationSetters: true })
class PokerModule extends VuexModule {
  players: any = [];

  messages: any[] = [];

  subscribed = false;

  pokerTable: any = {};

  chatInputMessage = '';

  player = { name: `Player-${Math.floor(Math.random() * 1000)}`, id: Math.floor(Math.random() * 100000000) };


  @Action
  sendChatMessage() {
    stompModule.publish('/chat', { user: this.player.name, text: this.chatInputMessage, timestamp: Date.now() });
    this.chatInputMessage = '';
  }

  @Action
  changeName() {
    stompModule.publish('/app/lobby/player/changed', JSON.stringify(this.player));
  }

  @Mutation
  addMessage(message?: ChatMessage) {
    if (!message) this.messages.push({ user: this.player.name, text: this.chatInputMessage });
    else { this.messages.push(message); }
    if (this.messages.length > 10) this.messages.shift();
  }

  @Action
  subscribe() {
    stompModule.subscribe({
      destination: '/lobby/players',
      callback: (message) => {
        console.log(message);
        this.players = JSON.parse(message.body);
      },
    });
    stompModule.subscribe({ destination: '/chat', callback: (message) => this.addMessage(JSON.parse(message.body)) });
    stompModule.subscribe({ destination: '/lobby/tables', callback: (message) => console.log(message) });

    this.addMessage({ user: 'Lobby', text: `Welcome to the Lobby, ${this.player.name}!` });

    stompModule.publish('/app/lobby/player/new', JSON.stringify(this.player));

    window.document.addEventListener('beforeunload', () => {
      console.log('beforeDestroy called');
      stompModule.publish('/app/lobby/player/deleted', JSON.stringify(this.player));
    });

    this.subscribed = true;

    // trying the node broker
    // stompModule.subscribe('/*', (x) => console.log(x));
    // stompModule.subscribe('/test', (x) => console.log(x));

    // this works with the spring boot broker
    stompModule.subscribe({ destination: '/join/table', callback: this.joinTable });
    stompModule.subscribe({ destination: '/user/queue/error', callback: (message) => console.log(message) });
    stompModule.subscribe({ destination: '/user/queue/karten', callback: (message) => console.log(message) });
    stompModule.subscribe({ destination: '/user/queue/cards', callback: (message) => console.log(message) });
  }

  joinTable(message: IMessage) {
    this.pokerTable = message.body;
    console.log('pokerTable', this.pokerTable);
  }
}

const pokerModule = new PokerModule({ store, name: 'poker' });
export default pokerModule;

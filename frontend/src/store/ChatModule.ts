import {
  VuexModule, Module, Action, Mutation,
} from 'vuex-class-modules';
import ChatMessage from '@/model/ChatMessage';
import stompModule from './StompModule';
import store from './store';

@Module({ generateMutationSetters: true })
class ChatModule extends VuexModule {
  players: any = [];

  messages: any[] = [];

  subscribed = false;

  chatInputMessage = '';

  // eslint-disable-next-line no-underscore-dangle
  player = { name: stompModule.idToken.name, id: stompModule.idToken.__raw }

  @Action
  sendChatMessage() {
    stompModule.publish('/chat', { user: stompModule.idToken.name, text: this.chatInputMessage, timestamp: Date.now() });
    this.chatInputMessage = '';
  }

  @Action
  changeName() {
    stompModule.publish('/app/lobby/player/changed', this.player);
  }

  @Mutation
  addMessage(message?: ChatMessage) {
    if (!message) this.messages.push({ user: stompModule.idToken.name, text: this.chatInputMessage });
    else { this.messages.push(message); }
    if (this.messages.length > 10) this.messages.shift();
  }

  @Action
  subscribe() {
    stompModule.subscribe({
      destination: '/lobby/players',
      callback: (message) => {
        console.log('/lobby/players', message);
        this.players = JSON.parse(message.body);
      },
    });
    stompModule.subscribe({ destination: '/chat', callback: (message) => this.addMessage(JSON.parse(message.body)) });
    stompModule.subscribe({ destination: '/lobby/tables', callback: (message) => console.log(message) });

    this.addMessage({ user: 'Lobby', text: `Welcome to the Lobby, ${stompModule.idToken.name}!` });

    // eslint-disable-next-line no-underscore-dangle
    this.player = { name: stompModule.idToken.name, id: stompModule.idToken.__raw };
    console.log('publishing', JSON.stringify(this.player));
    // stompModule.publish('/app/lobby/player/new', { name: 'SomePlayer', id: '123' });
    stompModule.publish('/app/lobby/player/new', this.player);

    window.document.addEventListener('beforeunload', () => {
      console.log('beforeDestroy called');
      stompModule.publish('/app/lobby/player/deleted', this.player);
    });

    this.subscribed = true;
  }
}

const chatModule = new ChatModule({ store, name: 'chat' });
export default chatModule;

import {
  VuexModule, Module, Action, Mutation,
} from 'vuex-class-modules';
import { IMessage } from '@stomp/stompjs';
import stompModule from './StompModule';
import store from './store';

@Module({ generateMutationSetters: true })
class PokerModule extends VuexModule {
  table: any = {};

  // Table management
  getGame() {
    stompModule.publishString('/app/poker/game', this.table.tableName);
  }

  join() {
    stompModule.publishString('/app/poker/game/join', this.table.tableName);
  }

  reset() {
    stompModule.publishString('/app/poker/game/reset', this.table.tableName);
  }

  newHand() {
    stompModule.publishString('/app/poker/game/newhand', this.table.tableName);
  }

  // Player/Hand interaction
  leave() {
    stompModule.publishString('/app/poker/game/leave', this.table.tableName);
  }

  bet(amount: number) {
    stompModule.publish('/app/poker/game/bet', { tableName: this.table.tableName, amount });
  }

  fold() {
    stompModule.publishString('/app/poker/game/fold', this.table.tableName);
  }

  @Action
  subscribe(tableName: string) {
    stompModule.subscribe({
      destination: `/queue/pokertable/${tableName}`,
      callback: (message: IMessage) => {
        this.table = JSON.parse(message.body);
      },
    });
    stompModule.publishString('/app/poker/game/join', tableName);
  }
}

const pokerModule = new PokerModule({ store, name: 'poker' });
export default pokerModule;

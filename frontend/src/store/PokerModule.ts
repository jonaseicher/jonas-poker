import {
  VuexModule, Module, Action, Mutation,
} from 'vuex-class-modules';
import { IMessage } from '@stomp/stompjs';
import Table from '@/model/Table';
import Player from '@/model/Player';
import stompModule from './StompModule';
import store from './store';

@Module({ generateMutationSetters: true })
class PokerModule extends VuexModule {
  table: Table = new Table();

  get allPlayers() {
    return [...this.table.players, ...this.table.newHandPlayers];
  }

  // Table management
  getGame() {
    stompModule.publishString('/app/poker/game', this.table.tableName);
  }

  join(position = 0) {
    console.log('join called', this.table.tableName);
    stompModule.publish('/app/poker/game/join', { tableName: this.table.tableName, position });
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

  getPlayer(playerName: string): Player | undefined {
    return this.allPlayers.find((player: any) => player.name === playerName);
    // return this.table.players.find((player: any) => player.name === playerName);
  }

  getMe(): Player | undefined {
    const playerName = stompModule.idToken.name;
    if (playerName) {
      console.log(playerName);
      const player = this.getPlayer(playerName);
      return player;
    }
    return undefined;
  }

  @Mutation
  updateTable(message: IMessage) {
    this.table = JSON.parse(message.body);
    console.log('table updated in pokerModule');
  }

  subscribe(tableName: string) {
    console.log(`subscribe to ${tableName}`);
    stompModule.subscribe({
      destination: `/queue/pokertable/${tableName}`,
      callback: this.updateTable,
    });
    stompModule.publishString('/app/poker/game', tableName);
  }
}
const pokerModule = new PokerModule({ store, name: 'poker' });
export default pokerModule;

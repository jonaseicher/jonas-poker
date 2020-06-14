<template>
  <v-container>
    <v-btn @click="stomp.publishString('/app/poker/game', table.tableName)">Get Game</v-btn>
    <v-btn @click="stomp.publish('/app/poker/game/join', { tableName: tableName, position: 0 })">Join</v-btn>
    <v-btn @click="stomp.publishString('/app/poker/game/reset', table.tableName)">Reset</v-btn>
    <v-btn @click="stomp.publishString('/app/poker/game/newhand', table.tableName)">New Hand</v-btn>
    <v-row>
      <v-col v-for="player in table.players" :key="player.name">
        <div>{{ player.name }}</div>
        <v-btn @click="stomp.publishString('/app/poker/game/leave', table.tableName)">Leave</v-btn>
        <v-btn @click="stomp.publish('/app/poker/game/bet', { tableName: table.tableName, amount: 2 })">Bet</v-btn>
        <!-- <v-btn @click="stomp.publish('/app/poker/game/bet', player, 2)">Bet</v-btn> -->
        <v-btn @click="stomp.publishString('/app/poker/game/fold', table.tableName)">Fold</v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col v-for="p in table.players" v-bind:key="p.name">
        <div>{{ p.name }}, Chips: {{ p.chips }}</div>
        <!-- Reveal cards only to the owner or if hand is done and wasn't won by folding -->
        <v-row v-if="p.name === stomp.idToken.name || (table.state === 'HAND_DONE' && !table.winDueToFolding)">
          <v-col v-for="card in p.pocketCards"  :key="card.rank + card.suit">
            <PokerCard :card="card" />
          </v-col>
        </v-row>
        <div v-if="p.bet">Bet: {{ p.bet }}</div>
        <div v-if="p.potContribution">Pot Contribution: {{ p.potContribution }}</div>
        <div v-if="p.chipsAwarded">Chips Awarded: {{ p.chipsAwarded }}</div>
        <div v-if="p.ready">Ready</div>
        <div v-if="p.folded">Folded</div>
        <div v-if="p.broke">Broke</div>
      </v-col>
    </v-row>
    <v-row>
      <v-col v-for="p in table.players" v-bind:key="p.name">
        <div v-if="p.bestHand">Best Hand: {{ p.bestHand.category }}
          <PokerCard :card="card" v-for="card in p.bestHand.cards" :key="card.rank + card.suit" />
        </div>
      </v-col>
    </v-row>
    <div v-if="table.actor">Actor: {{ table.actor.name }}</div>
    <v-col>
      Table
      <PokerCard v-for="card in table.boardCards"
        :key="card.rank + card.suit"
        :card="card"/>
    </v-col>
    <div v-if="table.lastRaised">Last Raise: {{ table.lastRaised.name }} {{ table.lastRaised.bet }}</div>
    <div v-if="table.potSize">Pot Size: {{ table.potSize }}</div>
    <div v-if="table.toRaiseAmount">To Raise Amount: {{ table.toRaiseAmount }}</div>
    <div v-if="table.gameWinner">Game Winner: {{ table.gameWinner.name }}</div>
    <div v-if="table.gameOver">Game Over</div>
    <div v-if="table.state">Table State: {{ table.state }}</div>
    <div v-if="table.stateDescription">State Description: {{ table.stateDescription }}</div>
    <div v-if="table.started">Started: {{ table.started }}</div>
    <div v-if="table.winDueToFolding">Win Due To Folding</div>
    <div v-if="table.winningCards && table.winningCards.length > 0">
      Winning Cards:
      <PokerCard :card="card" v-for="card in table.winningCards" :key="card.rank + card.suit" />
    </div>
    <v-divider></v-divider>
    {{ table }}
  </v-container>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import { IMessage } from '@stomp/stompjs';
import PokerCard from './PokerCard.vue';
import PlayerCard from './PlayerCard.vue';
import stompModule from '../store/StompModule';
import Table from '../model/Table';

@Component({
  components: {
    PokerCard,
    PlayerCard,
  },
})
export default class PokerTable extends Vue {
  @Prop()
  tableName!: string;

  get stomp() {
    return stompModule;
  }

  table: Table = new Table();

  created() {
    stompModule.subscribe({ destination: `/queue/pokertable/${this.tableName}`, callback: this.updateTable });
    stompModule.publish('/app/poker/game/join', { tableName: this.tableName, position: 0 });
  }

  updateTable(message: IMessage) {
    this.table = JSON.parse(message.body);
  }
}

</script>

<style scoped>
</style>

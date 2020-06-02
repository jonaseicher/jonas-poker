<template>
  <v-container>
    <v-btn @click="test()">Connect + Subscribe</v-btn>
    <v-btn @click="stomp.publish('/app/poker/game', {})">Get Game</v-btn>
    <v-btn @click="stomp.publish('/app/poker/game/reset', {})">Reset</v-btn>
    <v-btn @click="stomp.publishString('/app/poker/game/newhand', player)">New Hand</v-btn>
    <v-row>
      <v-col v-for="player in players" :key="player">
        <div>{{ player }}</div>
        <v-btn @click="stomp.publishString('/app/poker/game/join', player)">Join</v-btn>
        <v-btn @click="stomp.publishString('/app/poker/game/leave', player)">Leave</v-btn>
        <v-btn @click="stomp.publish('/app/poker/game/bet', { playerId: player, amount: 2 })">Bet</v-btn>
        <!-- <v-btn @click="stomp.publish('/app/poker/game/bet', player, 2)">Bet</v-btn> -->
        <v-btn @click="stomp.publishString('/app/poker/game/fold', player)">Fold</v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col v-for="p in table.players" v-bind:key="p.name">
        <div>{{ p.name }}, Chips: {{ p.chips }}</div>
        <v-row>
          <v-col v-for="card in p.pocketCards"  :key="card.rank + card.suit">
          <PokerCard :card="card"  />
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
    <!-- {{ poker.pokerTable }} -->
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { IMessage } from '@stomp/stompjs';
import PokerCard from './PokerCard.vue';
import stompModule from '../store/StompModule';
import pokerModule from '../store/PokerModule';

@Component({
  components: {
    PokerCard,
  },
})
export default class PokerTable extends Vue {
  get stomp() {
    return stompModule;
  }

  players = ['Jonas', 'Mitch', 'Krischna', 'Jenny', 'Michael'];

  table: any = {};

  test() {
    stompModule.connect();
    stompModule.subscribe({ destination: '/queue/pokertable', callback: this.updateTable });
  }

  updateTable(message: IMessage) {
    this.table = JSON.parse(message.body);
    console.log(this.table.players);
  }
}

</script>

<style scoped>

</style>

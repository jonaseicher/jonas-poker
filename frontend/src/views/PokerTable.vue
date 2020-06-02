<template>
  <v-container>
    <v-btn @click="test()">Connect + Subscribe</v-btn>
    <v-btn @click="stomp.publish('/app/poker/game', {})">Get Game</v-btn>
    <v-btn @click="stomp.publish('/app/poker/game/reset', {})">Reset</v-btn>
    <v-btn @click="stomp.publishString('/app/poker/game/newhand', player)">New Hand</v-btn>
    <v-row>
      <v-col v-for="player in players" :key="player">
        <div>{{ player }}, Chips: {{ }} </div>
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
        <PokerCard :card="card" v-for="card in p.pocketCards" :key="card.rank + card.suit" />
        <div v-if="p.ready">Ready</div>
        <div v-if="p.folded">Folded</div>
        <div v-if="p.broke">Broke</div>
        <div v-if="p.bestHand">Best Hand: {{ p.bestHand.category }}
          <PokerCard :card="card" v-for="card in p.bestHand.cards" :key="card.rank + card.suit" />
        </div>
      </v-col>
    </v-row>
    <div v-if="table.actor">Actor: {{ table.actor.name }}</div>
    <v-col>
      <PokerCard v-for="card in table.boardCards"
        :key="card.rank + card.suit"
        :card="card"/>
    </v-col>
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

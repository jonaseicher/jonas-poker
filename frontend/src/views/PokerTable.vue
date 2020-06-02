<template>
  <v-container>
    <v-btn @click="test()">Connect + Subscribe</v-btn>
    <v-btn @click="stomp.publish('/app/poker/game', {})">Get Game</v-btn>
    <v-btn @click="stomp.publish('/app/poker/game/reset', {})">Reset</v-btn>
    <v-row>
      <v-col>
        <div>Jonas</div>
        <v-btn @click="stomp.publish('/app/poker/game/join', 'Jonas')">Join</v-btn>
        <v-btn @click="stomp.publish('/app/poker/game/leave', 'Jonas')">Leave</v-btn>
        <v-btn @click="stomp.publish('/app/poker/game/bet', { playerId: 'Jonas', amount: 2 })">Bet</v-btn>
        <!-- <v-btn @click="stomp.publish('/app/poker/game/bet', 'Jonas', 2)">Bet</v-btn> -->
        <v-btn @click="stomp.publish('/app/poker/game/fold', 'Jonas')">Fold</v-btn>
      </v-col>
      <v-col>
        <div>Jonas2</div>
        <v-btn @click="stomp.publish('/app/poker/game/join', 'Jonas2')">Join</v-btn>
        <v-btn @click="stomp.publish('/app/poker/game/leave', 'Jonas2')">Leave</v-btn>
        <v-btn @click="stomp.publish('/app/poker/game/bet', { playerId: 'Jonas2', amount: 2 })">Bet</v-btn>
        <v-btn @click="stomp.publish('/app/poker/game/fold', 'Jonas2')">Fold</v-btn>
      </v-col>
    </v-row>
    <div v-for="p in table.players" v-bind:key="p.name">{{ p.name }} {{ p.chips }} {{ p.pocketCards }}</div>
    <div v-if="table.actor">Actor: {{ table.actor.name }}</div>
    {{ table }}
    <!-- {{ poker.pokerTable }} -->
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { IMessage } from '@stomp/stompjs';
import stompModule from '../store/StompModule';
import pokerModule from '../store/PokerModule';

@Component
export default class PokerTable extends Vue {
  get stomp() {
    return stompModule;
  }

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

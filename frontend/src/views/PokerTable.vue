<template>
  <v-container>
    <v-btn @click="test()">Connect + Subscribe</v-btn>
    <v-btn @click="stomp.publish('/app/poker/game', {})">Get Game</v-btn>
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
    this.table = message.body;
  }
}

</script>

<style scoped>

</style>

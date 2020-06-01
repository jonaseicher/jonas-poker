<template>
  <div>
    <template v-if="stomp.connected">
      <v-btn  class="mr-2 mb-2" @click="stomp.publish('/app/karten', 'sending to /app/karten')">Send to /app/karten</v-btn>
      <v-btn class="mr-2 mb-2" @click="stomp.publish('/app/cards', 'sending to /app/cards')">Send to /app/cards</v-btn>
      <!-- <v-btn @click="publish('/topic/greetings', 'sending to /topic/greetings')">Send to /topic/greetings</v-btn>
      <v-btn @click="publish('/app/hello', 'sending to /app/hello')">Send to /app/hello</v-btn> -->
      <v-btn class="mr-2 mb-2" @click="stomp.publish('/app/join/table', 'SomeTable')">Join Poker Table</v-btn>
      <v-btn class="mr-2 mb-2" @click="stomp.publish('/app/lobby/player/new', { name: 'SomePlayer', id: '123' })">New Player</v-btn>
    </template>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import stompModule from '../store/StompModule';


@Component
export default class StompComponent extends Vue {
  get stomp() {
    return stompModule;
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

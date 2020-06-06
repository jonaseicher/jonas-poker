<template>
  <div>
    <v-row class="display-1 pl-5">To-Do List</v-row>
    <v-divider></v-divider>
    <v-container class="d-flex flex-wrap flex-grow-1">
      <v-card
        v-for="todo in todos" :key="todo.title"
        class="mx-auto align-self-stretch my-2"
        width="344"
      >
        <v-card-text>
          <p class="display-1 text--primary">
            {{ todo.title }}
          </p>
          <!-- <p>adjective</p> -->
          <div class="text--primary">
            {{ todo.description }}
          </div>
        </v-card-text>
        <!-- <v-card-actions>
          <v-btn
            text
            color="light-blue accent-4"
          >
            Mark as done
          </v-btn>
          <v-btn
            text
            color="light-red accent-4"
          >
            Delete
          </v-btn>
        </v-card-actions> -->
      </v-card>
    </v-container>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import ChatComponent from './ChatComponent.vue';
import StompComponent from './StompComponent.vue';
import chatModule from '../store/ChatModule';
import stompModule from '../store/StompModule';

@Component
export default class Dashboard extends Vue {
  todos = [
    { title: 'User "Cards"', description: 'Make "user-cards" with name, picture, chips, blind/dealer button, pocket cards, actions. Highlight when its this players turn.' },
    { title: 'Custom bets and raises', description: '' },
    { title: 'Nice playing-cards', description: '' },
    { title: 'Show waiting players', description: '' },
    { title: 'Refund chips when forcing new hand', description: '' },
    { title: 'Restict New Hand usage', description: 'Only allow new hand when game state = HAND_DONE' },
    { title: 'Cleanup', description: 'Hide debug stuff (json, unused tabs, etc)' },
    { title: 'Sound Effects', description: 'Add sound effects for New Hand, Bet, call, raise, fold, join/leave, your turn' },
    { title: 'Visual Effects', description: 'Add visual effects for New Hand, Bet, call, raise, fold, join/leave, your turn' },
    { title: 'Highlight best/winning hand', description: 'Highlight combination of pocket/table that make up each players best hand' },
    { title: 'Reveal cards', description: 'Allow players to reveal their cards voluntarily after game conclusion' },
    { title: 'All-in mode', description: 'Detect All-ins and reveal pockets, then deal remaining cards.' },
    { title: 'Dealer Mode', description: 'Dealer can interactively deal the cards, for example with drag & drop, then click to reveal them' },
  ];
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

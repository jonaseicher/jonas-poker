<template>
  <div>
    <v-row class="display-1 pl-5">Player Controls</v-row>
      <PlayerCard :table="table" :player="player" class="ma-5" />
    <v-divider></v-divider>
    <v-row class="display-1 pl-5 pt-2">Poker Cards</v-row>
      <PokerCard :card="{ rank: 11, suit: 'HEART'}" class="mx-1 my-3"/>
      <PokerCard :card="{ rank: 0, suit: 'SPADE'}" class="mx-1 my-3"/>
      <PokerCard :card="{ rank: 12, suit: 'CLUB'}" class="mx-1 my-3"/>
      <PokerCard :card="{ rank: 7, suit: 'SPADE'}" class="mx-1 my-3"/>
      <PokerCard :card="{ rank: 1, suit: 'DIAMOND'}" class="mx-1 my-3"/>
    <v-divider></v-divider>
    <v-row class="display-1 pl-5 pt-2">To-Do List</v-row>
    <v-divider></v-divider>
    <v-container class="d-flex flex-wrap flex-grow-1">
      <v-card
        v-for="todo in todos" :key="todo.title"
        class="mx-auto align-self-stretch my-2"
        width="344"
      >
        <v-card-text>
          <p class="display-1 text--primary">
            <v-icon v-if="todo.done" large color="green" class="mb-2">mdi-check</v-icon>
            <v-icon v-else large color="grey" class="mb-2">mdi-square-outline</v-icon>
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
import PokerCard from './PokerCard.vue';
import PlayerCard from './PlayerCard.vue';
import StompComponent from './StompComponent.vue';
import chatModule from '../store/ChatModule';
import stompModule from '../store/StompModule';


@Component({
  components: {
    PokerCard,
    PlayerCard,
  },
})
export default class Dashboard extends Vue {
  todos = [
    { title: 'Restict New Hand usage', description: 'Only allow new hand when game state = HAND_DONE' },
    { title: 'Cleanup', description: 'Hide debug stuff (json, unused tabs, etc)' },
    { title: 'Highlight best/winning hand', description: 'Highlight combination of pocket/table that make up each players best hand' },
    { title: 'All-in mode', description: 'Detect All-ins and reveal pockets, then deal remaining cards.' },
    { title: 'Copy assets to heroku', description: 'Assets are not deployed to heroku in the current build. Change that.' },
    { title: 'Retain Poker-Table', description: 'Store joined poker-table in vuex and load it when player clicks on "Poker" nav item.' },
    { title: 'Show waiting players', description: 'Show players who joined while a hand is in progress as waiting.' },
    { title: 'Fix connection', description: 'Connection state management is a bit wonky (requires manual logout/login when token expires)' },
    { title: 'Sound Effects', description: 'Add sound effects for New Hand, Bet, call, raise, fold, join/leave, your turn' },
    { title: 'Visual Effects', description: 'Add visual effects for New Hand, Bet, call, raise, fold, join/leave, your turn' },
    { title: 'Refund chips when forcing new hand', description: '' },
    { title: 'Reveal cards', description: 'Allow players to reveal their cards voluntarily after game conclusion' },
    { title: 'Dealer Mode', description: 'Dealer can interactively deal the cards, for example with drag & drop, then click to reveal them' },
    { done: true, title: 'Player Controls', description: 'Make player-controls with name, picture, chips, blind/dealer button, pocket cards, actions. Highlight when its this players turn.' },
    { done: true, title: 'Custom bets and raises', description: 'Players can choose how much money to bet or raise (within the limits)' },
    { done: true, title: 'Nice playing-cards', description: 'Make playing cards look nice' },
  ];

  table = {
    tableName: 'ALL YOUR BASE',
    players: [{
      id: 'asdf@asdf.de', name: 'asdf@asdf.de', chips: 18, bet: 0, potContribution: 2, chipsAwarded: 0, pocketCards: [{ rank: 12, suit: 'SPADE', faceCard: true }, { rank: 1, suit: 'DIAMOND', faceCard: false }], bestHand: null, net: -2, folded: false, totalBet: 2, ready: true, broke: false, maxBet: 18,
    }, {
      id: 'Jonas Eicher',
      name: 'Jonas Eicher',
      chips: 16,
      bet: 2,
      potContribution: 2,
      chipsAwarded: 0,
      pocketCards: [{ rank: 5, suit: 'HEART', faceCard: false },
        { rank: 2, suit: 'CLUB', faceCard: false }],
      bestHand: null,
      net: -2,
      folded: false,
      totalBet: 4,
      ready: true,
      broke: false,
      maxBet: 18,
    }],
    boardCards: [{ rank: 6, suit: 'SPADE', faceCard: false }, { rank: 1, suit: 'CLUB', faceCard: false }, { rank: 10, suit: 'DIAMOND', faceCard: true }],
    button: {
      id: 'asdf@asdf.de', name: 'asdf@asdf.de', chips: 18, bet: 0, potContribution: 2, chipsAwarded: 0, pocketCards: [{ rank: 12, suit: 'SPADE', faceCard: true }, { rank: 1, suit: 'DIAMOND', faceCard: false }], bestHand: null, net: -2, folded: false, totalBet: 2, ready: true, broke: false, maxBet: 18,
    },
    actor: {
      id: 'asdf@asdf.de', name: 'asdf@asdf.de', chips: 18, bet: 0, potContribution: 2, chipsAwarded: 0, pocketCards: [{ rank: 12, suit: 'SPADE', faceCard: true }, { rank: 1, suit: 'DIAMOND', faceCard: false }], bestHand: null, net: -2, folded: false, totalBet: 2, ready: true, broke: false, maxBet: 18,
    },
    lastRaised: {
      id: 'Jonas Eicher', name: 'Jonas Eicher', chips: 16, bet: 2, potContribution: 2, chipsAwarded: 0, pocketCards: [{ rank: 5, suit: 'HEART', faceCard: false }, { rank: 2, suit: 'CLUB', faceCard: false }], bestHand: null, net: -2, folded: false, totalBet: 4, ready: true, broke: false, maxBet: 18,
    },
    winners: null,
    pot: 4,
    toCallAmount: 2,
    newHandPlayers: [],
    state: 'BET_2',
    started: true,
    gameOver: false,
    gameWinner: null,
    winningCards: [],
    potSize: 6,
    toRaiseAmount: 6,
    stateDescription: 'Flop',
    winDueToFolding: false,
    potContribution: 2,
  };

  player = this.table.players[0];
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

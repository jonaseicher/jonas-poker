<template>
  <v-container>
    <v-btn @click="poker.newHand(tableName)" :disabled="!isHandDone">New Hand</v-btn>
    <v-row>
      <v-col v-for="p in poker.table.players" v-bind:key="p.name">
        <PlayerCard :player="p" :table="poker.table" />
      </v-col>
    </v-row>
    <v-row>
      <v-col v-for="p in poker.table.players" v-bind:key="p.name">
        <div v-if="p.bestHand">Best Hand: {{ p.bestHand.category }}
          <PokerCard :card="card" v-for="card in p.bestHand.cards" :key="card.rank + card.suit" />
        </div>
      </v-col>
    </v-row>
    <div v-if="poker.table.actor">Actor: {{ poker.table.actor.name }}</div>
    <v-col>
      Table
      <PokerCard v-for="card in poker.table.boardCards"
        :key="card.rank + card.suit"
        :card="card"/>
    </v-col>
    <div v-if="poker.table.lastRaised">Last Raise: {{ poker.table.lastRaised.name }} {{ poker.table.lastRaised.bet }}</div>
    <div v-if="poker.table.potSize">Pot Size: {{ poker.table.potSize }}</div>
    <div v-if="poker.table.toRaiseAmount">To Raise Amount: {{ poker.table.toRaiseAmount }}</div>
    <div v-if="poker.table.gameWinner">Game Winner: {{ poker.table.gameWinner.name }}</div>
    <div v-if="poker.table.gameOver">Game Over</div>
    <div v-if="poker.table.state">Table State: {{ poker.table.state }}</div>
    <div v-if="poker.table.stateDescription">State Description: {{ poker.table.stateDescription }}</div>
    <div v-if="poker.table.started">Started: {{ poker.table.started }}</div>
    <div v-if="poker.table.winDueToFolding">Win Due To Folding</div>
    <div v-if="poker.table.winningCards && poker.table.winningCards.length > 0">
      Winning Cards:
      <PokerCard :card="card" v-for="card in poker.table.winningCards" :key="card.rank + card.suit" />
    </div>
    <v-btn @click="poker.reset(tableName)">Reset Table</v-btn>
    <v-btn @click="poker.join(tableName)">Join Table</v-btn>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import { IMessage } from '@stomp/stompjs';
import PokerCard from './PokerCard.vue';
import PlayerCard from './PlayerCard.vue';
import stompModule from '../store/StompModule';
import pokerModule from '../store/PokerModule';

@Component({
  components: {
    PokerCard,
    PlayerCard,
  },
})
export default class PokerTable2 extends Vue {
  @Prop()
  tableName!: string;

  get poker() {
    return pokerModule;
  }

  created() {
    pokerModule.subscribe(this.tableName);
  }

  get isHandDone() {
    console.log(pokerModule.table.state);
    return pokerModule.table.state === 'HAND_DONE';
  }
}

</script>

<style scoped>
</style>

<template>
  <v-container>
    <v-row>
      <v-col v-for="p in poker.table.players" v-bind:key="p.name">
        <PlayerCard :player="p" :table="poker.table" />
      </v-col>
      <v-col v-for="p in poker.table.newHandPlayers" v-bind:key="p.name">
        <PlayerCard :player="p" :table="poker.table" />
      </v-col>
    </v-row>
    <v-row>
      <v-img
        v-if="isHandDone"
        max-width="150"
        max-height="150"
        src="./deck3.jpg"
        position="left"
        class="mr-3 bounce elevation-4"
        @click="poker.newHand(tableName)"
      ></v-img>
      <v-img
        v-else
        max-width="150"
        max-height="150"
        src="./deck3.jpg"
        position="left"
        class="mr-3"
      ></v-img>
      <CardsTable :cards="poker.table.boardCards" />
      <v-card width="150"
        height="150" color="cyan darken-4" class="d-flex ml-3 align-center justify-center">

        <v-tooltip bottom>
          <template v-slot:activator="{ on }">
            <v-chip color="rgba(0, 0, 0, 0.7)" v-on="on" label large class="headline ml-1">
              <span>
                <v-icon large left>mdi-cash-usd</v-icon>
              </span>
              <span v-if="poker.table.potSize">{{ poker.table.potSize }}</span>
            </v-chip>
          </template>
          <span>Amount of chips in the pot</span>
        </v-tooltip>
      </v-card>
    </v-row>
    <v-row>
      <v-col v-for="p in poker.table.players" v-bind:key="p.name">
        <div v-if="p.bestHand">Best Hand: {{ p.bestHand.category }}
          <PokerCard :card="card" v-for="card in p.bestHand.cards" :key="card.rank + card.suit" />
        </div>
      </v-col>
    </v-row>
    <div v-for="winner in poker.table.winners" :key="winner.name">Hand Winner: {{ winner.name }}</div>
    <div v-if="poker.table.gameOver">Game Over</div>
    <div v-if="poker.table.gameWinner">Game Winner: {{ poker.table.gameWinner.name }}</div>
    <div v-if="poker.table.stateDescription">Phase: {{ poker.table.stateDescription }}</div>
    <div v-if="poker.table.winDueToFolding">Everybody folded. </div>
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
import CardsTable from './CardsTable.vue';
import stompModule from '../store/StompModule';
import pokerModule from '../store/PokerModule';

@Component({
  components: {
    PokerCard,
    PlayerCard,
    CardsTable,
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
/* .green {
  background: radial-gradient(circle, rgb(0, 184, 21) 0%, rgb(0, 82, 10) 100%);
} */

.bounce {
  animation: bounce 0.5s;
  animation-iteration-count: infinite;
}

@keyframes bounce {
  0% { transform: translate(0px, 1px); }
  20% { transform: translate(0px, 2px); }
  40% { transform: translate(0px, 3px); }
  60% { transform: translate(0px, 4px); }
  80% { transform: translate(0px, 2px); }
  100% { transform: translate(0px, 0px); }
}
</style>

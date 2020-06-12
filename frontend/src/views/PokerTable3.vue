<template>
<v-container>
  <!-- ROW 1 -->
  <div class="player-row">
    <div class="player">Div</div>
    <div class="player">Div</div>
    <div class="player">Dqwewqeqweiv</div>
    <div class="player">Div</div>
  </div>
  <!-- ROW 2 -->
  <div class="row">
    <div class="player-col">
      <div class="player">P1</div>
      <div class="player">P2</div>
    </div>
    <div class="table d-flex flex-grow-1 justify-center align-center">
      <v-row class="ma-5 d-flex">
        <v-col>
          <!-- <PokerCard v-if="cards[0]" :card="cards[0]" /> -->
        </v-col>
        <v-col>
          <!-- <PokerCard v-if="cards[1]" :card="cards[1]" /> -->
        </v-col>
        <v-col>
          <!-- <PokerCard v-if="cards[2]" :card="cards[2]" /> -->
        </v-col>
        <v-col>
          <!-- <PokerCard v-if="cards[3]" :card="cards[3]" /> -->
        </v-col>
        <v-col>
          <!-- <PokerCard v-if="cards[4]" :card="cards[4]" /> -->
        </v-col>
      </v-row>
    </div>
    <div class="player-col">
      <div class="player">P1</div>
      <div class="player">P2</div>
    </div>
  </div>
  <!-- ROW 3 -->
  <div class="player-row">
    <div class="player">Div</div>
    <div class="player">Div</div>
    <div class="player">Div</div>
    <div class="player">Div</div>
  </div>
</v-container>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import { IMessage } from '@stomp/stompjs';
import PokerCard from './PokerCard.vue';
import PlayerCard2 from './PlayerCard2.vue';
import CardsTable from './CardsTable.vue';
import stompModule from '../store/StompModule';
import pokerModule from '../store/PokerModule';

@Component({
  components: {
    PokerCard,
    PlayerCard2,
    CardsTable,
  },
})
export default class PokerTable3 extends Vue {
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

.player {
  background-color: rgb(33, 33, 33);
  /* justify-content: space-between; */
  outline: rgb(58, 58, 58) solid 1px;
  padding: 10px;
}

.table {
  min-width: 300px;
  /* max-width: 600px; */
  min-height: 150px;
  height: 300px;
  max-height: 400px;
  background-color: #006064;
  border-radius: 85px;
}

.player-col {
  flex-direction: column;
  display: flex;
  justify-content: space-around;
  flex-grow: 0;
  margin: 30px 10px;
}

.player-row {
  margin: 10px 80px;
  justify-content: space-around;
  display: flex;
}
</style>

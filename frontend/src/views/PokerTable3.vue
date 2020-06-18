<template>
<v-container>
  <!-- ROW 1 -->
  <div class="player-row">
    <div v-if="!iHaveJoined()" class="player" id="player-0" @click="poker.join(0)">Div</div>
    <div v-if="!iHaveJoined()" class="player" id="player-1" @click="poker.join(1)">Div</div>
    <div v-if="!iHaveJoined()" class="player" id="player-2" @click="poker.join(2)">Div</div>
    <div v-if="!iHaveJoined()" class="player" id="player-3" @click="poker.join(3)">Div</div>
  </div>
  <!-- ROW 2 -->
  <div class="row">
    <div class="player-col">
      <div v-if="!iHaveJoined()" class="player" id="player-10" @click="poker.join(10)">Div</div>
      <div v-if="!iHaveJoined()" class="player" id="player-11" @click="poker.join(11)">Div</div>
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
      <div v-if="!iHaveJoined()" class="player" id="player-4" @click="poker.join(4)">Div</div>
      <div v-if="!iHaveJoined()" class="player" id="player-5" @click="poker.join(5)">Div</div>
    </div>
  </div>
  <!-- ROW 3 -->
    <div class="player-row">
    <div v-if="!iHaveJoined()" class="player" id="player-6" @click="poker.join(6)">Div</div>
    <div v-if="!iHaveJoined()" class="player" id="player-7" @click="poker.join(7)">Div</div>
    <div v-if="!iHaveJoined()" class="player" id="player-8" @click="poker.join(8)">Div</div>
    <div v-if="!iHaveJoined()" class="player" id="player-9" @click="poker.join(9)">Div</div>
  </div>
  <div class="row">
    <v-btn @click="poker.reset(tableName)">Reset Table</v-btn>
    <v-btn @click="poker.join()">Join Table</v-btn>
  </div>
  {{ poker.table }}
</v-container>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import { IMessage } from '@stomp/stompjs';
import PokerCard from './PokerCard.vue';
import PlayerCardSmall from './PlayerCardSmall.vue';
import PlayerCardNotMe from './PlayerCardNotMe.vue';
import CardsTable from './CardsTable.vue';
import pokerModule from '../store/PokerModule';
import Table from '../model/Table';
import Player from '../model/Player';
import PlayerCard from './PlayerCard.vue';

@Component({
  components: {
    PokerCard,
    PlayerCardSmall,
    PlayerCardNotMe,
    PlayerCard,
    CardsTable,
  },
})
export default class PokerTable3 extends Vue {
  @Prop()
  tableName!: string;

  playerCards: Vue[] = [];

  get poker() {
    return pokerModule;
  }

  created() {
    pokerModule.subscribe(this.tableName);
    pokerModule.subscribeWithCallback(this.tableName, this.updateTablePositions);
  }

  get isHandDone(): boolean {
    console.log(pokerModule.table.state);
    return pokerModule.table.state === 'HAND_DONE';
  }

  /** Returns players in the current game plus joined players waiting for the new hand. */
  get allPlayers(): Player[] {
    const allPlayers = pokerModule.table.newHandPlayers.concat(pokerModule.table.players);
    console.log('allPlayers (in-game and new-hand-players)', allPlayers);
    return allPlayers;
  }

  /** Returns Vue elements of players that are no longer on the table */
  get leavingPlayerElements(): Vue[] {
    const leavers = this.playerCards.filter((card) => !(this.allPlayers.map((player) => player.tablePosition).includes(card.$props.player.tablePosition)));
    console.log('leavers', leavers);
    return leavers;
  }

  removePlayerCards(badCards: Vue[]) {
    badCards.forEach((badCard: any) => {
      this.getChairElement(badCard.$props.player.tablePosition).style.display = 'flex';
      badCard.$destroy();
      badCard.$el.parentElement.removeChild(badCard.$el);
      this.playerCards.splice(this.playerCards.indexOf(badCard));
    });
  }

  get newPlayers() {
    const newPlayers = this.allPlayers.filter((player) => !(this.playerCards.map((card) => card.$props.player.tablePosition).includes(player.tablePosition)));
    console.log('newPlayers', newPlayers);
    return newPlayers;
  }

  getChairElement(i: number): any {
    const elementId = `player-${i}`;
    return document.getElementById(elementId);
  }

  updateTablePositions(message: IMessage) {
    console.log('updateTablePositions called');
    const badCards = this.leavingPlayerElements;
    this.removePlayerCards(badCards);

    this.newPlayers.forEach((player, i) => {
      const elementId = `player-${player.tablePosition}`;
      const playerChair: any = document.getElementById(elementId);
      if (playerChair) {
        let playerCard: Vue;
        if (this.isMe(player)) {
          playerCard = new PlayerCardSmall({
            propsData: { player },
          });
        } else {
          playerCard = new PlayerCardNotMe({
            propsData: { player },
          });
        }
        playerCard.$mount();
        playerChair.parentNode.insertBefore(playerCard.$el, playerChair);
        playerChair.style.display = 'none';
        this.playerCards.push(playerCard);
      } else {
        console.log('Player Element already exists.', player);
      }
    });
  }

  iHaveJoined() {
    // pokerModule.table;
    // console.log('joined', this.playersAtTable.includes(pokerModule.getMe()));
    // return this.playersAtTable.includes(pokerModule.getMe());
    return false;
  }

  isMe(player: Player) {
    return this.$auth.user.name === player.name;
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

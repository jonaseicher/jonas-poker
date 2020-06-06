<template>
<v-row>
  <v-col class="flex-grow-0">

  <v-card
    v-if="player"
    :class="cardClass"
    max-width="320"
  >
  <v-img
    width="320px"
    height="226px"
    src="./stack.jpg"
    position="left"
  >
    <v-card-title>
      <span v-if="isActor" class="title mr-auto bounce">{{ player.name }}</span>
      <span v-else class="title mr-auto">{{ player.name }}</span>
      <v-img v-if="player.picture" :src="player.picture" max-width="54px"></v-img>
      <v-tooltip bottom v-if="isDealer">
        <template v-slot:activator="{ on }">
          <v-icon large v-on="on">mdi-alpha-d-circle</v-icon>
        </template>
        <span>This player is the dealer</span>
      </v-tooltip>
      <!-- <v-icon v-else large left>mdi-account</v-icon> -->
    </v-card-title>
    <v-card-text>
      <v-row class="pt-3">
        <v-tooltip bottom>
          <template v-slot:activator="{ on }">
            <v-chip color="grey darken-4" v-on="on" label large class="headline ml-1">
              <span>
                <v-icon large left>mdi-cash-usd</v-icon>
              </span>
              <span>{{ player.chips }}</span>
            </v-chip>
          </template>
          <span>The players total remainining chips</span>
        </v-tooltip>
        <v-slider
        v-if="isMe"
        v-model="sliderBet"
        :min="sliderMin"
        :max="player.maxBet - player.bet"
        thumb-label="always"
        color="grey darken-4"
        class="pt-6 pl-6 pr-3"

        ></v-slider>
      </v-row>
    </v-card-text>

    <v-card-actions v-if="isMe">

      <template v-if="poker.table.toCallAmount > player.bet">
        <v-btn :disabled="!isActor"
          @click="poker.bet(poker.table.toCallAmount)"
        >call for {{ poker.table.toCallAmount - player.bet }}
        </v-btn>
        <v-btn :disabled="!isActor"
          @click="poker.bet(sliderBet)"
        >raise to {{ sliderBet }}
        </v-btn>
        <v-btn :disabled="!isActor"
          class="ml-auto"
          @click="poker.fold()"
        >fold</v-btn>
      </template>
      <template v-else>
        <v-btn :disabled="!isActor" @click="poker.fold()">check</v-btn>
        <v-btn :disabled="!isActor" @click="poker.bet(sliderBet)">bet {{ sliderBet }}</v-btn>
      </template>

    </v-card-actions>
  </v-img>
  </v-card>
  <v-row
    class="ma-2"
  >
    <div v-for="card in player.pocketCards"
      :key="card.rank + card.suit"
    >
      <PokerCard  v-if="isMe || (poker.table.state === 'HAND_DONE' && !poker.table.winDueToFolding)" :card="card" />
      <PokerCard  v-else :card="{ rank: -1, suit: 'COVER' }" />
    </div>
    <span
      v-if="player.folded"
      class="ml-auto"
    >
      <v-tooltip bottom>
        <template v-slot:activator="{ on }">
          <v-chip color="grey darken-4" v-on="on" label large class="headline ma-1">
            <!-- <span>
              <v-icon large class="mr-2">mdi-cash-usd</v-icon>
            </span> -->
            <span>FOLDED</span>
          </v-chip>
        </template>
        <span>This player has folded his cards and is out of the game</span>
      </v-tooltip>
    </span>

    <span
      v-if="player.bet > 0"
      class="ml-auto"
    >
      <v-tooltip  bottom>
        <template v-slot:activator="{ on }">
          <v-chip color="grey darken-4" v-on="on" label large class="headline ma-1">
            <span>
              <v-icon large class="mr-2">mdi-cash-usd</v-icon>
            </span>
            <span>{{ player.bet }}</span>
          </v-chip>
        </template>
        <span>The players current bet</span>
      </v-tooltip>
    </span>
  </v-row>
  </v-col>
</v-row>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import pokerModule from '../store/PokerModule';
import Card from '../model/Card';
import PokerCard from './PokerCard.vue';


@Component({
  components: {
    PokerCard,
  },
})
export default class PlayerCard extends Vue {
  @Prop()
  player: any;

  sliderBet = 2;

  get poker() {
    return pokerModule;
  }

  get isActor() {
    if (pokerModule.table.actor == null) {
      return false;
    }
    return pokerModule.table.actor.name === this.player.name;
  }

  get isDealer() {
    if (pokerModule.table.actor == null) {
      return false;
    }
    return pokerModule.table.button.name === this.player.name;
  }

  get isMe() {
    return this.$auth.user.name === this.player.name;
  }

  get cardClass() {
    let cardClass = 'mx-auto';
    if (this.isMe) {
      cardClass += ' itsMe';
    }
    return cardClass;
  }

  get sliderMin() {
    if (pokerModule.table.toCallAmount > this.player.bet) {
      console.log('Can only raise: ', pokerModule.table.toRaiseAmount);
      return pokerModule.table.toCallAmount * 2;
    }
    return 2;
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.v-image {
  border-radius: 25px;
}

.v-card__title {
  background-color:rgba(0, 0, 0, 0.5)
}

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

.itsMe {
  box-shadow: 0px 0px 16px rgba(255, 255, 255, 0.7);
}
</style>

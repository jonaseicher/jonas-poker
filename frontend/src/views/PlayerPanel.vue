<template>
<v-row>
  <v-col class="flex-grow-0">

  <PlayerCard2 :player="player" />

  <!-- POCKET -->
  <v-row class="ma-2">
    <div v-for="card in player.pocketCards"
      :key="card.rank + card.suit"
    >
      <PokerCard  v-if="isMe || (table.state === 'HAND_DONE' && !table.winDueToFolding)" :card="card" />
      <PokerCard  v-else :card="{ rank: -1, suit: 'COVER' }" />
    </div>
  <!-- STATUS -->
    <PlayerStatusBar
      v-if="player.folded"
      text="FOLDED"
      tooltip="This player has folded his cards and is out of the game"
      class="ml-auto" />
    <PlayerStatusBar
      v-if="player.bet > 0" :text="player.bet"
      tooltip="The players current bet"
      icon="mdi-cash-usd"
      class="ml-auto" />
    <PlayerStatusBar
      v-if="player.chipsAwarded > 0"
      :text="`+ ${player.chipsAwarded}`"
      tooltip="The player won this amount of chips"
      icon="mdi-cash-usd"
      class="ml-auto" />
  </v-row>
  </v-col>
</v-row>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import PlayerCard from './PlayerCard.vue';
import PlayerCard2 from './PlayerCard2.vue';
import PokerCard from './PokerCard.vue';
import PlayerStatusBar from './PlayerStatusBar.vue';


@Component({
  components: {
    PlayerCard,
    PlayerCard2,
    PokerCard,
    PlayerStatusBar,
  },
})
export default class PlayerPanel extends Vue {
  @Prop()
  player: any;

  @Prop()
  table: any;

  get isMe() {
    console.log(this.$auth.user.name === this.player.name);
    return this.$auth.user.name === this.player.name;
  }
}

</script>

<style scoped>
</style>

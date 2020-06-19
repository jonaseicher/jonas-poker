<template>
  <v-card
    v-if="player"
    min-width="100"
    max-width="160"
    dark
  >
    <v-card-title>
      <span v-if="isActor" class="mr-auto bounce">{{ player.name }}</span>
      <span v-else class="mr-auto">{{ player.name }}</span>
    </v-card-title>
    <v-card-text class="pb-3">
      <v-row class="">
        <v-tooltip bottom>
          <template v-slot:activator="{ on }">
            <v-chip color="rgba(0, 0, 0, 0.7)" v-on="on" label large class="headline mx-auto">
              <span>
                <v-icon large left>mdi-cash-usd</v-icon>
              </span>
              <span>{{ player.chips }}</span>
            </v-chip>
          </template>
          <span>The players total remainining chips</span>
        </v-tooltip>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import pokerModule from '../store/PokerModule';
import PlayerStatusBar from './PlayerStatusBar.vue';
import Player from '../model/Player';


@Component({
  components: {
    PlayerStatusBar,
  },
})
export default class PlayerCardNotMe extends Vue {
  @Prop()
  playerName!: string;

  @Prop()
  tablePosition!: number

  get player() {
    return pokerModule.getPlayer(this.playerName);
  }

  get poker() {
    return pokerModule;
  }

  get isActor() {
    if (pokerModule.table.actor == null) {
      return false;
    }
    return pokerModule.table.actor.name === this.playerName;
  }

  get isDealer() {
    if (pokerModule.table.actor == null) {
      return false;
    }
    return pokerModule.table.button.name === this.playerName;
  }

  created() {
    if (this.player) {
      this.tablePosition = this.player.tablePosition;
    }
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
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

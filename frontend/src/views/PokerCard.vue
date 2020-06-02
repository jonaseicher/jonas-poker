<template>
  <v-card>
      <v-card-content>
        <span v-if="card.rank == 0">A</span>
        <span v-if="card.rank == 12">K</span>
        <span v-if="card.rank == 11">D</span>
        <span v-if="card.rank == 10">B</span>
        <span v-if="!card.faceCard">{{ card.rank + 1 }}</span>
        <span class="pl-4" v-if="card.suit === 'SPADE'" style="color: rgb(138, 138, 138);">{{ card.suit }}</span>
        <span class="pl-4" v-if="card.suit === 'HEART'" style="color: rgb(232, 0, 0);">{{ card.suit }}</span>
        <span class="pl-4" v-if="card.suit === 'DIAMOND'" style="color: rgb(232, 90, 0);">{{ card.suit }}</span>
        <span class="pl-4" v-if="card.suit === 'CLUB'" style="color: rgb(90, 140, 150);">{{ card.suit }}</span>
      </v-card-content>
    <v-icon color="red">mdi-heart</v-icon>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import stompModule from '../store/StompModule';
import Card from '../model/Card';


@Component
export default class PokerCard extends Vue {
  @Prop()
  card!: Card;
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
div {
  color: gray;
}
</style>

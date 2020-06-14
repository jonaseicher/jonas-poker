<template>
  <v-chip large label color="grey lighten-4" class="ma-1">
    <strong v-if="card.rank == 0">A</strong>
    <strong v-if="card.rank == 12">K</strong>
    <strong v-if="card.rank == 11">D</strong>
    <strong v-if="card.rank == 10">B</strong>
    <strong v-if="card.rank < 10 && card.rank > 0">{{ card.rank + 1 }}</strong>
    <v-icon style="color: black;" v-if="card.suit === 'SPADE'">mdi-cards-spade</v-icon>
    <v-icon style="color: rgb(232, 0, 0);" v-if="card.suit === 'HEART'">mdi-heart</v-icon>
    <v-icon style="color: rgb(202, 80, 0);" v-if="card.suit === 'DIAMOND'">mdi-cards-diamond</v-icon>
    <v-icon style="color: rgb(0, 100, 110);" v-if="card.suit === 'CLUB'" >mdi-cards-club</v-icon>
    <v-icon style="color: rgb(100, 140, 150);" v-if="card.suit === 'COVER'" >mdi-cards</v-icon>
  </v-chip>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import Card from '../model/Card';


@Component
export default class PokerCard extends Vue {
  @Prop()
  card!: Card;
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
strong {
  font-size: 150%;
  color: black;
}
.v-icon {
  /* padding-bottom: 8px; */
  padding-left: 3px;
}
</style>

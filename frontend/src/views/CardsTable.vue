<template>
<v-card min-width="400" max-width="620"
    height="150" class="d-flex flex-grow-1" color="cyan darken-4">
<div class="d-flex flex-grow-1 justify-center align-center">
  <v-row class="ma-5 d-flex">
    <v-col>
      <PokerCard v-if="cards[0]" :card="cards[0]" />
    </v-col>
    <v-col>
      <PokerCard v-if="cards[1]" :card="cards[1]" />
    </v-col>
    <v-col>
      <PokerCard v-if="cards[2]" :card="cards[2]" />
    </v-col>
    <v-col>
      <PokerCard v-if="cards[3]" :card="cards[3]" />
    </v-col>
    <v-col>
      <PokerCard v-if="cards[4]" :card="cards[4]" />
    </v-col>
  </v-row>
</div>
</v-card>
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
export default class CardsTable extends Vue {
  @Prop()
  cards: Card[] = [];
  //   { rank: 0, suit: 'SPADE' },
  //   { rank: 0, suit: 'HEART' },
  //   { rank: 1, suit: 'SPADE' },
  //   { rank: 10, suit: 'CLUB' },
  // ];

  get poker() {
    return pokerModule;
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

<template>
  <v-card>
      <v-card-content>
        {{ card.rank + 1 }}
        {{ card.suit }}
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

</style>

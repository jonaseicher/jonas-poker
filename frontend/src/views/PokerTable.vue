<template>
  <v-container>
    <v-btn>Join Table</v-btn>
    <v-btn>Deal Cards</v-btn>
    {{ poker.pokerTable }}
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import pokerModule from '../store/PokerModule';

@Component
export default class PokerTable extends Vue {
  created() {
    pokerModule.subscribe();
  }

  get poker() {
    return pokerModule;
  }
}

</script>

<style scoped>

</style>

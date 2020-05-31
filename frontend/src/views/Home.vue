<template>
  <v-container>
    <span>{{ auth.user }}</span>
    <StompComponent v-if="auth.isAuthenticated" />
    <ChatComponent v-if="connected" />
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { TexasHoldEmPokerGameType, Player } from 'typedeck';
import ChatComponent from './ChatComponent.vue';
import StompComponent from './StompComponent.vue';
import chatModule from '../store/ChatModule';
import stompModule from '../store/StompModule';

@Component({
  components: {
    ChatComponent,
    StompComponent,
  },
})
export default class Home extends Vue {
  get auth() {
    return this.$auth;
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
div.container {
  height: inherit;
  /* max-height: initial; */
  display: flex;
  flex-direction: column;
}
</style>

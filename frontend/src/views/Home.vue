<template>
  <v-container>
    <v-btn @click="login">Sign In</v-btn>
    <v-btn @click="logout">Logout</v-btn>
    <span>{{ user }}</span>
    <div>{{ claims.__raw }}</div>
    <v-btn v-if="!connected" class="mr-2 mb-2" @click="connect">Connect</v-btn>
    <v-btn v-else class="mr-2 mb-2" @click="disconnect">Disconnect</v-btn>
    <div v-if="connected && subscribed">
      <v-btn  class="mr-2 mb-2" @click="publish('/app/karten', 'sending to /app/karten')">Send to /app/karten</v-btn>
       <v-btn class="mr-2 mb-2" @click="publish('/app/cards', 'sending to /app/cards')">Send to /app/cards</v-btn>
      <!-- <v-btn @click="publish('/topic/greetings', 'sending to /topic/greetings')">Send to /topic/greetings</v-btn>
      <v-btn @click="publish('/app/hello', 'sending to /app/hello')">Send to /app/hello</v-btn> -->
      <v-btn class="mr-2 mb-2" @click="publish('/app/join/table', 'SomeTable')">Join Poker Table</v-btn>
    </div>
    <ChatComponent v-if="connected" />
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { TexasHoldEmPokerGameType, Player } from 'typedeck';
import ChatComponent from './ChatComponent.vue';
import chatModule from '../store/ChatModule';
import stompModule from '../store/StompModule';

@Component({
  components: {
    ChatComponent,
  },
})
export default class Home extends Vue {
  get chatInputMessage() {
    return chatModule.chatInputMessage;
  }

  set chatInputMessage(value) {
    chatModule.chatInputMessage = value;
  }

  publish(destination: string, body: any) {
    stompModule.publish(destination, body);
  }

  get loading() {
    return stompModule.loading;
  }

  get connected() {
    return stompModule.connected;
  }

  get subscribed() {
    return chatModule.subscribed;
  }


  get player() {
    return chatModule.player;
  }

  get players() {
    return chatModule.players;
  }

  get messages() {
    return chatModule.messages;
  }

  connect() {
    stompModule.connect();
  }

  disconnect() {
    stompModule.disconnect();
  }

  subscribe() {
    chatModule.subscribe();
  }

  chat() {
    chatModule.sendChatMessage();
  }

  changeName() {
    chatModule.changeName();
  }

  get user() {
    return this.$auth.user;
  }

  get claims() {
    return this.$auth.claims;
  }

  login() {
    this.$auth.loginWithRedirect();
  }

  logout() {
    this.$auth.logout({
      returnTo: window.location.origin,
    });
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

.v-input {
  flex-grow: 0;
}

.chat-window {
  /* background-color: lightblue; */
  box-shadow: 1px 1px 6px 0px;
  border-radius: 3px;
  flex: 1 1 auto;
  overflow-y: visible;
}

.message {
  text-align: left;
}

h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>

<template>
  <v-container>
    <!-- <a href="https://dev-3lnv-vrr.eu.auth0.com/authorize?scope=openid profile&response_type=token&client_id=9wQDflrHGcuj5KuLvj2KMndatmz7bpLc&redirect_uri=http://localhost:8180&nonce=12345789">
    Sign In
    </a> -->
    <v-btn @click="login">Sign In</v-btn>
    <v-btn @click="logout">Logout</v-btn>
    <span>{{ user }}</span>
    <v-text-field label="Name" outlined v-model="player.name" @keyup="changeName"/>
    <ul v-for="player in players" :key="player.id">
      <li>
        <span>
          {{ player.name }}
        </span>
      </li>
    </ul>
    <v-btn v-if="!connected" class="mr-2 mb-2" @click="connect">Connect</v-btn>
    <v-btn v-else class="mr-2 mb-2" @click="disconnect">Disconnect</v-btn>
    <v-btn v-if="connected && !subscribed" class="mr-2 mb-2" @click="subscribe">Subscribe</v-btn>
    <div v-if="connected && subscribed">
      <v-btn  class="mr-2 mb-2" @click="publish('/app/karten', 'sending to /app/karten')">Send to /app/karten</v-btn>
       <v-btn class="mr-2 mb-2" @click="publish('/app/cards', 'sending to /app/cards')">Send to /app/cards</v-btn>
      <!-- <v-btn @click="publish('/topic/greetings', 'sending to /topic/greetings')">Send to /topic/greetings</v-btn>
      <v-btn @click="publish('/app/hello', 'sending to /app/hello')">Send to /app/hello</v-btn> -->
      <v-btn class="mr-2 mb-2" @click="publish('/app/join/table', 'SomeTable')">Join Poker Table</v-btn>
    </div>
    <div>Chat</div>
    <div class="chat-window pa-3 my-3">
      <div class="message" v-for="message in messages" :key="message.text + message.timestamp">
        {{ message.user }} : {{ message.text }}
      </div>
    </div>
    <v-text-field v-if="connected && subscribed" outlined v-model="chatInputMessage" @keyup.enter="chat"/>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { TexasHoldEmPokerGameType, Player } from 'typedeck';
import pokerModule from '../store/PokerModule';

@Component
export default class Home extends Vue {
  get chatInputMessage() {
    return pokerModule.chatInputMessage;
  }

  set chatInputMessage(value) {
    pokerModule.chatInputMessage = value;
  }

  publish(destination: string, body: any) {
    pokerModule.publish(destination, body);
  }

  get loading() {
    return pokerModule.loading;
  }

  get subscribed() {
    return pokerModule.subscribed;
  }

  get connected() {
    return pokerModule.connected;
  }

  get player() {
    return pokerModule.player;
  }

  get players() {
    return pokerModule.players;
  }

  get messages() {
    return pokerModule.messages;
  }

  connect() {
    pokerModule.connect();
  }

  subscribe() {
    pokerModule.subscribe();
  }

  disconnect() {
    pokerModule.disconnect();
  }

  chat() {
    pokerModule.sendChatMessage();
  }

  changeName() {
    pokerModule.changeName();
  }

  accessToken = '';

  token = '';

  get user() {
    return this.$auth.user;
  }

  login() {
    this.$auth.loginWithRedirect();
  }

  logout() {
    this.$auth.logout({
      returnTo: window.location.origin,
    });
  }

  // mounted() {
  //   if (window.location.hash) {
  //     this.parseHash();
  //   }
  // }

  // parseHash(hash = window.location.hash) {
  //   const matches = hash.match(/access_token=([^&]+).*id_token=([^&]+).*[^_]/);
  //   if (matches == null || matches.length < 3) {
  //     throw new Error('Unable to parse id_token from hash.');
  //   }
  //   [, this.accessToken, this.token] = matches;
  //   localStorage.setItem('id_token', this.token);
  //   localStorage.setItem('access_token', this.accessToken);
  // }
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

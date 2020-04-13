<template>
  <v-container>
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
    <v-btn v-if="connected && subscribed" class="mr-2 mb-2" @click="send('/app/karten', 'sending to /app/karten')">Send to /app/karten</v-btn>
    <!-- <v-btn @click="send('/user/karten', 'sending to user/karten')">Send to /user/karten</v-btn>
    <v-btn @click="send('/topic/greetings', 'sending to /topic/greetings')">Send to /topic/greetings</v-btn>
    <v-btn @click="send('/app/hello', 'sending to /app/hello')">Send to /app/hello</v-btn> -->
    <v-btn v-if="connected && subscribed" class="mr-2 mb-2" @click="send('/app/join/table', 'SomeTable')">Join Poker Table</v-btn>
    <div>Chat</div>
    <div class="chat-window pa-3 my-3" v-chat-scroll>
      <div class="message" v-for="message in messages" :key="message.text + Date.now()">
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

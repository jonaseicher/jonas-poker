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
    <v-btn v-if="!subscribed" class="mr-2 mb-2" @click="subscribe">Subscribe</v-btn>
    <div>Chat</div>
    <div class="chat-window pa-3 my-3">
      <div class="message" v-for="message in messages" :key="message.text + message.timestamp">
        {{ message.user }} : {{ message.text }}
      </div>
    </div>
    <v-text-field v-if="subscribed" outlined v-model="chatInputMessage" @keyup.enter="chat"/>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import chatModule from '../store/ChatModule';

@Component
export default class ChatComponent extends Vue {
  get chatInputMessage() {
    return chatModule.chatInputMessage;
  }

  set chatInputMessage(value) {
    chatModule.chatInputMessage = value;
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

  subscribe() {
    chatModule.subscribe();
  }

  chat() {
    chatModule.sendChatMessage();
  }

  changeName() {
    chatModule.changeName();
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

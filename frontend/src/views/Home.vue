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
    <div>
      <div>Chat</div>
      <div class="chat-window pa-3 my-3">
        <div class="message" v-for="message in messages" :key="message.text + Date.now()">
          {{ message.user }} : {{ message.text }}
        </div>
      </div>
      <v-text-field v-if="connected && subscribed" outlined v-model="chatInputMessage" @keyup.enter="chat"/>
    </div>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { TexasHoldEmPokerGameType, Player } from 'typedeck';
import { Client } from '@stomp/stompjs';

@Component
export default class Home extends Vue {
  players = null;

  messages: any[] = [];

  connected = false;

  subscribed = false;

  loading = false;

  // disconnectHandler = () => {
  //   console.log('disconnect handler');
  //   this.loading = false;
  //   this.connected = false;
  //   this.subscribed = false;
  //   console.log('subscribed = ', this.subscribed);
  //   this.messages.push({ user: 'Server', text: 'Disconnected' });
  // }

  chatInputMessage = '';

  player = { name: `Player-${Math.floor(Math.random() * 1000)}`, id: Math.floor(Math.random() * 100000000) };

  stompClient = new Client({
    // brokerURL: 'ws://127.0.0.1:9090/stomp', // spring-boot-backend (local)
    brokerURL: 'ws://35.158.11.245:9090/stomp', // spring-boot-backend-service (aws)
    // brokerURL: 'ws://localhost:61614/stomp', // node-backend
    connectHeaders: {
      sessionId: 'really',
    },
    debug(str) {
      console.log(str);
    },
    reconnectDelay: 0, // 0 = disabled
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  connect() {
    this.stompClient.onConnect = (receipt) => {
      this.connected = true;
      this.messages.push({ user: 'Server', text: 'Connected to the Server.' });
    };

    const handler = () => {
      this.loading = false;
      this.connected = false;
      this.subscribed = false;
      this.messages.push({ user: 'Server', text: 'Disconnected' });
    };

    // this.stompClient.onDisconnect = handler;
    this.stompClient.onWebSocketClose = handler;
    this.loading = true;
    this.stompClient.activate();
  }

  disconnect() {
    this.stompClient.deactivate();
    // this.connected = false;
  }

  chat() {
    this.send('/chat', { user: this.player.name, text: this.chatInputMessage });
    this.chatInputMessage = '';
  }

  send(destination: string, body: any) {
    this.stompClient.publish({ destination, body: JSON.stringify(body) });
  }

  changeName(newName: string) {
    this.stompClient.publish({ destination: '/app/lobby/player/changed', body: JSON.stringify(this.player) });
    // this.player.name = newName;
  }

  subscribe() {
    this.stompClient.subscribe('/lobby/players', (message) => {
      console.log(message);
      this.players = JSON.parse(message.body);
    });
    this.stompClient.subscribe('/chat', (message) => this.messages.push(JSON.parse(message.body)));
    this.stompClient.subscribe('/lobby/tables', (message) => console.log(message));

    this.messages.push({ user: 'Lobby', text: `Welcome to the Lobby, ${this.player.name}!` });

    this.stompClient.publish({ destination: '/app/lobby/player/new', body: JSON.stringify(this.player) });

    window.document.addEventListener('beforeunload', () => {
      console.log('beforeDestroy called');
      this.stompClient.publish({ destination: '/app/lobby/player/deleted', body: JSON.stringify(this.player) });
    });

    this.subscribed = true;

    // trying the node broker
    // this.stompClient.subscribe('/*', (x) => console.log(x));
    // this.stompClient.subscribe('/test', (x) => console.log(x));

    // this works with the spring boot broker
    // this.stompClient.subscribe('/join/table', (message) => console.log(message));
    // this.stompClient.subscribe('/user/queue/error', (message) => console.log(message));
    // this.stompClient.subscribe('/user/queue/karten', (message) => {
    //   console.log('SOMETHING');
    //   console.log(message);
    // });

    // old testing stuff
    // this.stompClient.subscribe('/topic/greetings', (message) => console.log(message));
    // this.stompClient.subscribe('/user/queue/humba', (message) => console.log(message));
    // this.stompClient.subscribe('/user/jonas/humba', (message) => console.log(message));
    // this.stompClient.subscribe('/user/queue/karten', (message) => console.log(message));
    // this.stompClient.subscribe('/user/jonas/karten', (message) => console.log(message));
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
div.container {
  height: inherit;
}

.chat-window {
  flex: 1;
  display: flex;
  flex-direction: column;
  /* background-color: lightblue; */
  box-shadow: 1px 1px 6px 0px;
  border-radius: 3px;
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

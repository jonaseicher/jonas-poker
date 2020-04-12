<template>
  <div>
    <input type="text"  v-model="player.name" @keyup="changeName">
    <ul v-for="player in players" :key="player.id">
      <li>
        <span>
          {{ player.name }}
        </span>
      </li>
    </ul>
    <button @click="connect">Connect</button>
    <button @click="disconnect">Disconnect</button>
    <button @click="subscribe">Subscribe</button>
    <button @click="send('/app/karten', 'sending to /app/karten')">Send to /app/karten</button>
    <!-- <button @click="send('/user/karten', 'sending to user/karten')">Send to /user/karten</button>
    <button @click="send('/topic/greetings', 'sending to /topic/greetings')">Send to /topic/greetings</button>
    <button @click="send('/app/hello', 'sending to /app/hello')">Send to /app/hello</button> -->
    <button @click="send('/app/join/table', 'SomeTable')">Join Poker Table</button>
    <div>Chat</div>
    <ul>
      <li v-for="message in messages" :key="message.text">{{ message.user }} : {{ message.text }}</li>
    </ul>
    <input type="text" v-model="chatInputMessage" @keyup.enter="chat">
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { TexasHoldEmPokerGameType, Player } from 'typedeck';
import { Client } from '@stomp/stompjs';

@Component
export default class HelloWorld extends Vue {
  players = null;

  messages = [];

  chatInputMessage = '';

  player = { name: `Player-${Math.floor(Math.random() * 1000)}`, id: Math.floor(Math.random() * 100000000) };

  stompClient = new Client({
    brokerURL: 'ws://127.0.0.1:9090/stomp',
    // brokerURL: 'ws://localhost:61614/stomp',
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
    this.stompClient.activate();
    this.messages = [{ user: 'Server', text: 'Connceted to the Server.' }];
  }

  disconnect() {
    this.stompClient.deactivate();
  }

  chat() {
    this.send('/chat', { user: this.player.name, text: this.chatInputMessage });
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

    this.messages = [{ user: 'Lobby', text: `Welcome to the Lobby, ${this.player.name}!` }];

    this.stompClient.publish({ destination: '/app/lobby/player/new', body: JSON.stringify(this.player) });

    window.document.addEventListener('beforeunload', () => {
      console.log('beforeDestroy called');
      this.stompClient.publish({ destination: '/app/lobby/player/deleted', body: JSON.stringify(this.player) });
    });

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

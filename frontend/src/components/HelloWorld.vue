<template>
  <div>
    <ul v-for="player in players" :key="player.name">
      <li>
        <span>
          {{ player.name }} --
        </span>
        <div>
          Hand: {{ player.getHand() }}
        </div>
        <div>
          Cards: {{ player.getHand().getCards() }}
        </div>
        <div v-for="card in player.getHand().getCards()" :key="card.cardName">
          {{ card }}
        </div>
        <button @click="connect">Connect</button>
        <button @click="disconnect">Disconnect</button>
        <button @click="subscribe">Subscribe</button>
        <button @click="send('/app/karten', 'sending to /app/karten')">Send to /app/karten</button>
        <button @click="send('/user/karten', 'sending to user/karten')">Send to /user/karten</button>
        <button @click="send('/topic/greetings', 'sending to /topic/greetings')">Send to /topic/greetings</button>
        <button @click="send('/app/hello', 'sending to /app/hello')">Send to /app/hello</button>
        <button @click="send('/app/humba', 'sending to /app/humba')">Send to /app/humba</button>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { TexasHoldEmPokerGameType, Player } from 'typedeck';
import { Client } from '@stomp/stompjs';

@Component
export default class HelloWorld extends Vue {
  deck = new TexasHoldEmPokerGameType().createDeck();

  players = [new Player('Bob'), new Player('Alice'), new Player('Steve')];

  stompClient = new Client({
    brokerURL: 'ws://127.0.0.1:9090/ws',
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
  }

  disconnect() {
    this.stompClient.deactivate();
  }


  subscribe() {
    // this.stompClient.subscribe('/topic/greetings', (message) => console.log(message));
    // this.stompClient.subscribe('/user/queue/humba', (message) => console.log(message));
    this.stompClient.subscribe('/user/queue/karten', (message) => {
      console.log('SOMETHING');
      console.log(message);
    });
    // this.stompClient.subscribe('/user/jonas/humba', (message) => console.log(message));
    // this.stompClient.subscribe('/user/queue/karten', (message) => console.log(message));
    // this.stompClient.subscribe('/user/jonas/karten', (message) => console.log(message));
    this.stompClient.subscribe('/user/queue/error', (message) => console.log(message));
  }

  send(destination: string, body: string) {
    this.stompClient.publish({ destination, body });
  }


  mounted() {
  // deal 2 cards to each player
    this.deck.shuffle();
    this.players.forEach((player) => this.deck.deal(player.getHand(), 2));
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

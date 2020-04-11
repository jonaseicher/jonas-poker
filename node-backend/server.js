var http = require("http");
var StompServer = require('stomp-broker-js');
// var StompServer = require('stompServer');
 
var server = http.createServer();
var stompServer = new StompServer({server: server});
const { TexasHoldEmPokerGameType, Player } = require("typedeck");
 
server.listen(61614);

stompServer.subscribe("/**", function(msg, headers) {
  var topic = headers.destination;
  console.log(topic, "->", msg);
  stompServer.send("/**", {}, "test");
});

// stompServer.send('/test', {}, 'testMsg');

stompServer.onSubscribe = () => console.log('onSubscribe');

stompServer.onClientConnected = () => console.log('onClientConnected');


deal();

function deal() {
  const deck = new TexasHoldEmPokerGameType().createDeck();
  deck.shuffle();
  let players = [new Player('Bob'), new Player('Alice'), new Player('Steve')];
  players.forEach((player) => deck.deal(player.getHand(), 7));

  console.log(`Deck has ${deck.getCount()} remaining cards.`);
  console.log(`Each player has ${players[1].getHand().getCount()} cards.`);
}
<template>
  <v-container>
    <v-row>
      <v-col>
        <!-- <v-text-field label="Name" outlined v-model="chat.player.name" @keyup="chat.changeName"/> -->
        <v-btn v-if="!chat.subscribed" class="mr-2 mb-2" @click="chat.subscribe">Subscribe</v-btn>
        <div class="chat-window pa-3 my-3">
          <div class="message" v-for="message in chat.messages" :key="message.text + message.timestamp">
            {{ message.user }} : {{ message.text }}
          </div>
        </div>
      </v-col>
      <v-col class="col-3">
        <v-list>
          <v-subheader>USERS IN CHAT</v-subheader>
          <v-list-item-group
          v-model="selected"
          multiple
          active-class="pink--text"
        >
          <v-list-item v-for="player in chat.players" :key="player.id">
            <template v-slot:default="{ active }">
              <v-list-item-content>
                <v-list-item-title>{{ player.name }} </v-list-item-title>
                <!-- <v-list-item-subtitle class="text--primary">Other title</v-list-item-subtitle>
                <v-list-item-subtitle>Subtitle</v-list-item-subtitle> -->
              </v-list-item-content>

              <v-list-item-action>
                <!-- <v-list-item-action-text>Add to favorites</v-list-item-action-text> -->
                <v-icon
                  v-if="!active"
                  color="grey lighten-1"
                >
                  mdi-star-outline
                </v-icon>

                <v-icon
                  v-else
                  color="yellow"
                >
                  mdi-star
                </v-icon>
              </v-list-item-action>
            </template>
          </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-col>
    </v-row>
    <v-text-field outlined
      v-if="chat.subscribed"
      v-model="chat.chatInputMessage"
      @keyup.enter="chat.sendChatMessage"
    />
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import chatModule from '../store/ChatModule';
import stompModule from '../store/StompModule';

@Component
export default class ChatComponent extends Vue {
  selected = [];

  get chat() {
    return chatModule;
  }

  created() {
    if (!chatModule.subscribed) {
      chatModule.subscribe();
    }
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

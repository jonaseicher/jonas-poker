<template>
  <v-container>
    <v-data-table
      :headers="headers"
      :items="tables"
      sort-by="tableName"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Poker Tables</v-toolbar-title>
          <!-- <v-divider
            class="mx-4"
            inset
            vertical
          ></v-divider>
          <v-spacer></v-spacer> -->
        </v-toolbar>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-btn @click="joinTable(item)">
          <v-icon class="mr-2">mdi-login</v-icon>
          Join
        </v-btn>
      </template>
    </v-data-table>
    {{ tables }}
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import stompModule from '../store/StompModule';


@Component
export default class TableLobby extends Vue {
  tables = [];

  headers = [
    {
      text: 'Table Name',
      align: 'start',
      value: 'tableName',
    },
    { text: 'Players', value: 'players.length' },
    { text: 'Actions', value: 'actions', sortable: false },
    // { text: 'Started', value: 'started' },
    { text: 'State', value: 'state' },
    // { text: 'Game Over', value: 'gameOver' },
  ];

  joinTable(item: any) {
    console.log('join table');
  }

  created() {
    stompModule.subscribe({
      destination: '/lobby/tables',
      callback: (message) => {
        console.log('lobby/tables', message);
        this.tables = JSON.parse(message.body);
      },
    });
    stompModule.publish('/app/lobby/tables', {});
  }
}

</script>

<style scoped>

</style>

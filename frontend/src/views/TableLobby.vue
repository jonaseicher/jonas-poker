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
          1
        </v-btn>
        <v-btn @click="joinTable2(item)">
          <v-icon class="mr-2">mdi-login</v-icon>
          2
        </v-btn>
        <v-btn @click="joinTable3(item)">
          <v-icon class="mr-2">mdi-login</v-icon>
          3
        </v-btn>
      </template>
    </v-data-table>
    {{ tables }}
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import stompModule from '../store/StompModule';
import pokerModule from '../store/PokerModule';


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

  joinTable(table: any) {
    console.log('Joining Table:', table.tableName);
    this.$router.push({ path: `/table/${table.tableName}` });
  }

  joinTable2(table: any) {
    console.log('Joining Table2:', table.tableName);
    this.$router.push({ path: `/table2/${table.tableName}` });
  }

  joinTable3(table: any) {
    console.log('Joining Table3:', table.tableName);
    this.$router.push({ path: `/table3/${table.tableName}` });
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

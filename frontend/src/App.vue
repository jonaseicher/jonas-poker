<template>
  <v-app id="jonas-poker">
    <v-navigation-drawer v-model="drawer" app clipped>
      <v-list dense>
        <router-link to="/" tag="v-list-item">
          <v-list-item link>
            <v-list-item-action>
              <v-icon>mdi-view-dashboard</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>Dashboard</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </router-link>
        <router-link to="/chat" tag="v-list-item">
          <v-list-item link>
            <v-list-item-action>
              <v-icon>mdi-chat</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>Chat</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </router-link>
        <router-link to="/lobby" tag="v-list-item">
          <v-list-item link>
            <v-list-item-action>
              <v-icon>mdi-table-chair</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>Lobby</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </router-link>
        <!-- <router-link to="/table" tag="v-list-item">
          <v-list-item link>
            <v-list-item-action>
              <v-icon>mdi-cards</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>Poker</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </router-link> -->
        <!-- <router-link to="/about" tag="v-list-item">
          <v-list-item link>
            <v-list-item-action>
              <v-icon>mdi-card-account-details</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>My Profile</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </router-link> -->
        <!-- <router-link to="/cards" tag="v-list-item">
          <v-list-item link>
            <v-list-item-action>
              <v-icon>mdi-cards</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>Cards (Test)</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </router-link>
        <router-link to="/player" tag="v-list-item">
          <v-list-item link>
            <v-list-item-action>
              <v-icon>mdi-card-account-details</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>Player (Test)</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </router-link> -->
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app clipped-left>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-toolbar-title>Jonas-Poker <v-icon large>mdi-cards</v-icon></v-toolbar-title>

      <v-spacer></v-spacer>

      <AccountComponent />

    </v-app-bar>

    <v-content>
      <v-container class="fill-height" fluid>
        <router-view></router-view>
        <!-- <v-row align="center" justify="center">
          <v-icon large>mdi-chat</v-icon>
          <span class="px-4">Welcome!</span>
          <v-icon large>mdi-cards</v-icon>
        </v-row> -->
      </v-container>
    </v-content>

    <v-footer app>
      <span>Made with <v-icon small color="red" class="pb-1">mdi-heart</v-icon> in Munich!</span>
    </v-footer>
  </v-app>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import AccountComponent from './views/AccountComponent.vue';

@Component({
  components: {
    AccountComponent,
  },
})
export default class App extends Vue {
  drawer = false;

  created() {
    this.$vuetify.theme.dark = true;
  }
}
</script>

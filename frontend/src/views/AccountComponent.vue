<template>
  <div>
    <v-menu v-if="auth.isAuthenticated" offset-y>
      <template v-slot:activator="{ on }">
        <v-btn v-on="on">
          <v-img v-if="auth.user.picture" :src="auth.user.picture" max-width="30px"></v-img>
          <v-icon v-else>mdi-account</v-icon>
          <v-spacer class="px-2"></v-spacer>
          {{ auth.user.name }}
        </v-btn>
      </template>
      <v-list>
        <v-list-item @click="logout">
          <v-list-item-icon>
            <v-icon>mdi-logout</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Logout</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
    <v-btn v-else @click="login">
       <v-icon class="pr-2">mdi-login</v-icon>
      Login
    </v-btn>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';


@Component
export default class AccountComponent extends Vue {
  get auth() {
    return this.$auth;
  }

  login() {
    this.$auth.loginWithRedirect();
  }

  logout() {
    this.$auth.logout({
      returnTo: window.location.origin,
    });
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

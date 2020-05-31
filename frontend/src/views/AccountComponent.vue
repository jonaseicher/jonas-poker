<template>
  <div>
    <v-btn v-if="auth.isAuthenticated" @click="logout">
      <v-img v-if="auth.user.picture" :src="auth.user.picture" max-width="30px"></v-img>
      <v-icon v-else>mdi-account</v-icon>
      <v-spacer class="px-2"></v-spacer>
      {{ auth.user.name }}
    </v-btn>
    <v-btn v-else @click="login">
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

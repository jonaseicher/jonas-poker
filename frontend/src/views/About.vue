<template>
  <div class="fill-height">
    <h1>This is an about page</h1>
    <h2>Text</h2>
    <h3>ChatBox</h3>
    <h4>type here</h4>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Home extends Vue {

}
</script>

<style scoped>

div {
  background-color: darkgrey;
  display: flex;
  flex-direction: column;
  /* align-items: flex-end; */
  /* justify-content: flex-end; */
}

h1 {
  background-color: darkgreen;
}
h2 {
  background-color: rgb(10, 0, 60);
}
h3 {
  background-color: rgb(36, 33, 0);
  flex-grow: 1;
}
h4 {
  background-color: rgb(44, 8, 29);
  /* align-self: end; */
}
</style>

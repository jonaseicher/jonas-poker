<template>
  <span>
    <v-tooltip  bottom>
      <template v-slot:activator="{ on }">
        <v-chip color="grey darken-4" v-on="on" label large class="headline ma-1">
          <span v-if="icon">
            <v-icon large class="mr-2">{{ icon }}</v-icon>
          </span>
          <span>{{ text }}</span>
        </v-chip>
      </template>
      <span v-if="tooltip">{{ tooltip }}</span>
    </v-tooltip>
  </span>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component
export default class PlayerStatusBar extends Vue {
  @Prop()
  text!: string;

  @Prop()
  tooltip!: string;

  @Prop()
  icon!: string
}
</script>

import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store/store';
import vuetify from './plugins/vuetify';

// eslint-disable-next-line @typescript-eslint/no-var-requires
const VueChatScroll = require('vue-chat-scroll');

Vue.use(VueChatScroll);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount('#app');

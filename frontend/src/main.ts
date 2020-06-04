import Vue from 'vue';
import vuetify from './plugins/vuetify';
import App from './App.vue';
import router from './router';
import store from './store/store';
import { Auth0Plugin } from './auth/index';

Vue.use(Auth0Plugin, {
  domain: 'dev-3lnv-vrr.eu.auth0.com',
  clientId: '9wQDflrHGcuj5KuLvj2KMndatmz7bpLc',
  // onRedirectCallback: appState => {
  //   router.push(
  //     appState && appState.targetUrl
  //       ? appState.targetUrl
  //       : window.location.pathname
  //   );
  // }
});

Vue.config.productionTip = false;

new Vue({
  vuetify,
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');

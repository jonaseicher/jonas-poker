import Vue from 'vue';
import VueRouter, { RouteConfig } from 'vue-router';
import Dashboard from '../views/Dashboard.vue';

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'Dashboard',
    component: Dashboard,
  },
  {
    path: '/chat',
    name: 'Chat',
    component: () => import(/* webpackChunkName: "chat" */ '../views/ChatComponent.vue'),
  },
  {
    path: '/lobby',
    name: 'TableLobby',
    component: () => import(/* webpackChunkName: "table" */ '../views/TableLobby.vue'),
  },
  {
    path: '/table/:tableName',
    props: true,
    name: 'PokerTable',
    component: () => import(/* webpackChunkName: "table" */ '../views/PokerTable.vue'),
  },
  {
    path: '/about',
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;

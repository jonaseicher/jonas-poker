import Vue from 'vue';
import VueRouter, { RouteConfig } from 'vue-router';
import Dashboard from '../views/Dashboard.vue';

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'dashboard',
    component: Dashboard,
  },
  {
    path: '/chat',
    name: 'chat',
    component: () => import(/* webpackChunkName: "chat" */ '../views/ChatComponent.vue'),
  },
  {
    path: '/lobby',
    name: 'lobby',
    component: () => import(/* webpackChunkName: "table" */ '../views/TableLobby.vue'),
  },
  {
    path: '/table/:tableName',
    props: true,
    name: 'table',
    component: () => import(/* webpackChunkName: "table" */ '../views/PokerTable.vue'),
  },
  {
    path: '/table2/:tableName',
    props: true,
    name: 'table2',
    component: () => import(/* webpackChunkName: "table" */ '../views/PokerTable2.vue'),
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
  },
  {
    path: '/cards',
    name: 'cards',
    component: () => import(/* webpackChunkName: "about" */ '../views/PokerCard.vue'),
  },
  {
    path: '/player',
    name: 'player',
    component: () => import(/* webpackChunkName: "about" */ '../views/PlayerCard.vue'),
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;

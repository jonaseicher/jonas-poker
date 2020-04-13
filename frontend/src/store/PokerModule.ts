import {
  VuexModule, Module, Action, Mutation,
} from 'vuex-class-modules';
import store from './store';

@Module
class PokerModule extends VuexModule {

}

const pokerModule = new PokerModule({ store, name: 'poker' });
export default pokerModule;

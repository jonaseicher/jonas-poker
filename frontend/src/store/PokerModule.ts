import {
  VuexModule, Module, Action, Mutation,
} from 'vuex-class-modules';
import Customer from '../models/Customer';
import CustomerService from '@/services/Customer.service';
import store from './store';
import TaCoDetails from '@/models/Taco';

@Module
class CustomerModule extends VuexModule {
  customer: Customer = new Customer();

  loading: boolean = false;

  private customerService = new CustomerService();

  @Action
  async loadCustomer(subscriptionId: number) {
    this.setCustomer(new Customer());
    this.setLoading(true);
    const response = await this.customerService.getCustomerBasicData(subscriptionId);
    const customer = response.data;
    this.setCustomer(customer);
    this.setLoading(false);
  }

  @Mutation
  setLoading(loading: boolean) {
    this.loading = loading;
  }


  @Mutation
  setCustomer(customer: Customer) {
    this.customer = customer;
  }
}

const customerModule = new CustomerModule({ store, name: 'customer' });
export default customerModule;

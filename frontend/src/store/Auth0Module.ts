import {
  VuexModule, Module, Action, Mutation, RegisterOptions,
} from 'vuex-class-modules';
import createAuth0Client, { Auth0Client, Auth0ClientOptions } from '@auth0/auth0-spa-js';
import AuthState from '@/model/AuthState';
import Auth0User from '@/model/Auth0User';
import store from './store';


@Module
class Auth0Module extends VuexModule {
  /** Define a default action to perform after authentication */
  DEFAULT_REDIRECT_CALLBACK = () => window.history.replaceState({}, document.title, window.location.pathname);

  onRedirectCallback = this.DEFAULT_REDIRECT_CALLBACK;

  loading = true;

  isAuthenticated = false;

  user = new Auth0User();

  options: Auth0ClientOptions = {
    domain: 'dev-3lnv-vrr.eu.auth0.com',
    // eslint-disable-next-line
    client_id: '9wQDflrHGcuj5KuLvj2KMndatmz7bpLc',
    // audience,
    // eslint-disable-next-line
    redirect_uri: 'http://localhost:8180',
  }

  auth0Client: Auth0Client = new Auth0Client(this.options);

  popupOpen = false;

  error = null;

  constructor(options: RegisterOptions) {
    super(options);
    this.created();
  }

  /** Authenticates the user using a popup window */
  @Action
  async loginWithPopup(o: any) {
    if (this.auth0Client == null) return;

    this.popupOpen = true;

    try {
      await this.auth0Client.loginWithPopup(o);
    } catch (e) {
      console.error(e);
    } finally {
      this.popupOpen = false;
    }

    this.user = await this.auth0Client.getUser();
    this.isAuthenticated = true;
  }

  /** Handles the callback when logging in using a redirect */
  @Action
  async handleRedirectCallback() {
    if (this.auth0Client == null) return;
    this.loading = true;
    try {
      await this.auth0Client.handleRedirectCallback();
      this.user = await this.auth0Client.getUser();
      this.isAuthenticated = true;
    } catch (e) {
      this.error = e;
    } finally {
      this.loading = false;
    }
  }

  /** Authenticates the user using the redirect method */
  @Action
  loginWithRedirect(o: any) {
    console.log('auth0Client', this.auth0Client);
    console.log(o);
    if (this.auth0Client == null) return null;
    return this.auth0Client.loginWithRedirect(o);
  }

  /** Returns all the claims present in the ID token */
  @Action
  getIdTokenClaims(o: any) {
    if (this.auth0Client == null) return null;
    return this.auth0Client.getIdTokenClaims(o);
  }

  /** Returns the access token. If the token is invalid or missing, a new one is retrieved */
  @Action
  getTokenSilently(o: any) {
    if (this.auth0Client == null) return null;
    return this.auth0Client.getTokenSilently(o);
  }

  /** Gets the access token using a popup window */
  @Action
  getTokenWithPopup(o: any) {
    if (this.auth0Client == null) return null;
    return this.auth0Client.getTokenWithPopup(o);
  }

  /** Logs the user out and removes their session on the authorization server */
  @Action
  logout(o: any) {
    if (this.auth0Client == null) return null;
    return this.auth0Client.logout(o);
  }

  /** Use this lifecycle method to instantiate the SDK client */
  @Action
  async created() {
    console.log('created auth0 module called');
    this.auth0Client = await createAuth0Client(this.options);
    console.log('auth0 client', this.auth0Client);

    try {
    // If the user is returning to the app after authentication..
      if (
        window.location.search.includes('code=')
      && window.location.search.includes('state=')
      ) {
      // handle the redirect and retrieve tokens
        const { appState } = await this.auth0Client.handleRedirectCallback();

        // Notify subscribers that the redirect callback has happened, passing the appState
        // (useful for retrieving any pre-authentication state)
        this.onRedirectCallback();
      }
    } catch (e) {
      this.error = e;
    } finally {
      // Initialize our internal authentication state
      this.initAuthState({ isAuthenticated: await this.auth0Client.isAuthenticated(), user: await this.auth0Client.getUser() });
    }
  }

  @Mutation
  initAuthState(authState: AuthState) {
    console.log(authState);
    this.isAuthenticated = authState.isAuthenticated;
    this.user = authState.user;
    console.log('user', this.user);
    this.loading = false;
  }
}

const auth0Module = new Auth0Module({ store, name: 'auth' });
export default auth0Module;

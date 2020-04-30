import Auth0User from './Auth0User';

export default class AuthState {
  isAuthenticated = false;

  user: Auth0User = new Auth0User();
}

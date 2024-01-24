export const LOGIN = 'LOGIN';
export const LOGOUT = 'LOGOUT';

interface LoginAction {
  type: typeof LOGIN;
  isLogged: boolean;
}

interface LogoutAction {
  type: typeof LOGOUT;
  isLogged: boolean;
}

export type AuthenticationActionTypes = LoginAction | LogoutAction;

// Redux/Actions/authenticationActions.tsx
import { LOGIN, LOGOUT, AuthenticationActionTypes } from '../Types';

export function login(): AuthenticationActionTypes {
  return {
    type: LOGIN,
    isLogged: true,
  };
}

export function logout(): AuthenticationActionTypes {
  return {
    type: LOGOUT,
    isLogged: false,
  };
}

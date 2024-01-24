// Redux/Reducers/authenticationReducer.tsx
import { LOGIN, LOGOUT, AuthenticationActionTypes } from '../Types';

const initialState = {
  isLogged: false,
};

export function authenticationReducer(
  state = initialState,
  action: AuthenticationActionTypes
) {
  switch (action.type) {
    case LOGIN:
      return {
        ...state,
        isLogged: action.isLogged,
      };
    case LOGOUT:
      return {
        ...state,
        isLogged: action.isLogged,
      };
    default:
      return state;
  }
}

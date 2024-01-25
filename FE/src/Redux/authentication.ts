import {
  createSlice,
  //   createAsyncThunk,
  PayloadAction,
  configureStore,
} from '@reduxjs/toolkit';
import { LoginData, LoginState } from './Types';

const initialMemberState: LoginState = {
  token: null,
};
export const setLoginData = createSlice({
  name: 'authentication',
  initialState: initialMemberState,
  reducers: {
    login: (state, action: PayloadAction<LoginData | null>) => {
      console.log(state.token);
      if (
        action.payload?.memberLoginId === 'ssafy' &&
        action.payload?.memberLoginPassword === '1234'
      ) {
        state.token = 'tmp';
        console.log('Success');
        console.log(state.token);
      } else {
        console.log('Fail');
        console.log(state.token);
      }
    },
    logout: (state) => {
      console.log(state.token);
      state.token = null;
    },
  },
});

export const { login, logout } = setLoginData.actions;

export const store = configureStore({
  reducer: {
    member: setLoginData.reducer,
  },
});

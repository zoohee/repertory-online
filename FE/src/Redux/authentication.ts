import {
  createSlice,
  //   createAsyncThunk,
  PayloadAction,
  configureStore,
} from '@reduxjs/toolkit';
import { LoginData, memberState } from './Types';

const initialMemberState: memberState = {
  token: localStorage.getItem('token'),
  memberName: null,
  memberProfile: null,
};
export const setLoginData = createSlice({
  name: 'authentication',
  initialState: initialMemberState,
  reducers: {
    login: (state, action: PayloadAction<LoginData | null>) => {
      if (
        action.payload?.memberLoginId === 'ssafy' &&
        action.payload?.memberLoginPassword === '1234'
      ) {
        localStorage.setItem('token', 'Token');
      } else {
        console.log('Fail');
        console.log(state.token);
      }
    },
    logout: (state) => {
      localStorage.removeItem('token');
    },
  },
});

export const { login, logout } = setLoginData.actions;

export const store = configureStore({
  reducer: {
    member: setLoginData.reducer,
  },
});

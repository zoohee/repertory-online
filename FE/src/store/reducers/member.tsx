import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface MemberState {
  isLogged: boolean;
}

interface LoginPayLoad {
  memberLoginId: string;
  memberLoginPassword: string;
}

const initialState: MemberState = {
  isLogged: false,
};
export const counterSlice = createSlice({
  name: 'member',
  initialState,
  reducers: {
    login: (state, action: PayloadAction<LoginPayLoad>) => {
      console.log(`[Before] : ${state.isLogged}`);
      if (
        action.payload.memberLoginId === 'ssafy' &&
        action.payload.memberLoginPassword === '1234'
      ) {
        state.isLogged = true;
        console.log('[Login Successed]');
      } else {
        console.log('[Login Failed]');
      }
      console.log(`[After] : ${state.isLogged}`);
    },
    logout: (state) => {
      console.log(`[Before] : ${state.isLogged}`);
      state.isLogged = false;
      console.log(`[After] : ${state.isLogged}`);
    },
  },
});

export const { login, logout } = counterSlice.actions;

export default counterSlice.reducer;

import { createSlice } from '@reduxjs/toolkit';

export const counterSlice = createSlice({
  name: 'member',
  initialState: {
    isLogged: false,
  },
  reducers: {
    login: (state, action) => {
      console.log(`[Before] : ${state.isLogged}`);
      if (
        action.payload.memberLoginId === 'ssafy' &&
        action.payload.memberLoginPassword === '1234'
      ) {
        state.value = true;
        console.log('[Login Successed]');
      } else {
        console.log('[Login Failed]');
      }
      console.log(`[After] : ${state.isLogged}`);
    },
    logout: (state) => {
      console.log(`[Before] : ${state.isLogged}`);
      state.value = false;
      console.log(`[After] : ${state.value}`);
      return 'Return Fail...';
    },
  },
});

export const { login, logout } = counterSlice.actions;

export default counterSlice.reducer;

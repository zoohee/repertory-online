import { configureStore } from '@reduxjs/toolkit';
import member from './reducers/member';
export default configureStore({
  reducer: {
    member: member,
  },
});

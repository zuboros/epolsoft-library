import { configureStore } from "@reduxjs/toolkit"
import bookReducer from './reducers/bookSlice';
import topicReducer from "./reducers/topicSlice";
import authReducer from './reducers/authSlice'
import userReducer from './reducers/userSlice'
import { USERS } from './entitiesConst'

export default configureStore({
   reducer: {
      books: bookReducer,
      topics: topicReducer,
      auth: authReducer,
      [USERS]: userReducer,
   }
});
import { configureStore } from "@reduxjs/toolkit"
import bookReducer from './reducers/bookSlice';
import topicReducer from "./reducers/topicSlice";
import authReducer from './reducers/userSlice'

export default configureStore({
   reducer: {
      books: bookReducer,
      topics: topicReducer,
      auth: authReducer,
   }
});
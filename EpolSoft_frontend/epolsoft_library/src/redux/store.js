import { configureStore } from "@reduxjs/toolkit"
import bookReducer from './reducers/bookSlice';
import topicReducer from "./reducers/topicSlice";

export default configureStore({
   reducer: {
      books: bookReducer,
      topics: topicReducer
   }
});
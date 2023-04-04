import { configureStore } from "@reduxjs/toolkit"
import bookReducer from './bookSlice';
import topicReducer from "./topicSlice";

export default configureStore({
   reducer: {
      books: bookReducer,
      topics: topicReducer,
   }
});
import { createSlice } from '@reduxjs/toolkit';

const bookSlice = createSlice({
   name: 'books',
   initialState: {
      books: []
   },
   reducers: {
      addBook(state, action) {
         console.log(action);

         state.books.push({
            id: new Date().toISOString(),
            title: action.payload.title
         });
      },
      removeBook(state, action) {
         state.books = state.books.filter(book => book.id !== action.payload.id);
      }
   }
});


export const { addBook, removeBook } = bookSlice.actions;

export default bookSlice.reducer;
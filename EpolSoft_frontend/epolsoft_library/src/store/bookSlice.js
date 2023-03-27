import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';

export const fetchBooks = createAsyncThunk(
   'books/fetchBooks',
   async function (_, { rejectWithValue }) {
      try {

         const response = await fetch('https://jsonplaceholder.typicode.com/posts?_limit=10');

         if (!response.ok) {
            throw new Error('Can\'t extract any elements');
         }

         const data = await response.json();
         console.log(data);
         return data;

      } catch (error) {
         return rejectWithValue(error.message)
      }
   }
);

export const deleteBook = createAsyncThunk(
   'book/deleteBook',
   async function (id, { rejectWithValue, dispatch }) {
      try {
         const response = await fetch(`https://jsonplaceholder.typicode.com/todos/${id}`, {
            method: 'DELETE',
         });
         console.log(response);

         if (!response.ok) {
            throw new Error('Can\'t delete element. Server error.');
         }

         dispatch(removeBook({ id }));

      } catch (error) {
         return rejectWithValue(error.message);
      }
   }
);

export const addNewBook = createAsyncThunk(
   'book/deleteBook',
   async function (title, { rejectWithValue, dispatch }) {
      try {

         const book = {
            title: title,
            id: 1,
         }

         const response = await fetch(`https://jsonplaceholder.typicode.com/todos`, {
            method: 'POST',
            headers: {
               'Content-Type': 'application/json',
            },
            body: JSON.stringify(book)
         });

         if (!response.ok) {
            throw new Error('Can\'t add element. Server error.');
         }

         const data = await response.json();
         console.log(data);
         dispatch(addBook(data));

      } catch (error) {
         return rejectWithValue(error.message);
      }
   }
);

const setError = (state, action) => {
   state.status = 'rejected';
   state.error = action.payload;
}

const bookSlice = createSlice({
   name: 'books',
   initialState: {
      books: [],
      status: null,
      error: null,
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
   },
   extraReducers: {
      [fetchBooks.pending]: (state) => {
         state.status = 'loading';
         state.error = null;
      },
      [fetchBooks.fulfilled]: (state, action) => {
         state.status = 'resolve';
         state.books = action.payload;
      },
      [fetchBooks.rejected]: setError,
      [deleteBook.rejected]: setError,
   }
});


export const { addBook, removeBook } = bookSlice.actions;

export default bookSlice.reducer;
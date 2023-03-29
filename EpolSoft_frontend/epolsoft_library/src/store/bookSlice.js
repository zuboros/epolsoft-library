import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { axios } from "../axios"
import { books } from "../API/serverData";

/// DON'T USE IT !!!
export const fetchBooks = createAsyncThunk(
   'books/fetchBooks',
   async function (_, { rejectWithValue }) {
      try {

         const response = await axios.get('?_limit=10');

         if (!response.status === 200) {
            throw new Error('Can\'t extract any elements');
         }

         const data = await response.data;
         return data;

      } catch (error) {
         return rejectWithValue(error.message)
      }
   }
);

/// DON'T USE IT !!!
export const deleteBook = createAsyncThunk(
   'book/deleteBook',
   async function (id, { rejectWithValue, dispatch }) {
      try {
         const response = await axios.delete(`/${id}`);
         console.log(response);

         if (!response.status === 200) {
            throw new Error('Can\'t delete element. Server error.');
         }

         dispatch(removeBook({ id }));

      } catch (error) {
         return rejectWithValue(error.message);
      }
   }
);

/// DON'T USE IT !!!
export const addNewBook = createAsyncThunk(
   'book/addNewBook',
   async function (newBook, { rejectWithValue, dispatch }) {
      try {

         const book = {
            title: newBook.title,
            userId: newBook.userId,
            body: newBook.body
         }

         const response = await axios.post(``, book);
         console.log(response);
         if (!response.status === 201) {
            throw new Error('Can\'t add element. Server error.');
         }

         const data = await response;
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
      fetchLocalBooks(state, action) {
         state.books.push(...books)
         console.log(state.books);
      },
      addBook(state, action) {
         console.log(action);
         state.books.push({
            id: new Date().toISOString(),
            ...action.payload
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


export const { fetchLocalBooks, addBook, removeBook } = bookSlice.actions;

export default bookSlice.reducer;
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { axios } from "../pidaras"
import { books, books2, getAllBooks } from "../API/serverData";

const serverResponse = async (data) => { setTimeout((e) => (e), 2000); return { data: data } };


export const fetchBooks = createAsyncThunk(
   'books/fetchBooks',
   async function ({ page, pageSize }, { rejectWithValue }) {
      try {

         /* const response = await axios.get('');

         if (!response.status === 200) {
            throw new Error('Can\'t extract any elements');
         } */

         //const data = await response.data;
         console.log('FETCH');

         const { data } = await serverResponse(getAllBooks.slice(pageSize * page - pageSize, pageSize * page % 7));

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
         /* const response = await axios.delete(`/${id}`);
         console.log(response);

         if (!response.status === 200) {
            throw new Error('Can\'t delete element. Server error.');
         } */
         console.log('Delete');

         const { data } = await serverResponse(id);
         //console.log(data);


         dispatch(removeBook({ id: data.id }));

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
         //console.log(newBook);

         /* const formData = new FormData();
         formData.append("file", newBook.uploadFiles[0]);
         const response = await axios.post('upload_file', formData, {
            headers: {
               'Content-Type': 'multipart/form-data'
            }
         });

         if (!response.status === 200) {
            throw new Error('Can\'t post the file');
         }
         else {

            const { topic, uploadFiles, ...rest } = newBook;

            const book = {
               ...rest,
               fileName: response.data.fileName
            }

            const response = await axios.post(``, book);

            if (!response.status === 200) {
               throw new Error('Can\'t add element. Server error.');
            }

            const { data } = response;
            dispatch(addBook(data));

         }
 */
         const { uploadFiles, ...rest } = newBook;

         const book = {
            ...rest,
            fileName: newBook.uploadFiles[0].name
         }
         console.log(book);

         dispatch(addBook(book));

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
         state.books = books;
      },
      addBook(state, action) {
         state.books.push(action.payload);
      },
      editBook(state, action) {
         console.log(action.payload);

         state.books = state.books.map(book => book.id === action.payload.id ? { ...book, ...action.payload } : book);
      },
      sortBook(state, action) {
         state.books = books2;
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


export const { fetchLocalBooks, addBook, removeBook, editBook, sortBook } = bookSlice.actions;

export default bookSlice.reducer;
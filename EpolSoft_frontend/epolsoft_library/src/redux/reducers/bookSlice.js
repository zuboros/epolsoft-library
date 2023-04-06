import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
//import { axios } from "../axios"
import { books, books2, getAllBooks } from "../../data/serverData";
import { createRequest } from '../../common/requestGenerator';
import * as axios from "../../lib/actionAxiosTypes";
import * as entities from "../entitiesConst"

const serverResponse = async (data) => { setTimeout((e) => (e), 2000); return { data: data } };

const initialState = {
   [entities.BOOKS]: [],
   status: null,
   error: null,
   loading: null,
   deleteLoading: null,
   postLoading: null,
   totalBooks: null,
}

export const fetchBooks = createAsyncThunk(
   `${entities.BOOKS}/${entities.FETCH_BOOKS}`,
   async function ({ pageNum, pageSize, sortOrder, sortField }, { rejectWithValue }) {
      try {

         const data = await createRequest({
            method: axios.GET, url: axios.PATH_GET_BOOKS_WITH_PARAMS({ pageNum, pageSize, sortOrder, sortField }),
            postCallback: (data) => {
               console.log(axios.GET);                      ///
               console.log(data.data);                   ///
               return data.data;
            }
         });

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


export const postBook = createAsyncThunk(
   'book/postBook',
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

         const id = new Date().toISOString();

         const book = {
            key: id,
            id: id,
            ...rest,
            authorName: rest.author,
            topicName: rest.topic,
            fileName: newBook.uploadFiles[0].name
         }
         console.log(book);

         dispatch(addBook(book));

      } catch (error) {
         return rejectWithValue(error.message);
      }
   }
);

export const putBook = createAsyncThunk(
   'book/putBook',
   async function (newBook, { rejectWithValue, dispatch }) {
      try {
         console.log(newBook);

         console.log('put');

         dispatch(editBook(newBook));

      } catch (error) {
         return rejectWithValue(error.message);
      }
   }
);

export const extractData = async (dispatch, queryParams) => {
   console.log('query address: ' + axios.PATH_GET_BOOKS_WITH_PARAMS(queryParams));
   await createRequest({
      preCallback: () => dispatch(setLoading(false)),
      method: axios.GET, url: axios.PATH_GET_BOOKS_WITH_PARAMS(queryParams),
      postCallback: (data) => {
         console.log(axios.GET);                      ///
         console.log(data);                   ///
         dispatch(setTotalBooks(data.data[1]));
         return data.data[0];
      },
      redux_cfg: {
         dispatch,
         actions: [fetchLocalBooks, setLoading]
      }
   })
}
export const deleteData = async (dispatch, id) =>
   await createRequest({
      preCallback: () => dispatch(setDeleteLoading(false)),
      method: axios.DELETE, url: axios.PATH_DELETE_BOOK({ id }),
      postCallback: () => {
         console.log(axios.DELETE);                                           ///
         console.log("entity: " + id + " was deleted");                    ///
         return { id };
      },
      redux_cfg: {
         dispatch,
         actions: [removeBook, setDeleteLoading]
      }
   })


export const postData = async (dispatch, data) => {

   const sendData = {
      file: data.uploadFiles[0],
      data
   }
   delete sendData.data['uploadFiles'];

   const formData = new FormData
   formData.append(sendData.file.name, sendData.file);

   await createRequest({
      method: axios.POST, url: axios.PATH_UPLOAD_FILE,
      body: {
         data: formData,
      }
   })

   await createRequest({
      method: axios.POST, url: axios.PATH_POST_BOOK,
      body: {
         ...sendData.data,
      },
      postCallback: (dataAfter) => {
         console.log(axios.POST);                                           ///
         const { data } = dataAfter;
         console.log(data);                    ///
         return data;
      },
      redux_cfg: {
         dispatch,
         actions: [addBook]
      }
   })
}


const setError = (state, action) => {
   state.status = 'rejected';
   state.error = action.payload;
}

const bookSlice = createSlice({
   name: entities.BOOKS,
   initialState,
   reducers: {
      fetchLocalBooks(state, action) {
         state.books = action.payload;
      },
      addBook(state, action) {
         state.books.push(action.payload);
      },
      editBook(state, action) {
         state.books = state.books.map(book => book.id === action.payload.id ? { ...book, ...action.payload } : book);
      },
      sortBook(state, action) {
         state.books = books2;
      },
      removeBook(state, action) {
         state.books = state.books.filter(book => book.id !== action.payload.id);
      },
      setLoading(state, action) {
         state.loading = !action.payload;
      },
      setDeleteLoading(state, action) {
         state.deleteLoading = !action.payload;
      },
      setPostLoading(state, action) {
         state.deleteLoading = !action.payload;
      },
      setTotalBooks(state, action) {
         state.totalBooks = action.payload;
      },
   },
   extraReducers: {
      [fetchBooks.pending]: (state) => {
         state.status = 'loading';
         //state.loading = true;
         state.error = null;
      },
      [fetchBooks.fulfilled]: (state, action) => {
         state.status = 'resolve';
         //state.loading = false;
         state[entities.BOOKS] = action.payload;
      },
      [fetchBooks.rejected]: setError,
      [deleteBook.rejected]: setError,
   }
});


export const { fetchLocalBooks, addBook, removeBook, editBook, sortBook, setLoading, setDeleteLoading, setPostLoading, setTotalBooks } = bookSlice.actions;

export default bookSlice.reducer;
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { createRequest, createDownloadRequest } from '../../common/requestGenerator';
import * as axios from "../../lib/actionAxiosTypes";
import * as entities from "../entitiesConst"
import { postBookDto, putBookDto } from '../../services/bookDto'
import myBooks from '../../data/books'

const initialState = {
   [entities.BOOKS]: [],
   status: null,
   error: null,
   loading: null,
   deleteLoading: null,
   postLoading: null,
   totalBooks: null,
   success: null,
}

export const fetchBooks = createAsyncThunk(
   `${entities.BOOKS}/fetchBooks`,
   async function (pageParams, { rejectWithValue, dispatch }) {
      try {

         console.log('query address: ' + axios.PATH_GET_BOOKS_WITH_PARAMS(pageParams));
         await createRequest({
            method: axios.GET, url: axios.PATH_GET_BOOKS_WITH_PARAMS(pageParams),
            postCallback: (dataAfter) => {
               const { data } = dataAfter;
               return myBooks;
            },
            redux_cfg: {
               dispatch,
               actions: [setBooks, setTotalBooks]
            }
         })

      } catch (error) {
         return rejectWithValue(error.message)
      }
   }
);

export const fetchBooksByUserId = createAsyncThunk(
   `${entities.BOOKS}/fetchBooks`,
   async function ({ userId, pageParams }, { rejectWithValue, dispatch }) {
      try {
         console.log('query address: ' + axios.PATH_GET_BOOKS_BY_USER_ID({ id: userId }, pageParams));
         await createRequest({
            method: axios.GET, url: axios.PATH_GET_BOOKS_BY_USER_ID({ id: userId }, pageParams),
            postCallback: (dataAfter) => {
               const { data } = dataAfter;
               return data;
            },
            redux_cfg: {
               dispatch,
               actions: [setBooks, setTotalBooks]
            }
         })

      } catch (error) {
         return rejectWithValue(error.message)
      }
   }
);

export const deleteBook = createAsyncThunk(
   `${entities.BOOKS}/deleteBook`,
   async ({ id }, { rejectWithValue }) => {
      try {
         await createRequest({
            method: axios.DELETE, url: axios.PATH_DELETE_FILE({ id }),
         })
         await createRequest({
            method: axios.DELETE, url: axios.PATH_DELETE_BOOK({ id }),
         })

      } catch (error) {
         if (error.response && error.response.data.message) {
            return rejectWithValue(error.response.data.message)
         } else {
            return rejectWithValue(error.message)
         }
      }
   }
);

export const postBook = createAsyncThunk(
   `${entities.BOOKS}/postBook`,
   async function (data, { rejectWithValue }) {
      try {
         const formData = new FormData();
         formData.append("file", data.fileList[0]);

         const responseData = await createRequest({
            method: axios.POST, url: axios.PATH_UPLOAD_FILE,
            body: formData,
            axios_cfg: {
               "Content-Type": "multipart/form-data"
            },
            postCallback: (dataAfter) => {
               const { data } = dataAfter;
               return data;
            }
         })

         await createRequest({
            method: axios.POST, url: axios.PATH_POST_BOOK,
            body: {
               ...postBookDto(data, { name: data.fileList[0].name, path: responseData }),
            },
            postCallback: (dataAfter) => {
               console.log(axios.POST);                                           ///
               const { data } = dataAfter;
               console.log(data);                    ///
               return data;
            }
         })

      } catch (error) {
         return rejectWithValue(error.message);
      }
   }
);

export const putBook = createAsyncThunk(
   `${entities.BOOKS}/putBook`,
   async function (data, { rejectWithValue }) {
      try {
         let isExist = data.fileList.length;
         const formData = new FormData();
         formData.append("file", data.fileList[0]);

         const responseData = isExist ? await createRequest({
            method: axios.POST, url: axios.PATH_UPLOAD_FILE,
            body: formData,
            axios_cfg: {
               "Content-Type": "multipart/form-data"
            },
            postCallback: (dataAfter) => {
               const { data } = dataAfter;
               return data;
            }
         })
            :
            null;

         await createRequest({
            method: axios.PUT, url: axios.PATH_PUT_BOOK,
            body: {
               ...putBookDto(data, { name: data?.fileList[0]?.name, path: responseData }, isExist),
            },
            postCallback: (dataAfter) => {
               console.log(axios.POST);                                           ///
               const { data } = dataAfter;
               console.log(data);                    ///
               return data;
            }
         })

      } catch (error) {
         return rejectWithValue(error.message);
      }
   }
);

export const fileDownload = createAsyncThunk(
   `${entities.BOOKS}/fileDownload`,
   async function ({ id }, { rejectWithValue }) {
      try {

         createDownloadRequest({
            url: axios.PATH_EXTRACT_FILE({ id }),
            postCallback: (response => {
               const href = URL.createObjectURL(response.data);
               console.log('response');
               console.log(response);
               console.log(response.data.type.split('/'));

               let type;
               switch (response.data.type.split('/')[1]) {
                  case "pdf":
                     type = "pdf";
                     break;
                  default:
                     type = "txt";
                     break;
               }

               // create "a" HTML element with href to file & click
               const link = document.createElement('a');
               link.href = href;
               link.setAttribute('download', `file.${type}`); //or any other extension
               document.body.appendChild(link);
               link.click();

               // clean up "a" element & remove ObjectURL
               document.body.removeChild(link);
               URL.revokeObjectURL(href);
            })
         })

      } catch (error) {
         return rejectWithValue(error.message);
      }
   }
);

export const approveBook = createAsyncThunk(
   `${entities.BOOKS}/approveBook`,
   async function ({ id }, { rejectWithValue }) {
      try {
         await createRequest({
            method: axios.POST, url: axios.PATH_SET_STATUS_WAIT_APPROVING({ id }),
         })

      } catch (error) {
         return rejectWithValue(error.message);
      }
   }
);

const bookSlice = createSlice({
   name: entities.BOOKS,
   initialState,
   reducers: {
      setBooks(state, { payload }) {
         state.books = payload[0];
      },
      setTotalBooks(state, { payload }) {
         state.totalBooks = payload[1];
      },
   },
   extraReducers: {
      [fetchBooks.pending]: (state) => {
         state.loading = true
         state.error = null
      },
      [fetchBooks.fulfilled]: (state) => {
         state.loading = false
         state.success = true
      },
      [fetchBooks.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
      [deleteBook.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
      [fetchBooksByUserId.pending]: (state) => {
         state.loading = true
         state.error = null
      },
      [fetchBooksByUserId.fulfilled]: (state) => {
         state.loading = false
         state.success = true
      },
      [fetchBooksByUserId.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
      [postBook.pending]: (state) => {
         state.loading = true
         state.error = null
      },
      [postBook.fulfilled]: (state) => {
         state.loading = false
         state.success = true
      },
      [postBook.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
      [putBook.pending]: (state) => {
         state.loading = true
         state.error = null
      },
      [putBook.fulfilled]: (state) => {
         state.loading = false
         state.success = true
      },
      [putBook.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
   }
});


export const { setBooks, setTotalBooks } = bookSlice.actions;

export default bookSlice.reducer;
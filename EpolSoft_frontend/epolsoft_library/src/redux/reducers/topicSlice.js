import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { createRequest } from '../../common/requestGenerator';
import * as axios from "../../lib/actionAxiosTypes";
import * as entities from "../entitiesConst"
import { topicsFetchAllDto } from '../../services/topicDto'

const initialState = {
   loading: false,
   [entities.TOPICS]: [],
   error: null,
   success: false,
   totalTopics: null,
   deleteLoading: false,
}


export const fetchTopics = createAsyncThunk(
   `${entities.TOPICS}/fetchTopics`,
   async function (_, { rejectWithValue, dispatch }) {
      try {

         await createRequest({
            method: axios.GET, url: axios.PATH_GET_AVAILABLE_TOPICS,
            postCallback: (response) => {
               console.log(axios.GET);
               const { data } = response
               return data;
            },
            redux_cfg: {
               dispatch,
               actions: [setTopics]
            }
         })
      } catch (error) {
         return rejectWithValue(error.message)
      }
   }
);

export const fetchAllTopics = createAsyncThunk(
   `${entities.TOPICS}/fetchAllTopics`,
   async function (pageParams, { rejectWithValue, dispatch }) {
      try {
         console.log('query address: ' + axios.PATH_GET_ALL_TOPICS(pageParams));
         await createRequest({
            method: axios.GET, url: axios.PATH_GET_ALL_TOPICS(pageParams),
            postCallback: (response) => {
               console.log(axios.GET);
               const { data } = response
               return data;
            },
            redux_cfg: {
               dispatch,
               actions: [setAllTopics, setTotalTopics]
            }
         })
      } catch (error) {
         return rejectWithValue(error.message)
      }
   }
);

export const deleteTopic = createAsyncThunk(
   `${entities.TOPICS}/deleteTopic`,
   async ({ id }, { rejectWithValue }) => {
      try {
         await createRequest({
            method: axios.DELETE, url: axios.PATH_DELETE_TOPIC({ id }),
            postCallback: (response) => {
               console.log(axios.DELETE);
               console.log(response);
            }
         })

      } catch (error) {
         if (error.response && error.response.data.message) {
            return rejectWithValue(error.response.data.message)
         } else {
            return rejectWithValue(error.message)
         }
      }
   }
)

export const postTopic = createAsyncThunk(
   `${entities.TOPICS}/postTopic`,
   async (data, { rejectWithValue }) => {
      try {
         await createRequest({
            method: axios.POST, url: axios.PATH_POST_TOPIC,
            body: {
               name: data.name,
            },
            postCallback: (response) => {
               console.log(axios.POST);
               console.log(response);
            }
         })

      } catch (error) {
         if (error.response && error.response.data.message) {
            return rejectWithValue(error.response.data.message)
         } else {
            return rejectWithValue(error.message)
         }
      }
   }
)

export const putTopic = createAsyncThunk(
   `${entities.TOPICS}/putTopic`,
   async (data, { rejectWithValue }) => {
      try {
         await createRequest({
            method: axios.PUT, url: axios.PATH_PUT_TOPIC,
            body: {
               id: data.id,
               name: data.name,
            },
            postCallback: (response) => {
               console.log(axios.POST);
               console.log(response);
            }
         })

      } catch (error) {
         if (error.response && error.response.data.message) {
            return rejectWithValue(error.response.data.message)
         } else {
            return rejectWithValue(error.message)
         }
      }
   }
)

export const enableTopic = createAsyncThunk(
   `${entities.TOPICS}/enableTopic`,
   async ({ id }, { rejectWithValue }) => {
      try {
         await createRequest({
            method: axios.POST, url: axios.PATH_ENABLE_TOPIC({ id }),
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

export const disableTopic = createAsyncThunk(
   `${entities.TOPICS}/disableTopic`,
   async ({ id }, { rejectWithValue }) => {
      try {
         await createRequest({
            method: axios.POST, url: axios.PATH_DISABLE_TOPIC({ id }),
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

const topicSlice = createSlice({
   name: entities.TOPICS,
   initialState,
   reducers: {
      setTopics(state, { payload }) {
         state.topics = payload;
      },
      setAllTopics(state, { payload }) {
         state.topics = topicsFetchAllDto(payload[0]);
      },
      setTotalTopics(state, { payload }) {
         state.totalTopics = payload[1];
      },
   },
   extraReducers: {
      [fetchTopics.pending]: (state) => {
         state.status = true;
         state.error = null;
      },
      [fetchTopics.fulfilled]: (state, { payload }) => {
         state.status = false;
         state.success = true;
      },
      [fetchTopics.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
      [fetchAllTopics.pending]: (state) => {
         state.status = true;
         state.error = null;
      },
      [fetchAllTopics.fulfilled]: (state, { payload }) => {
         state.status = false;
         state.success = true;
      },
      [fetchAllTopics.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
      [deleteTopic.pending]: (state) => {
         state.status = true;
         state.error = null;
         state.deleteLoading = true;
      },
      [deleteTopic.fulfilled]: (state, { payload }) => {
         state.status = false;
         state.success = true;
         state.deleteLoading = false;
      },
      [deleteTopic.rejected]: (state, { payload }) => {
         state.loading = false;
         state.error = payload;
         state.deleteLoading = false;
      },
      [postTopic.pending]: (state) => {
         state.status = true;
         state.error = null;
      },
      [postTopic.fulfilled]: (state, { payload }) => {
         state.status = false;
         state.success = true;
      },
      [postTopic.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
   }
});


export const { setTopics, setAllTopics, setTotalTopics } = topicSlice.actions;

export default topicSlice.reducer;
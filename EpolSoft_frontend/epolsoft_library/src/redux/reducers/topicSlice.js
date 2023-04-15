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

const postTopic = createAsyncThunk(
   `${entities.TOPICS}/postTopic`,
   async (data, { rejectWithValue, dispatch }) => {
      try {
         await createRequest({
            method: axios.POST, url: axios.PATH_DELETE_TOPIC(),
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
      [fetchTopics.fulfilled]: (state, action) => {
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
      [fetchAllTopics.fulfilled]: (state, action) => {
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
      },
      [deleteTopic.fulfilled]: (state, action) => {
         state.status = false;
         state.success = true;
      },
      [deleteTopic.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
      [postTopic.pending]: (state) => {
         state.status = true;
         state.error = null;
      },
      [postTopic.fulfilled]: (state, action) => {
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
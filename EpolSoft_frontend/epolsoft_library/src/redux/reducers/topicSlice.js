import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { createRequest } from '../../common/requestGenerator';
import * as axios from "../../lib/actionAxiosTypes";
import * as entities from "../entitiesConst"


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
   async function (_, { rejectWithValue, dispatch }) {
      try {

         await createRequest({
            method: axios.GET, url: axios.PATH_GET_ALL_TOPICS,
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
   async ({ id }, { rejectWithValue, dispatch }) => {
      try {
         await createRequest({
            method: axios.DELETE, url: axios.PATH_DELETE_TOPIC({ id: id }),
            postCallback: (response) => {
               console.log(axios.DELETE);
               console.log(response);
               return { id };
            },
            redux_cfg: {
               dispatch,
               actions: [removeTopic]
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
         state.topics = payload[0];
      },
      setTotalTopics(state, { payload }) {
         state.totalTopics = payload[1];
      },
      removeTopic(state, { payload }) {
         state.topics = state.topics.filter(topic => topic.id !== payload.id);
      }
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
   }
});


export const { removeTopic, setTopics, setAllTopics, setTotalTopics } = topicSlice.actions;

export default topicSlice.reducer;
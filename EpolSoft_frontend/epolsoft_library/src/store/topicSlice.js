import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { axios } from "../axios"
import { topics } from "../API/serverData";

/// DON'T USE IT !!!
export const fetchTopics = createAsyncThunk(
   'topics/fetchTopics',
   async function (_, { rejectWithValue }) {
      try {

         const response = await axios.get('?_limit=10'); //Replace address

         if (!response.status === 200) {
            throw new Error('Can\'t extract any topics');
         }

         const data = await response.data;
         return data;

      } catch (error) {
         return rejectWithValue(error.message)
      }
   }
);

const setError = (state, action) => {
   state.status = 'rejected';
   state.error = action.payload;
}

const topicSlice = createSlice({
   name: 'topics',
   initialState: {
      topics: [],
      status: null,
      error: null,
   },
   reducers: {
      fetchLocalTopics(state, action) {
         state.topics = topics;
      },
      addTopic(state, action) {
         console.log(action);

         state.topics.push({
            id: new Date().toISOString(),
            title: action.payload.title
         });
      },
      removeTopic(state, action) {
         state.topics = state.topics.filter(topic => topic.id !== action.payload.id);
      }
   },
   extraReducers: {
      [fetchTopics.pending]: (state) => {
         state.status = 'loading';
         state.error = null;
      },
      [fetchTopics.fulfilled]: (state, action) => {
         state.status = 'resolve';
         state.topics = action.payload;
      },
      [fetchTopics.rejected]: setError,
   }
});


export const { addTopic, removeTopic } = topicSlice.actions;

export default topicSlice.reducer;
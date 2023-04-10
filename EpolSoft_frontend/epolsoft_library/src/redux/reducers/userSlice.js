import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { createRequest } from '../../common/requestGenerator';
import * as axios from "../../lib/actionAxiosTypes";
import * as entities from "../entitiesConst"


const initialState = {
   loading: false,
   [entities.USERS]: [],
   error: null,
   success: false,
   totalUsers: null,
}



export const fetchUsers = createAsyncThunk(
   `${[entities.USERS]}/fetchUsers`,
   async (pageParams, { rejectWithValue, dispatch }) => {
      try {
         await createRequest({
            method: axios.GET, url: axios.PATH_GET_USERS(pageParams),
            postCallback: (dataAfter) => {
               const { data } = dataAfter;
               return data;
            },
            redux_cfg: {
               dispatch,
               actions: [setUsers, setTotalUsers]
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

export const blockUser = createAsyncThunk(
   `${[entities.USERS]}/blockUser`,
   async ({ id }, { rejectWithValue, dispatch }) => {
      try {

         await createRequest({
            method: axios.PUT, url: axios.PATH_BLOCK_USERS({ id }),
            body: {

            },
            postCallback: () => {
               return { id };
            },
            redux_cfg: {
               dispatch,
               actions: [updateBlockUser]
            }
         })

      } catch (error) {
         // return custom error message from backend if present
         if (error.response && error.response.data.message) {
            return rejectWithValue(error.response.data.message)
         } else {
            return rejectWithValue(error.message)
         }
      }
   }
)

const userSlice = createSlice({
   name: entities.USERS,
   initialState,
   reducers: {
      setUsers(state, { payload }) {
         state[entities.USERS] = payload[0];
      },
      setTotalUsers(state, { payload }) {
         state.totalUsers = payload[1];
      },
      updateBlockUser(state, { payload }) {
         state[entities.USERS] = state[entities.USERS].map(user => user.id !== payload.id ? user : { ...user, isBlocked: !user.isBlocked });
      }
   },
   extraReducers: {
      // login user
      [fetchUsers.pending]: (state) => {
         state.loading = true
         state.error = null
      },
      [fetchUsers.fulfilled]: (state, { payload }) => {
         state.loading = false
         state.success = true
      },
      [fetchUsers.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
   },
})

export const { setUsers, setTotalUsers, updateBlockUser } = userSlice.actions;

export default userSlice.reducer
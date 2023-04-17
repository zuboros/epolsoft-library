import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { createRequest } from '../../common/requestGenerator';
import * as axios from "../../lib/actionAxiosTypes";
import * as entities from "../entitiesConst"
import { usersFetchAllDto } from '../../services/userDto'

const initialState = {
   loading: false,
   [entities.USERS]: [],
   error: null,
   success: false,
   totalUsers: null,
   blockLoading: false,
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
   async ({ id }, { rejectWithValue }) => {
      try {

         await createRequest({
            method: axios.PUT, url: axios.PATH_BLOCK_USERS({ id }),
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

export const unblockUser = createAsyncThunk(
   `${[entities.USERS]}/unblockUser`,
   async ({ id }, { rejectWithValue }) => {
      try {

         await createRequest({
            method: axios.PUT, url: axios.PATH_UNBLOCK_USERS({ id }),
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
         state[entities.USERS] = usersFetchAllDto(payload[0]);
      },
      setTotalUsers(state, { payload }) {
         state.totalUsers = payload[1];
      }
   },
   extraReducers: {
      // login user
      [fetchUsers.pending]: (state) => {
         state.loading = true
         state.error = null
      },
      [fetchUsers.fulfilled]: (state) => {
         state.loading = false
         state.success = true
      },
      [fetchUsers.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
      [blockUser.pending]: (state) => {
         state.blockLoading = true
         state.error = null
      },
      [blockUser.fulfilled]: (state) => {
         state.blockLoading = false
         state.success = true
      },
      [blockUser.rejected]: (state, { payload }) => {
         state.blockLoading = false
         state.error = payload
      },
      [unblockUser.pending]: (state) => {
         state.blockLoading = true
         state.error = null
      },
      [unblockUser.fulfilled]: (state) => {
         state.blockLoading = false
         state.success = true
      },
      [unblockUser.rejected]: (state, { payload }) => {
         state.blockLoading = false
         state.error = payload
      },
   },
})

export const { setUsers, setTotalUsers } = userSlice.actions;

export default userSlice.reducer
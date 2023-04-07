import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { createRequest } from '../../common/requestGenerator';
import * as axios from "../../lib/actionAxiosTypes";
import * as entities from "../entitiesConst"


const userToken = localStorage.getItem('userToken')
   ? localStorage.getItem('userToken')
   : null

const userInfo = localStorage.getItem('userInfo')
   ? JSON.parse(localStorage.getItem('userInfo'))
   : null

const setLocalStoreToken = ({ userInfo, userToken }) => {
   console.log({ userInfo, userToken });
   if (!userInfo && !userToken)
      return;
   localStorage.setItem(`userInfo`, JSON.stringify(userInfo));
   localStorage.setItem(`userToken`, userToken);
}

const removeLocalStoreToken = () => {
   localStorage.removeItem(`userInfo`);
   localStorage.removeItem(`userToken`);
}

const initialState = {
   loading: false,
   userInfo,
   userToken,
   error: null,
   success: false,
}



export const registerUser = createAsyncThunk(
   `${entities.AUTH}/registerUser`,
   async ({ userName, email, password }, { rejectWithValue, dispatch }) => {
      try {
         console.log('data: ' + userName, email, password);

         await createRequest({
            method: axios.POST, url: axios.PATH_USER_REGISTER,
            body: {
               userName,
               email,
               password,
            },
            postCallback: (dataAfter) => {
               console.log(axios.POST);
               console.log(dataAfter);
               return dataAfter;
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

export const userLogin = createAsyncThunk(
   `${entities.AUTH}/userLogin`,
   async ({ email, password }, { rejectWithValue, dispatch }) => {
      try {
         console.log('data: ' + email, password);

         await createRequest({
            method: axios.POST, url: axios.PATH_USER_LOGIN,
            body: {
               email,
               password,
            },
            postCallback: (dataAfter) => {
               const { data } = dataAfter;
               return data;
            },
            redux_cfg: {
               dispatch,
               actions: [setUserInfo, setUserToken]
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

export const userLogout = createAsyncThunk(
   `${entities.AUTH}/userLogout`,
   async (_, { rejectWithValue, dispatch }) => {
      try {

         dispatch(removeUserInfo());
         dispatch(removeUserToken());

      } catch (error) {
         if (error.response && error.response.data.message) {
            return rejectWithValue(error.response.data.message)
         } else {
            return rejectWithValue(error.message)
         }
      }
   }
)

const authSlice = createSlice({
   name: entities.AUTH,
   initialState,
   reducers: {
      setUserInfo(state, { payload }) {
         state.userInfo = payload;
      },
      setUserToken(state, { payload }) {
         state.userToken = payload.userToken;
         setLocalStoreToken({ userInfo: payload, userToken: payload.userToken });
      },
      removeUserInfo(state, { payload }) {
         state.userInfo = payload;
      },
      removeUserToken(state, { payload }) {
         state.userToken = payload;
         removeLocalStoreToken();
      }
   },
   extraReducers: {
      // login user
      [userLogin.pending]: (state) => {
         state.loading = true
         state.error = null
      },
      [userLogin.fulfilled]: (state, { payload }) => {
         state.loading = false
         //state.userInfo = payload
         //state.userToken = payload.userToken
      },
      [userLogin.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
      // register user
      [registerUser.pending]: (state) => {
         state.loading = true
         state.error = null
      },
      [registerUser.fulfilled]: (state, { payload }) => {
         state.loading = false
         state.success = true
      },
      [registerUser.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
   },
})

export const { setUserInfo, setUserToken, removeUserInfo, removeUserToken } = authSlice.actions;

export default authSlice.reducer
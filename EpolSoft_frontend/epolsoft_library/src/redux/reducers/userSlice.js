import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { createRequest } from '../../common/requestGenerator';
import * as axios from "../../lib/actionAxiosTypes";
import * as entities from "../entitiesConst"


const userToken = localStorage.getItem('userToken')
   ? localStorage.getItem('userToken')
   : null

const userInfo = localStorage.getItem('userInfo')
   ? localStorage.getItem('userInfo')
   : null

const setLocalStoreToken = ({ userInfo, userToken }) => {
   console.log({ userInfo, userToken });
   if (!userInfo && !userToken)
      return;



   localStorage.setItem(`userInfo`, JSON.stringify(userInfo));
   localStorage.setItem(`userToken`, userToken);
}

const removeLocalStoreToken = ({ userInfo, userToken }) => {
   if (userInfo && userToken)
      return;

   localStorage.removeItem(`userInfo`);
   localStorage.removeItem(`userToken`);
}

const initialState = {
   loading: false,
   userInfo, // for user object
   userToken, // for storing the JWT
   error: null,
   success: false, // for monitoring the registration process.
}



export const registerUser = createAsyncThunk(
   'auth/register',
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
               console.log(dataAfter);                    ///
               return dataAfter;
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

export const userLogin = createAsyncThunk(
   'auth/login',
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
               console.log(axios.POST);
               console.log(data);
               return data;
            },
            redux_cfg: {
               dispatch,
               actions: [setUserInfo, setUserToken]
            }
         })

      } catch (error) {
         // return custom error message from API if any
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
         console.log('sdac');
         console.log(state.userInfo);
         console.log(payload);
         state.userInfo = payload;
      },
      setUserToken(state, { payload }) {
         state.userToken = payload.userToken;
         setLocalStoreToken({ userInfo: payload, userToken: payload.userToken });
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
         state.success = true // registration successful
      },
      [registerUser.rejected]: (state, { payload }) => {
         state.loading = false
         state.error = payload
      },
   },
})

export const { setUserInfo, setUserToken } = authSlice.actions;

export default authSlice.reducer
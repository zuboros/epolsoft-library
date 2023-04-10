import Axios from "axios";

export const serverURL = process.env.REACT_APP_API_ADDRESS;

export const axios = Axios.create({
   baseURL: serverURL,
   timeout: 3000,
});
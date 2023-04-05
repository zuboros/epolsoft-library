import Axios from "axios";


export const axios = Axios.create({
   baseURL: process.env.REACT_APP_API_ADDRESS,
   timeout: 3000,
});
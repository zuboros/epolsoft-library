import { axios } from "../lib/axios";
import { GET, DELETE } from '../lib/actionAxiosTypes'

const userToken = localStorage.getItem('userToken')
   ? localStorage.getItem('userToken')
   : null

const addToken = (method, body, axios_cfg) => {
   const headers = {
      'Authorization': userToken
   }

   let exp_body;
   let exp_axios_cfg;
   if (method === GET || method === DELETE) {
      exp_body = {
         headers, ...body
      }
      exp_axios_cfg = axios_cfg;
      return [exp_body, exp_axios_cfg]
   } else {
      exp_axios_cfg = {
         headers, ...axios_cfg
      }
      exp_body = body;
      return [exp_body, exp_axios_cfg]
   }
}

export async function createRequest({ preCallback, pre_redux_cfg, url, method, body, axios_cfg, extensionHandler, postCallback, redux_cfg }) {

   try {
      const [exp_body, exp_axios_cfg] = addToken(method, body, axios_cfg);

      /* console.log('my: ' + method);
      console.log(url);
      console.log(body);
      console.log(exp_body);
      console.log(axios_cfg);
      console.log(exp_axios_cfg); */

      let dataBefore = !!preCallback && preCallback();

      !!dataBefore && pre_redux_cfg?.actions.forEach((action) => redux_cfg.dispatch(action(dataBefore)));


      let data = await axios[method](url, exp_body, exp_axios_cfg);


      if (data.status > 300 && data.status < 199) {
         !!extensionHandler && extensionHandler(data);
         throw new Error("server status response: " + data.status);
      }

      let dataAfter = !!postCallback ? postCallback(data) : data;

      redux_cfg?.actions.forEach((action) => redux_cfg.dispatch(action(dataAfter)));

      return dataAfter;

   } catch (error) {
      throw new Error("requestGenerator: " + error.message)
   }
}


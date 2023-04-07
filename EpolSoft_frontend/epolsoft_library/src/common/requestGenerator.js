import { axios } from "../lib/axios";

export async function createRequest({ preCallback, pre_redux_cfg, url, method, body, axios_cfg, extensionHandler, postCallback, redux_cfg }) {

   try {

      let dataBefore = !!preCallback && preCallback();

      pre_redux_cfg?.actions.forEach((action) => redux_cfg.dispatch(action(dataBefore)));

      let data = await axios[method](url, body, axios_cfg);

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
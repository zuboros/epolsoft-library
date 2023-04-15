export const authRegistDto = ({ userName, email, password }) => ({
   name: userName,
   mail: email,
   password
})

export const authLoginDto = ({ email, password }) => ({
   mail: email,
   password
})

export const authDto = (serverParams) => {

   const authInfo = {
      ...serverParams,
      userName: serverParams.name,
      userToken: serverParams.token,
      isBlocked: serverParams.blocked,
   }

   delete authInfo["name"]
   delete authInfo["token"]
   delete authInfo["blocked"]

   return authInfo;
}
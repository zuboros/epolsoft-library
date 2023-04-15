export const usersFetchAllDto = (users) => users.map(user => {

   const userInfo = {
      ...user,
      isBlocked: user.blocked,
   }

   delete userInfo["blocked"]

   return userInfo
})
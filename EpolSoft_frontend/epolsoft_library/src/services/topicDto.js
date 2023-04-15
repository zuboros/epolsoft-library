export const topicsFetchAllDto = (topics) => topics.map(topic => {

   const topicInfo = {
      ...topic,
      isActive: topic.active,
   }

   delete topicInfo["active"]

   return topicInfo
})
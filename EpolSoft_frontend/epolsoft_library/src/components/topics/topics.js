import { Space } from 'antd';
import { useDispatch, useSelector } from 'react-redux';
import { fetchAllTopics, deleteTopic } from '../../redux/reducers/topicSlice';
import { TOPICS, AUTH } from '../../redux/entitiesConst'
import { useEffect } from 'react';
import TopicTable from './table/topicTable'

const Topics = () => {
   const { error, loading, [TOPICS]: topics, success, totalTopics } = useSelector(state => state[TOPICS]);
   const { userToken } = useSelector(state => state[AUTH])
   const dispatch = useDispatch();

   useEffect(() => {
      dispatch(fetchAllTopics(userToken));
   }, [dispatch])

   const hiddenColumns = [
      "isActive",
      "id"
   ]

   const deleteHandler = (record) => {
      dispatch(deleteTopic({ auth: userToken, id: record.id }));
   }

   return (
      <>
         <div>
            <h2>Topics:</h2>
            {error && <h3>{error}</h3>}
         </div>
         {success && <TopicTable entities={topics} totalEntities={totalTopics} hiddenColumns={hiddenColumns} loading={loading} deleteBtnHandler={deleteHandler} />}
      </>
   )
}

export default Topics;
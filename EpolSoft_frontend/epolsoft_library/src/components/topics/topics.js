import { Space, Button } from 'antd';
import { useDispatch, useSelector } from 'react-redux';
import { fetchAllTopics, deleteTopic } from '../../redux/reducers/topicSlice';
import { TOPICS } from '../../redux/entitiesConst'
import { useEffect } from 'react';
import TopicTable from '../common/table/table'
/* import CreateTopic from './actions/createTopic' */
import { DeleteOutlined } from '@ant-design/icons'
import * as table from '../common/table/tableConsts'

const Topics = () => {
   const { error, loading, [TOPICS]: topics, success, totalTopics } = useSelector(state => state[TOPICS]);

   const dispatch = useDispatch();

   const getAllTopics = (pageParams) => {
      dispatch(fetchAllTopics(pageParams));
   }

   useEffect(() => {
      getAllTopics(table.pageParams);
   }, [dispatch])

   const hiddenColumns = [
      "isActive",
      "id"
   ]

   const deleteHandler = (record) => {
      dispatch(deleteTopic({ id: record.id }));
   }

   const actionRender = (_, record) =>
      <Space>
         {!record?.isActive &&
            <Button danger onClick={() => deleteHandler(record)} ><DeleteOutlined /></Button>
         }
      </Space>

   return (
      <>
         <div>
            <h2>Topics:</h2>
            {error && <h3>{error}</h3>}
         </div>
         {/* <div>
            Create a new one: <CreateTopic />
         </div> */}
         {success &&
            <TopicTable
               entities={topics}
               totalEntities={totalTopics}
               hiddenColumns={hiddenColumns}
               loading={loading}
               actionColumn={true}
               actionRender={actionRender}
               extractEntities={getAllTopics}
            />}
      </>
   )
}

export default Topics;
import { Space, Button, Popconfirm } from 'antd';
import { useDispatch, useSelector } from 'react-redux';
import { fetchAllTopics, deleteTopic } from '../../redux/reducers/topicSlice';
import { TOPICS } from '../../redux/entitiesConst'
import { useEffect } from 'react';
import TopicTable from '../common/table/table'
import CreateTopic from './actions/createTopic'
import EditTopic from './actions/editTopic'
import { DeleteOutlined, EditOutlined } from '@ant-design/icons'
import * as table from '../common/table/tableConsts'

const Topics = () => {
   const { error, loading, [TOPICS]: topics, success, totalTopics, deleteLoading } = useSelector(state => state[TOPICS]);

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

   const deleteHandler = async (record) => {
      await dispatch(deleteTopic({ id: record.id }));
      getAllTopics(table.pageParams);
   }

   const actionRender = (_, record) =>
      <Space size={0}>
         <div style={{ width: "30px" }}>
            <EditTopic record={record} getTopics={getAllTopics} />
         </div>
         <div style={{ width: "30px" }}>
            {!record?.isActive &&
               <Popconfirm
                  title="Are you sure?"
                  onConfirm={() => deleteHandler(record)}
               >
                  <Button danger type='link' loading={deleteLoading}><DeleteOutlined /></Button>
               </Popconfirm>}
         </div>

      </Space>

   return (
      <>
         <div>
            <h2>Topics:</h2>
            {error && <h3>{error}</h3>}
         </div>
         <div style={{ marginBottom: 10 }}>
            <CreateTopic />
         </div>
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
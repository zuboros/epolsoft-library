import { useDispatch, useSelector } from 'react-redux';
import { Space, Button, Popconfirm } from 'antd'
import { SaveOutlined, StopOutlined, DeleteOutlined, EditOutlined, DownloadOutlined } from '@ant-design/icons'
import { extractDataByUserId, deleteData } from '../../../redux/reducers/bookSlice';
import { BOOKS, AUTH } from '../../../redux/entitiesConst'
import { useEffect } from 'react';
import UserBookTable from '../../common/table/table'
import EditBook from '../../books/actionComponents/editBook'
import CreateBook from '../../books/actionComponents/createBook'
import * as table from '../../common/table/tableConsts'
import { PATH_EXTRACT_FILE } from '../../../lib/actionAxiosTypes'
import { downloadFile } from '../../books/table/features/tableMethods'

const UserBooks = () => {
   const { error, loading, [BOOKS]: books, totalBooks, success, deleteLoading } = useSelector(state => state[BOOKS]);
   const { userToken, userInfo } = useSelector(state => state[AUTH])
   const dispatch = useDispatch();

   useEffect(() => {
      getBooksByUserId(table.pageParams);
   }, [dispatch])

   const getBooksByUserId = (pageParams) => {
      extractDataByUserId(dispatch, userInfo.id, pageParams);
   }

   const hiddenColumns = [
      "file",
      "id"
   ]


   const deleteHandler = (record) => {
      console.log('DELETE');
      deleteData(dispatch, record.id)
      //dispatch(blockUser({ auth: userToken, id: record.id }));
   }

   const downloadHandler = (path) => {
      console.log('DOWNLOAD');
      console.log(path);

      downloadFile(path);
      //dispatch(blockUser({ auth: userToken, id: record.id }));
   }

   const actionRender = (_, record) =>
      <Space>
         <Button onClick={() => { downloadHandler(PATH_EXTRACT_FILE({ id: record.id })) }}><DownloadOutlined /></Button>
         <EditBook record={record} />
         <Popconfirm
            title="Are you sure?"
            onConfirm={() => deleteHandler(record)}
         >
            <Button danger loading={deleteLoading}><DeleteOutlined /></Button>
         </Popconfirm>
      </Space>

   const addingExpandable = (record) => <p><b>Description: </b>{record.description}</p>

   return (
      <>
         <div>
            <h2>My books:</h2>
            {error && <h3>{error}</h3>}
         </div>
         <div>
            Create a new one: <CreateBook />
         </div>
         {success &&
            <UserBookTable
               entities={books}
               totalEntities={totalBooks}
               hiddenColumns={hiddenColumns}
               loading={loading}
               actionRender={actionRender}
               extractEntities={getBooksByUserId}
               addingExpandable={addingExpandable}
            />}
      </>
   )
}

export default UserBooks;
import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Space, Button, Popconfirm } from 'antd';
import { DeleteOutlined, DownloadOutlined } from '@ant-design/icons'
import CreateBook from "./actionComponents/createBook";
import EditBook from './actionComponents/editBook'
import BookTable from "../common/table/table";
import SearchBook from "./actionComponents/searchBook";
import { USER, BOOKS, AUTH } from '../../redux/entitiesConst'
import { extractData, deleteData } from '../../redux/reducers/bookSlice';
import { PATH_EXTRACT_FILE } from '../../lib/actionAxiosTypes'
import * as table from '../common/table/tableConsts'
/* import { downloadFile } from './table/features/tableMethods' */

const downloadFile = (url) => {
   const fileName = url.split("/").pop();
   const aTag = document.createElement("a");
   aTag.href = url;
   aTag.setAttribute("download", fileName);
   document.body.appendChild(aTag);
   aTag.click();
   aTag.remove();
}
function Books() {
   const dispatch = useDispatch();
   const { error, loading, [BOOKS]: books, totalBooks, success, deleteLoading, status } = useSelector(state => state.books);
   const { userInfo } = useSelector(state => state[AUTH])
   const privateItem = userInfo?.roles?.find(column => column === USER)

   const getBooks = (pageParams) => {
      extractData(dispatch, pageParams);
   }

   useEffect(() => {
      getBooks(table.pageParams);
   }, [dispatch])

   const hiddenColumns = [
      "file",
      "id"
   ]

   const deleteHandler = (record) => {
      console.log('DELETE');
      deleteData(dispatch, record.id)
   }

   const downloadHandler = (path) => {
      console.log('DOWNLOAD');
      console.log(path);

      downloadFile(path);
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
      <div className='Books'>
         <Space style={{ display: "flex", justifyContent: 'center', alignItems: "center" }}>
            <SearchBook />
            {privateItem && <CreateBook />}
         </Space>
         {status === 'loading' && <h3>Loading...</h3>}
         {error && <h3>Server error: {error}</h3>}
         {success &&
            <BookTable
               entities={books}
               totalEntities={totalBooks}
               hiddenColumns={hiddenColumns}
               loading={loading}
               actionRender={actionRender}
               extractEntities={getBooks}
               addingExpandable={addingExpandable}
            />}
      </div>
   );
}

export default Books;
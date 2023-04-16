import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Space, Button, Popconfirm } from 'antd';
import { DeleteOutlined, DownloadOutlined } from '@ant-design/icons'
import CreateBook from "./actionComponents/createBook";
import EditBook from './actionComponents/editBook'
import BookTable from "../common/table/table";
import SearchBook from "./actionComponents/searchBook";
import { USER, BOOKS, AUTH, ADMIN } from '../../redux/entitiesConst'
import { fetchBooks, deleteBook, fileDownload } from '../../redux/reducers/bookSlice';
import { PATH_EXTRACT_FILE } from '../../lib/actionAxiosTypes'
import * as table from '../common/table/tableConsts'
/* import { downloadFile } from './table/features/tableMethods' */
import { NIGHT_COLOR } from '../../common/designConst'

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
   const privateUserItem = userInfo?.roles?.find(column => column === USER || column === ADMIN)

   const getBooks = (pageParams) => {
      dispatch(fetchBooks(pageParams));
   }

   useEffect(() => {
      getBooks(table.pageParams);
   }, [dispatch])

   const hiddenColumns = [
      "file",
      "id",
      "authorId"
   ]

   const deleteHandler = async (record) => {
      await dispatch(deleteBook({ id: record.id }));
      getBooks(table.pageParams);
   }

   const downloadHandler = async (id) => {
      await dispatch(fileDownload(id));
   }

   const actionRender = (_, record) =>
      <Space size={0}>
         <div style={{ width: "50px" }}>
            <Button style={{ marginRight: "10px", color: NIGHT_COLOR }} type='link' onClick={() => { downloadHandler({ id: record.id }) }}><DownloadOutlined /></Button>
         </div>
         <div style={{ width: "100px" }}>
            {record.authorId == userInfo.id &&
               <>
                  <EditBook record={record.name.props.entity} getBooks={getBooks} />
                  <Popconfirm
                     title="Are you sure?"
                     onConfirm={() => deleteHandler(record)}
                  >
                     <Button danger type='link' loading={deleteLoading}><DeleteOutlined /></Button>
                  </Popconfirm>
               </>
            }
         </div>

      </Space>

   const addingExpandable = (record) => <p><b>Description: </b>{record.description}</p>


   return (
      <div className='Books'>
         {/* <Space style={{ display: "flex", justifyContent: 'center', alignItems: "center" }}>
            <SearchBook />
         </Space> */}
         <h1>Library:</h1>
         {status === 'loading' && <h3>Loading...</h3>}
         {error && <h3>Server error: {error}</h3>}
         {success &&
            <BookTable
               entities={books}
               totalEntities={totalBooks}
               hiddenColumns={hiddenColumns}
               nameRefColumn={true}
               loading={loading}
               actionColumn={privateUserItem}
               actionRender={actionRender}
               extractEntities={getBooks}
               addingExpandable={addingExpandable}
            />}
      </div>
   );
}

export default Books;
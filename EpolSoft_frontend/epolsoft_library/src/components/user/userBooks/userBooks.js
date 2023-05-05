import { useDispatch, useSelector } from 'react-redux';
import { Space, Button, Popconfirm } from 'antd'
import { DeleteOutlined, DownloadOutlined, FieldTimeOutlined, SendOutlined } from '@ant-design/icons'
import { fetchBooksByUserId, deleteBook, fileDownload, approveBook } from '../../../redux/reducers/bookSlice';
import { BOOKS, AUTH } from '../../../redux/entitiesConst'
import { useEffect, useState } from 'react';
import UserBookTable from '../../common/table/table'
import EditBook from '../../books/actionComponents/editBook'
import CreateBook from '../../books/actionComponents/createBook'
import * as table from '../../common/table/tableConsts'
import { PATH_EXTRACT_FILE } from '../../../lib/actionAxiosTypes'
/* import { downloadFile } from '../../books/table/features/tableMethods' */
import { NIGHT_COLOR } from '../../../common/designConst'
import { WAIT_APPROVING, ARCHIVED, ACTIVED, BLOCKED, CREATED } from '../../../lib/actionAxiosTypes'

const UserBooks = () => {
   const { error, loading, [BOOKS]: books, totalBooks, success, deleteLoading } = useSelector(state => state[BOOKS]);
   const { userInfo } = useSelector(state => state[AUTH])
   const dispatch = useDispatch();

   useEffect(() => {
      getBooksByUserId(table.pageParams);
   }, [dispatch])

   const getBooksByUserId = (pageParams) => {
      dispatch(fetchBooksByUserId({ userId: userInfo.id, pageParams }));
   }

   const hiddenColumns = [
      "file",
      "id",
      "authorId",
      "description",
   ]


   const deleteHandler = async (record) => {
      await dispatch(deleteBook({ id: record.id }));
      getBooksByUserId(table.pageParams);
   }

   const downloadHandler = async (id) => {
      await dispatch(fileDownload(id));
   }

   const sendToApproveHandler = async ({ id }) => {
      await dispatch(approveBook({ id }));
      getBooksByUserId(table.pageParams);
   }

   const activeButton = (record) => {
      switch (record.status) {
         case CREATED:
            return (
               <Button style={{ color: NIGHT_COLOR }} type='link' onClick={() => { sendToApproveHandler({ id: record.id }) }}><SendOutlined /></Button>
            )
         case WAIT_APPROVING:
            return (
               <Button style={{ color: NIGHT_COLOR }} type='link' ><FieldTimeOutlined /></Button>
            )
         default:
            return (<></>)
      }
   }

   const actionRender = (_, record) =>
      <Space size={0}>
         <div style={{ width: "50px" }}>
            <Button style={{ color: NIGHT_COLOR }} type='link' onClick={() => { downloadHandler({ id: record.id }) }}><DownloadOutlined /></Button>
         </div>
         <div style={{ width: "50px" }}>
            {activeButton(record)}
         </div>
         <div style={{ width: "100px" }}>
            <EditBook record={record.name.props.entity} getBooks={getBooksByUserId} />
            <Popconfirm
               title="Are you sure?"
               onConfirm={() => deleteHandler(record)}
            >
               <Button danger type='link' loading={deleteLoading} style={{ paddingLeft: 0 }}><DeleteOutlined /></Button>
            </Popconfirm>
         </div>
      </Space>

   const addingExpandable = (record) => <p><b>Description: </b>{record.description}</p>

   return (
      <>
         <div>
            <h2>My books:</h2>
            {error && <h3>{error}</h3>}
         </div>
         <div style={{ marginBottom: 10 }}>
            <CreateBook />
         </div>
         {success &&
            <UserBookTable
               entities={books}
               totalEntities={totalBooks}
               hiddenColumns={hiddenColumns}
               loading={loading}
               nameRefColumn={true}
               actionColumn={true}
               actionRender={actionRender}
               extractEntities={getBooksByUserId}
               addingExpandable={addingExpandable}
            />}
      </>
   )
}

export default UserBooks;
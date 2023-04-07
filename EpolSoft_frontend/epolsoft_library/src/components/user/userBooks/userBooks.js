import { useDispatch, useSelector } from 'react-redux';
import { extractDataByUserId } from '../../../redux/reducers/bookSlice';
import { BOOKS, AUTH } from '../../../redux/entitiesConst'
import { useEffect } from 'react';
import UserBookTable from './table/userBookTable'

const UserBooks = () => {
   const { error, loading, [BOOKS]: books, totalBooks, success } = useSelector(state => state[BOOKS]);
   const { userToken, userInfo } = useSelector(state => state[AUTH])
   const dispatch = useDispatch();

   useEffect(() => {
      extractDataByUserId(dispatch, userInfo.id, userToken);
   }, [dispatch])

   const hiddenColumns = [
      "file",
      "id"
   ]

   const editHandler = (record) => {
      console.log('EDIT');

      //dispatch(blockUser({ auth: userToken, id: record.id }));
   }

   const deleteHandler = (record) => {
      console.log('DELETE');

      //dispatch(blockUser({ auth: userToken, id: record.id }));
   }

   const downloadHandler = (record) => {
      console.log('DOWNLOAD');

      //dispatch(blockUser({ auth: userToken, id: record.id }));
   }

   return (
      <>
         <div>
            <h2>My books:</h2>
            {error && <h3>{error}</h3>}
         </div>
         {success && <UserBookTable entities={books} totalEntities={totalBooks} hiddenColumns={hiddenColumns} loading={loading}
            editBtnHandler={editHandler}
            deleteBtnHandler={deleteHandler}
            downloadBtnHandler={downloadHandler}
         />}
      </>
   )
}

export default UserBooks;
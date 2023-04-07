import { useDispatch, useSelector } from 'react-redux';
import CreateBook from "./actionComponents/createBook";
import { BookTable } from "./table/bookTable";
import SearchBook from "./actionComponents/searchBook";
import { Space } from 'antd';
import { USER } from '../../redux/entitiesConst'
import { deleteData } from '../../redux/reducers/bookSlice';

function Books() {
   const dispatch = useDispatch();
   const { status, error, loading, deleteLoading } = useSelector(state => state.books);
   const { userInfo, userToken } = useSelector(state => state.auth);
   const privateItem = userInfo?.roles?.find(column => column === USER)

   const handleDelete = (value) => {
      deleteData(dispatch, value.id, userToken)
   }

   return (
      <div className='Books'>
         <Space style={{ display: "flex", justifyContent: 'center', alignItems: "center" }}>
            <SearchBook />
            {privateItem && <CreateBook auth={userToken} />}
         </Space>
         {status === 'loading' && <h3>Loading...</h3>}
         {error && <h3>Server error: {error}</h3>}
         <BookTable loading={loading} deleteLoading={deleteLoading} privateItem={privateItem} deleteButton={handleDelete} />
      </div>
   );
}

export default Books;
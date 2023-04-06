
import { useSelector } from 'react-redux';
import CreateBook from "./CreateBook";
import BookTable from "./Table/BookTable";
import SearchBook from "./SearchBook";
import { Space } from 'antd';

function Books() {
   const { status, error, loading, deleteLoading } = useSelector(state => state.books);

   return (
      <div className='Books'>
         <Space style={{ display: "flex", justifyContent: 'center', alignItems: "center" }}>
            <SearchBook />
            <CreateBook />
         </Space>
         {status === 'loading' && <h3>Loading...</h3>}
         {error && <h3>Server error: {error}</h3>}
         <BookTable loading={loading} deleteLoading={deleteLoading} />
      </div>
   );
}

export default Books;
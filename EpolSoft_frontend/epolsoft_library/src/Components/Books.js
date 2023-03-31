import { useSelector } from 'react-redux';
import Form from "./Form";
import BookTable from "./BookTable";
import SearchBook from "./SearchBook";
import { Space } from 'antd';

function Books() {
   const { status, error } = useSelector(state => state.books)

   return (
      <div className='Books'>
         <Space style={{ display: "flex", justifyContent: 'center', alignItems: "center" }}>
            <SearchBook />
            <Form />
         </Space>
         {status === 'loading' && <h3>Loading...</h3>}
         {error && <h3>Server error: {error}</h3>}
         <BookTable />
      </div>
   );
}

export default Books;
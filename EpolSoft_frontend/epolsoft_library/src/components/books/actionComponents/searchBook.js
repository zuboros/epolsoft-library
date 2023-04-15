import { Input, Space, Button, Form } from "antd"
import { SearchOutlined } from '@ant-design/icons'
import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { setBooks } from '../../../redux/reducers/bookSlice';

const SearchBook = () => {
   const [bookName, setBookName] = useState("");

   const dispatch = useDispatch();

   const handleSearch = () => {
      if (bookName) {
         dispatch(setBooks({}))
      }
   }

   return (
      <Form component={false} disabled>
         <Form.Item name="name" label="Name" style={{ margin: 0 }}
            rules={[
               {
                  required: true,
                  message: "Enter some book name"
               },
               { whitespace: true },
               { min: 3 }
            ]}
         >
            <Space >
               <Input value={bookName} onChange={(e) => setBookName(e.target.value)} />
               <Button type="primary" onClick={handleSearch} ><SearchOutlined /></Button>
            </Space>
         </Form.Item>
      </Form>
   )
}

export default SearchBook;
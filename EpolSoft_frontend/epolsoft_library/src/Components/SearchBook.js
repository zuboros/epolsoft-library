import { Input, Space, Button, Form } from "antd"
import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { fetchLocalBooks } from '../redux/reducers/bookSlice';

const SearchBook = () => {
   const [bookName, setBookName] = useState("");

   const dispatch = useDispatch();

   const handleSearch = () => {
      if (bookName) {
         dispatch(fetchLocalBooks({}))
      }
   }

   return (
      <Form >
         <Form.Item name="name" label="Name"
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
               <Button type="primary" onClick={handleSearch} >Search</Button>
            </Space>
         </Form.Item>
      </Form>
   )
}

export default SearchBook;
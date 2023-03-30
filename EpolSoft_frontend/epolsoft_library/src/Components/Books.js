import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { addNewBook, removeBook, fetchBooks, fetchLocalBooks } from '../store/bookSlice';
import BookList from './BookList';
import Form from "./Form";
import BookTable from "./BookTable";

function Books() {
   const [title, setTitle] = useState('');
   const dispatch = useDispatch();
   const { status, error } = useSelector(state => state.books)

   const handleAction = () => {
      if (title.trim().length) {
         dispatch(addNewBook({ title, userId: 1, body: "__" }));
         setTitle('');
      }
   }

   /* useEffect(() => {
      /// API function:
      //dispatch(fetchBooks())

      ///Local Storage:
      dispatch(fetchLocalBooks({}))
   }, [dispatch]) */

   return (
      <div className='Books'>
         <Form />
         {status === 'loading' && <h3>Loading...</h3>}
         {error && <h3>Server error: {error}</h3>}
         <BookTable />
         {/* <BookList /> */}
      </div>
   );
}

export default Books;
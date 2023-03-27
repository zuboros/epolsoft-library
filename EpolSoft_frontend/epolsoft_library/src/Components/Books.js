import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { addBook, removeBook, fetchBooks } from '../store/bookSlice';
import BookList from './BookList';
import InputTitle from './InputTitle';

function Books() {
   const [title, setTitle] = useState('');
   const dispatch = useDispatch();

   const handleAction = () => {
      if (title.trim().length) {
         dispatch(addBook({ title }));
         setTitle('');
      }
   }

   useEffect(() => {
      dispatch(fetchBooks())
   }, [dispatch])

   return (
      <div className='Books'>
         <InputTitle
            value={title}
            updateTitle={setTitle}
            handleAction={handleAction}
         />
         <BookList />
      </div>
   );
}

export default Books;
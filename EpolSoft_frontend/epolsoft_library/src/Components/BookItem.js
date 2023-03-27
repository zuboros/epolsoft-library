import { useDispatch } from 'react-redux';
import { removeBook, deleteBook } from '../store/bookSlice';

const BookItem = ({ id, ...fields }) => {
   const dispatch = useDispatch();

   return (
      <li>
         <p><span>userId: {fields.userId}</span></p>
         <p><span>title: {fields.title}</span></p>
         <p><span>body: {fields.body}</span></p>
         <p><span onClick={() => dispatch(deleteBook(id))}>&times;</span></p>
      </li>
   );
};

export default BookItem;
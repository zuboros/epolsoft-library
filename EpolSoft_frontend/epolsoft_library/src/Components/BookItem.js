import { useDispatch } from 'react-redux';
import { removeBook } from '../store/bookSlice';

const BookItem = ({ id, title }) => {
   const dispatch = useDispatch();

   return (
      <li>
         <span>{title}</span>
         <span onClick={() => dispatch(removeBook({ id }))}>&times;</span>
      </li>
   );
};

export default BookItem;
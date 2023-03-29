import { useDispatch } from 'react-redux';
import { removeBook, deleteBook } from '../store/bookSlice';

const BookItem = ({ id, ...fields }) => {
   const dispatch = useDispatch();

   return (
      <li>
         <p><span>id: {id}</span></p>
         <p><span>name: {fields.name}</span></p>
         <p><span>author: {fields.author}</span></p>
         <p><span>topic: {fields.topic}</span></p>
         <p><span>shortDescription: {fields.shortDescription}</span></p>
         <p><span>description: {fields.description}</span></p>
         <p><span>fileName: {fields.fileName}</span></p>
         <p><span onClick={() => dispatch(removeBook({ id }))}>&times;</span></p>
      </li>
   );
};

export default BookItem;
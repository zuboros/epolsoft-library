import { useSelector } from 'react-redux';
import BookItem from './BookItem';

const BookList = () => {
   const books = useSelector(state => state.books.books);

   return (
      <ul>
         {books.map((book) => (
            <BookItem
               key={book.id}
               {...book}
            />
         ))}
      </ul>
   );
};

export default BookList;
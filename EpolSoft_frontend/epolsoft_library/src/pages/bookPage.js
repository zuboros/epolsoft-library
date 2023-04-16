import { useParams } from 'react-router-dom'

const BookPage = () => {
   const { id } = useParams();
   return (
      <>
         {id}
      </>
   )
}

export default BookPage
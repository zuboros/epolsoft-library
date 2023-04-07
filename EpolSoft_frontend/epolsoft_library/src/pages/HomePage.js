import Books from '../components/books/books'
import { useDispatch, useSelector } from 'react-redux'


const HomePage = () => {

   const { loading, userInfo, error } = useSelector((state) => state.auth)

   return (
      <Books />
   )
}

export default HomePage;
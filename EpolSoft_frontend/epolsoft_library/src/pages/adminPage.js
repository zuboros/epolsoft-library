import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'
import Users from '../components/users/users'
import Topics from '../components/topics/topics'
import { userLogout } from '../redux/reducers/authSlice'
import * as entity from '../redux/entitiesConst'

const AdminPage = () => {

   const { loading, userInfo, error } = useSelector((state) => state.auth)
   const navigate = useNavigate()

   useEffect(() => {
      console.log(userInfo);
      if (!userInfo)
         navigate('/login')
   }, [navigate, userInfo])
   return (
      <>
         {userInfo?.roles?.find((role) => role === entity.ADMIN) && <Topics />}
         {userInfo?.roles?.find((role) => role === entity.ADMIN) && <Users />}
      </>
   )
}

export default AdminPage
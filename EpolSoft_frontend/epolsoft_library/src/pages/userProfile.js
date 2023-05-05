import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'
import UserBooks from '../components/user/userBooks/userBooks'
import UserDescription from '../components/user/userDescription'
import * as entity from '../redux/entitiesConst'

const UserProfile = () => {

   const { loading, userInfo, avatar, error } = useSelector((state) => state.auth)

   const navigate = useNavigate()

   useEffect(() => {
      if (!userInfo)
         navigate('/login')
   }, [navigate, userInfo])

   return (
      <>
         <div style={{ minHeight: 250 }}>
            {userInfo && <UserDescription userInfo={userInfo} avatar={avatar} />}
         </div>
         {userInfo?.roles?.find((role) => role === entity.USER) && <UserBooks userInfo={userInfo} />}
      </>
   )
}

export default UserProfile
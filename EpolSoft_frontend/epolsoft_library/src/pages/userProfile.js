import { Avatar } from 'antd'
import { useDispatch, useSelector } from 'react-redux'
import Error from '../components/common/error'
import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'
import Users from '../components/users/users'

const UserProfile = () => {

   const { loading, userInfo, error } = useSelector((state) => state.auth)
   const dispatch = useDispatch()
   const navigate = useNavigate()

   useEffect(() => {
      console.log(userInfo);

      if (!userInfo) {
         navigate('/login')
      }
   }, [navigate, userInfo])

   return (
      <>
         <Avatar src="https://thumbs.dreamstime.com/b/avatar-van-de-geekmens-104871313.jpg" />
         { }
      </>
   )
}

export default UserProfile
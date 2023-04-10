import { Avatar, Button, Popconfirm, Space } from 'antd'
import { useDispatch, useSelector } from 'react-redux'
import { LogoutOutlined, UserOutlined } from '@ant-design/icons'
import Error from '../components/common/error'
import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'
import Users from '../components/users/users'
import Topics from '../components/topics/topics'
import UserBooks from '../components/user/userBooks/userBooks'
import { userLogout } from '../redux/reducers/authSlice'
import UserDescription from '../components/user/userDescription'
import * as entity from '../redux/entitiesConst'
import * as axios from "../lib/actionAxiosTypes";

const UserProfile = () => {

   const { loading, userInfo, error } = useSelector((state) => state.auth)
   const dispatch = useDispatch()
   const navigate = useNavigate()

   useEffect(() => {
      console.log(userInfo);
      if (!userInfo)
         navigate('/login')
   }, [navigate, userInfo])

   const logout = () => {
      dispatch(userLogout());
   }

   return (
      <>
         <Space wrap size={16}>
            <Avatar size={128} icon={<UserOutlined />} src={axios.PATH_EXTRACT_AVATAR({ id: userInfo?.id })} />
            {userInfo && <UserDescription userInfo={userInfo} />}
         </Space>


         <Popconfirm title="Are you sure to cancel?" onConfirm={logout}>
            <Button danger type='primary'><LogoutOutlined /></Button>
         </Popconfirm>

         {userInfo?.roles?.find((role) => role === entity.USER) && <UserBooks userInfo={userInfo} />}
         {userInfo?.roles?.find((role) => role === entity.ADMIN) && <Topics />}
         {userInfo?.roles?.find((role) => role === entity.ADMIN) && <Users />}
      </>
   )
}

export default UserProfile
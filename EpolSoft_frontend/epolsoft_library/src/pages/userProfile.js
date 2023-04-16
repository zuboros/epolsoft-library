import { Avatar, Button, Popconfirm, Space } from 'antd'
import { useDispatch, useSelector } from 'react-redux'
import { UserOutlined } from '@ant-design/icons'
import Error from '../components/common/error'
import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'
import UserBooks from '../components/user/userBooks/userBooks'
import { userLogout } from '../redux/reducers/authSlice'
import UserDescription from '../components/user/userDescription'
import { DARK_COLOR } from '../common/designConst'
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

   return (
      <>
         {userInfo && <Space size={30}>
            <Avatar size={128} icon={<UserOutlined />} src={axios.PATH_EXTRACT_AVATAR({ id: userInfo?.id })} style={{ backgroundColor: DARK_COLOR }} />
            <UserDescription userInfo={userInfo} />
         </Space>}
         {userInfo?.roles?.find((role) => role === entity.USER) && <UserBooks userInfo={userInfo} />}
      </>
   )
}

export default UserProfile
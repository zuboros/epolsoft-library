import { Link } from 'react-router-dom'
import { Button, Popconfirm, Avatar } from 'antd'
import { LogoutOutlined, LoginOutlined, UserOutlined } from '@ant-design/icons'
import { ANONYM, USER, ADMIN } from '../../redux/entitiesConst'
import * as axios from "../../lib/actionAxiosTypes";

const profile = {
   label: <Link to='/profile'>profile</Link>
}
const adminPanel = {
   label: <Link to='/admin'>admin panel</Link>
}
const logout = (logoutHandler) => ({
   label:
      <Popconfirm title="Are you sure to logout?" onClick={logoutHandler}>
         <Button danger size='small'><LogoutOutlined /></Button>
      </Popconfirm>
})
const login = {
   label: <Link to='/login'>Login <LoginOutlined /></Link>
}

const anonym = [
   login
]

const user = [
   profile
]

const admin = [
   adminPanel
]

const rolePanel = {
   [ANONYM]: anonym,
   [USER]: user,
   [ADMIN]: admin,
}


export const menuItem = (userInfo, logoutHandler) => {

   const firstField = 1
   let innerField = 1;

   return (
      [{
         key: firstField,
         icon: !!userInfo ? <Avatar src={axios.PATH_EXTRACT_AVATAR({ id: userInfo?.id })} style={{ backgroundColor: "#ede8e8" }} ><UserOutlined /></Avatar> : <Avatar style={{ backgroundColor: "#ede8e8" }}><UserOutlined /></Avatar>,
         label: userInfo?.userName || login.label,
         children: !!userInfo &&
            userInfo.roles.reduce((arr, role) => {
               rolePanel[role]?.forEach((field, index) => {
                  arr = arr.filter((ar) => ar.label !== field.label);
                  arr.unshift({
                     key: firstField * 100 + innerField * 10 + index,
                     ...field,
                  })
               });
               innerField = innerField + 1;
               return arr;
            }, [
               {
                  key: firstField * 100 + 90,
                  ...logout(logoutHandler)
               }
            ])
      }
      ]
   )
}







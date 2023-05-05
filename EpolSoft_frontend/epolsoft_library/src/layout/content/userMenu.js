import { Link } from 'react-router-dom'
import { Button, Popconfirm, Avatar } from 'antd'
import { LogoutOutlined, LoginOutlined, UserOutlined, UserAddOutlined } from '@ant-design/icons'
import { ANONYM, USER, ADMIN, MODERATOR } from '../../redux/entitiesConst'
import { DARK_COLOR } from '../../common/designConst'
import * as axios from "../../lib/actionAxiosTypes";

const profile = {
   label: <Link to='/profile' ><UserOutlined />  profile</Link>
}
const adminPanel = {
   label: <Link to='/admin' ><UserAddOutlined />  admin panel</Link>
}
const moderatorPanel = {
   label: <Link to='/moderator' ><UserAddOutlined />  moderator panel</Link>
}
const logout = (logoutHandler) => ({
   key: "logout",
   label:
      <Popconfirm title="Are you sure to logout?" onConfirm={logoutHandler}>
         <Button danger type="link" size='small' style={{ height: "100%", width: "100%", textAlign: "left", padding: 0 }}><LogoutOutlined />  logout</Button>
      </Popconfirm>,
   danger: true,
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

const moderator = [
   moderatorPanel
]

const rolePanel = {
   [ANONYM]: anonym,
   [USER]: user,
   [ADMIN]: admin,
   [MODERATOR]: moderator,
}


export const menuItem = (userInfo, logoutHandler, avatar) => {
   const firstField = 1
   let innerField = 1;

   const labelItems = (userName) => userName ?
      <><Avatar src={avatar} style={{ backgroundColor: DARK_COLOR }} ><UserOutlined /></Avatar><span style={{ marginLeft: "10px" }}>{userInfo?.userName}</span></> :
      <><Avatar style={{ backgroundColor: DARK_COLOR }}><UserOutlined /></Avatar><span style={{ marginLeft: "10px" }}>{login.label}</span></>

   return (
      [{
         key: firstField,
         label: labelItems(userInfo?.userName),
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
                  ...logout(logoutHandler)
               }
            ])
      }
      ]
   )
}






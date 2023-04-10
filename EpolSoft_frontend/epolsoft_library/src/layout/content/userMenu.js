import { Link } from 'react-router-dom'
import { Button, Popconfirm, Avatar } from 'antd'
import { LogoutOutlined, UserOutlined } from '@ant-design/icons'
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
   label: <Link to='/login'>admin panel</Link>
}

const anonym = [
   login
]

const user = [
   profile,
   logout
]

const admin = [
   adminPanel,
   logout
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
         icon: <Avatar icon={<UserOutlined />} src={axios.PATH_EXTRACT_AVATAR({ id: userInfo?.id })} />,
         label: userInfo?.userName || "Anonym",
         children: !!userInfo ?


            userInfo.roles.reduce((arr, role) => {
               rolePanel[role].forEach((field, index) => {
                  field = field instanceof Function ? field(logoutHandler) : field;
                  arr = arr.filter((ar) => ar.label !== field.label);
                  arr.push({
                     key: firstField * 100 + innerField * 10 + index,
                     ...field,
                  })
                  /* console.log('=================');
                  console.log(field);
                  console.log('index: ' + index);
                  console.log('array');
                  console.log(arr);
                  console.log('role: ' + role);
                  console.log('innerField: ' + innerField); */
               });
               innerField = innerField + 1;
               return arr;
            }, [])
            :
            rolePanel[ANONYM].map((field, j) => ({
               ...field,
               key: firstField * 100 + 10 + j,
            }))
      }
      ]
   )
}







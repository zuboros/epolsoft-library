import React from 'react';
import { Outlet } from 'react-router-dom'
import { LaptopOutlined, NotificationOutlined, UserOutlined } from '@ant-design/icons';
import { Breadcrumb, Layout, Menu, theme } from 'antd';
import * as axios from "../../lib/actionAxiosTypes";
import { useSelector, useDispatch } from 'react-redux'
import { menuItem } from './userMenu'
import { userLogout } from '../../redux/reducers/authSlice'
/* const items2 = [UserOutlined, LaptopOutlined, NotificationOutlined].map((icon, index) => {
   const key = String(index + 1);
   return {
      key: `sub${key}`,
      icon: <Avatar icon={<UserOutlined />} src={axios.PATH_EXTRACT_AVATAR({ id: userInfo?.id })} />,
      label: userInfo?.userName,
      children: new Array(4).fill(null).map((_, j) => {
         const subKey = index * 4 + j + 1;
         return {
            key: subKey,
            label: `option${subKey}`,
         };
      }),
   };
}); */

const AppContent = () => {
   const dispatch = useDispatch();
   const { userInfo } = useSelector((state) => state.auth)

   console.log(menuItem(userInfo));

   const logoutHandler = () => {
      dispatch(userLogout());
   }

   const {
      token: { colorBgContainer },
   } = theme.useToken();

   const { Content, Sider } = Layout;

   return (
      <Content
         style={{
            padding: '0 20px',
            backgroundColor: '#ede8e8'
         }}
      >
         <Layout
            style={{
               padding: '24px 0',
               background: colorBgContainer,
            }}
         >
            <Sider
               style={{
                  background: colorBgContainer,
               }}
               width={200}
            >
               <Menu
                  mode="inline"
                  defaultSelectedKeys={['1']}
                  defaultOpenKeys={['sub1']}
                  style={{
                     height: '100%',
                  }}
                  items={menuItem(userInfo, logoutHandler)}
               />
            </Sider>
            <Content
               style={{
                  padding: '0 24px',
                  minHeight: "75vh",
               }}
            >
               <Outlet />
            </Content>
         </Layout>
      </Content>
   )
}

export default AppContent;
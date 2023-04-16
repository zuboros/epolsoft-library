import { Breadcrumb, Layout, Menu, theme, Affix } from 'antd';
import { Link } from "react-router-dom";
import React from 'react';
import { useSelector, useDispatch } from 'react-redux'
import './appLayout.css'
import { userLogout } from '../redux/reducers/authSlice'

import AppContent from './content/appContent'
import { menuItem } from './content/userMenu'
import { DARK_COLOR, LIGHT_COLOR, NIGHT_COLOR } from '../common/designConst';

const { Header, Footer } = Layout;

const AppLayout = () => {
   const { userInfo } = useSelector((state) => state.auth)
   const dispatch = useDispatch();

   const logoutHandler = () => {
      dispatch(userLogout());
   }
   const elements = [{
      key: 1,
      label: <Link to="/">
         Home
      </Link>,
   },
   {
      key: 2,
      label: <Menu
         theme="dark"
         selectable={false}
         mode="vertical"
         items={menuItem(userInfo, logoutHandler)}
      />
   }
   ];



   return (
      <Layout style={{ minHeight: "100vh" }}>
         <Affix>
            <Header className="header" style={{ backgroundColor: NIGHT_COLOR, }}>
               <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                  <div>
                     <Link to="/" style={{ color: LIGHT_COLOR, }}>
                        Home
                     </Link>
                  </div>
                  <Menu
                     style={{
                        verticalAlign: 'middle',
                        color: LIGHT_COLOR,
                        backgroundColor: NIGHT_COLOR,
                     }}
                     selectable={false}
                     mode="vertical"
                     items={menuItem(userInfo, logoutHandler)}
                  />
               </div>
            </Header>
         </Affix>

         <AppContent />

         <Footer
            style={{
               textAlign: 'center',
               backgroundColor: LIGHT_COLOR,
               color: NIGHT_COLOR,
            }}
         >
            EpolSoft ©2023 Created by Boy NextDoor, A$$ Fister, Performance Artist and other slaves
         </Footer>
      </Layout>
   );
};
export default AppLayout;

import { Breadcrumb, Layout, Menu, theme, Affix } from 'antd';
import { Link } from "react-router-dom";
import React from 'react';
import { useSelector, useDispatch } from 'react-redux'
import './appLayout.css'
import { userLogout } from '../redux/reducers/authSlice'

import AppContent from './content/appContent'
import { menuItem } from './content/userMenu'
import { DARK_COLOR, LIGHT_COLOR, NIGHT_COLOR } from '../common/designConst';
import logo from '../assets/book.svg'

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
                  <div className="pulsating-circle" >
                     <Link to="/" style={{ padding: 0, margin: 0, height: 0, width: 0 }}>
                        <img src={logo} alt='logo' />
                     </Link>
                  </div>
                  <Menu
                     style={{
                        verticalAlign: 'middle',
                        color: LIGHT_COLOR,
                        backgroundColor: NIGHT_COLOR,
                        marginBottom: 35
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

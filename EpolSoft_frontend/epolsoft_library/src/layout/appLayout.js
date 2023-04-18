import { Breadcrumb, Layout, Menu, theme, Affix } from 'antd';
import { Link } from "react-router-dom";
import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux'
import './appLayout.css'
import { userLogout, avatarDownload } from '../redux/reducers/authSlice'

import AppContent from './content/appContent'
import { menuItem } from './content/userMenu'
import { DARK_COLOR, LIGHT_COLOR, NIGHT_COLOR } from '../common/designConst';
import logo from '../assets/book.svg'

const { Header, Footer } = Layout;

const AppLayout = () => {
   const { userInfo } = useSelector((state) => state.auth)
   const dispatch = useDispatch();
   const [avatar, setAvatar] = useState(null);

   useEffect(() => {
      userInfo && dispatch(avatarDownload({ id: userInfo.id, setAvatar }));
   }, [userInfo]);

   const logoutHandler = () => {
      dispatch(userLogout());
   }
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
                     items={menuItem(userInfo, logoutHandler, avatar)}
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
            <Link to="https://www.epolsoft.com/en/home/">EpolSoft</Link> ©2023 Created by <Link to="https://t.me/boyofthisgym">Boy NextDoor</Link>, <Link to="https://t.me/deitykuybi">Performance Artist</Link>, <Link to="https://t.me/olegzs">Olegsei Darkholme</Link> and other slaves
         </Footer>
      </Layout>
   );
};
export default AppLayout;

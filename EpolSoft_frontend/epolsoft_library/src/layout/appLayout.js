import { LaptopOutlined, NotificationOutlined, UserOutlined } from '@ant-design/icons';
import { Breadcrumb, Layout, Menu, theme, Affix } from 'antd';
import { Link } from "react-router-dom";
import React from 'react';
import './appLayout.css'

import AppContent from './content/appContent'

const { Header, Footer } = Layout;

const elements = ['1'].map((key) => ({
   key,
   label: <Link to="/">
      Home
   </Link>,
}));

const AppLayout = () => {

   return (
      <Layout style={{ minHeight: "100vh" }}>
         <Affix>
            <Header className="header">
               <div className="logo" />      {/* It's here */}
               <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['2']} items={elements} />
            </Header>
         </Affix>

         <AppContent />

         <Footer
            style={{
               textAlign: 'center',
               backgroundColor: '#ede8e8'
            }}
         >
            EpolSoft ©2023 Created by Boy NextDoor, A$$ Fister, Performance Artist and other slaves
         </Footer>
      </Layout>
   );
};
export default AppLayout;

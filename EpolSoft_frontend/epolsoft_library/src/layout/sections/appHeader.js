import React from "react";
import { Link } from "react-router-dom";
import { Layout, Space } from "antd";
import { MenuItems } from './components/menuItems'
import logo from "../../assets/Books-logo.png";
import './appHeader.css'

const { Header } = Layout;

const AppHeader = () => {
   return (
      <Header className="app-header" >
         <Space>
            <div className="app-header__logo-search-section">
               <div className="app-header__logo">
                  <Link to="/" >
                     <img src={logo} alt="App logo" />
                  </Link>
               </div>
            </div>
            <div className="app-header__menu-section">
               <MenuItems />
            </div>
         </Space>
      </Header>
   );
};

export default AppHeader;
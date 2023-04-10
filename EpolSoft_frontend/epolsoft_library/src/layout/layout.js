import { NavLink, Outlet } from 'react-router-dom'
import AppHeader from '../layout/sections/appHeader'
import { Affix } from 'antd'

export const Layout = () => {
   return (
      <>
         <Affix offsetTop={0} className="app__affix-header" style={{ backgroundColor: "white" }}>
            <AppHeader />
         </Affix>
         <Outlet />
      </>
   )
}
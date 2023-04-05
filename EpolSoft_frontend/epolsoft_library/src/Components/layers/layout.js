import { NavLink, Outlet } from 'react-router-dom'

export const Layout = () => {
   return (

      <>
         <header>
            <NavLink to="/">Home</NavLink>
            <NavLink to="/login">Login</NavLink>
            <NavLink to="/register">Register</NavLink>
         </header>
         <Outlet />
      </>
   )
}
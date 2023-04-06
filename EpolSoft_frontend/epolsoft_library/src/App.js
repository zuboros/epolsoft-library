import './App.css';
import { Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom'
import HomePage from './pages/homePage'
import LoginPage from './pages/loginPage'
import RegisterPage from './pages/registerPage'
import NotFoundPage from './pages/notFoundPage'
import { Layout } from './layout/layout'
import UserProfile from './pages/userProfile'

const router = createBrowserRouter(createRoutesFromElements(
  <Route path='/' element={<Layout />}>
    <Route index element={<HomePage />} />
    <Route path='/login' element={<LoginPage />} />
    <Route path='/register' element={<RegisterPage />} />
    <Route path='/profile' element={<UserProfile />} />
    <Route path='*' element={<NotFoundPage />} />
  </Route>
));

function App() {

  return (
    <div className='App'>
      <RouterProvider router={router} />
    </div>
  );
}

export default App;

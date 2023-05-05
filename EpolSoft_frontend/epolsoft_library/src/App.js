import './App.css';
import { Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom'
import HomePage from './pages/homePage'
import LoginPage from './pages/loginPage'
import RegisterPage from './pages/registerPage'
import NotFoundPage from './pages/notFoundPage'
import AppLayout from './layout/appLayout'
import UserProfile from './pages/userProfile'
import AdminPage from './pages/adminPage'
import BookPage from './pages/bookPage'
import ModeratorPage from './pages/moderatorPage'
import NewPasswordPage from './pages/newPasswordPage'
{/* <Route path='/' element={<Layout />}> */ }

const router = createBrowserRouter(createRoutesFromElements(
  <Route path='/' element={<AppLayout />}>
    <Route index element={<HomePage />} />
    <Route path='/login' element={<LoginPage />} />
    <Route path='/register' element={<RegisterPage />} />
    <Route path='/profile' element={<UserProfile />} />
    <Route path='/admin' element={<AdminPage />} />
    <Route path='/moderator' element={<ModeratorPage />} />
    <Route path='/password' element={<NewPasswordPage />} />
    <Route path='/book/:id' element={<BookPage />} />
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

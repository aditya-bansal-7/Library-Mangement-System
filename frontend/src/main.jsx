import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import './index.css'
import SignUp from './component/SignUp.jsx';
import Login from './component/Login.jsx';
import Home from './component/Home.jsx';
import About from './component/About.jsx';
import Categories from './component/Categories.jsx';
import AddBook from './component/AddBook.jsx';
import AdminRoute from './component/AdminRoute.jsx';
import BookDetails from './component/BookDetails.jsx';
import AdminDashboard from './component/AdminDashboard.jsx';
import BookManagement from './component/BookManagement.jsx';
import AdminGrid from './component/AdminGrid.jsx';
// import UserManagement from './component/UserManagement.jsx';
// import Stats from './component/Stats.jsx';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "/",
        element: <Home />,
      },
      {
        path: "about",
        element: <About />,
      },
      {
        path: "categories",
        element: <Categories />,
      },
      {
        path: "admin/addbook",
        element: <AddBook />,
      },
      {
        path: "book/:bookId",
        element: <BookDetails />,
      },
    ],
  },
  {
    path: "/signup",
    element: <SignUp />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/admin",
    element: <AdminRoute />,
    children: [
      {
        path: "",
        element: <AdminDashboard />,
        children: [
          {
            path: "books",
            element: <BookManagement />,
          },
          // {
          //   path: "users",
          //   element: <UserManagement />,
          // },
          {
            path: "",
            element: <AdminGrid />,
          },
          // {
          //   path: "stats",
          //   element: <Stats />,
          // },
        ],
      },
      {
        path: "addbook",
        element: <AddBook />,
      },
      // {
      //   path: "editbook/:bookId",
      //   element: <EditBook />,
      // },
    ],
  },
]);

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)

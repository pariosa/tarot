import * as React from 'react'
import { createBrowserRouter } from 'react-router-dom'
import App from '../App'
import DailyReadingPage from '../pages/DailyReadingPage'
import FullReadingPage from '../pages/FullReadingPage'
import LoginPage from '../pages/Login'

// Remove the default export since we're using named export
export const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      {
        index: true, // This makes it the default route when path is just '/'
        element: <FullReadingPage />,
      },
      {
        path: 'home',
        element: <FullReadingPage />,
      },
      {
        path: 'login',
        element: <LoginPage />,
      },
      {
        path: 'full-reading',
        element: <FullReadingPage />,
      },
      {
        path: 'daily-reading',
        element: <DailyReadingPage />,
      },
      // {
      //   path: 'profile',
      //   element: <ProfilePage />, // Remove props here - handle them in ProfilePage component
      // },
    ],
  },
])

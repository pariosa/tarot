import * as React from 'react'
import { createBrowserRouter } from 'react-router-dom'
import App from '../App'
import { AuthLayout } from '../layouts/AuthLayout'
import { MainLayout } from '../layouts/MainLayout'
import DailyReadingPage from '../pages/DailyReadingPage'
import FullReadingPage from '../pages/FullReadingPage'
import LoginPage from '../pages/Login'

export const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      // Public routes (no layout)
      {
        path: 'login',
        element: <LoginPage />,
      },

      // Protected routes with MainLayout and AuthLayout
      {
        element: <AuthLayout />,
        children: [
          {
            element: <MainLayout />,
            children: [
              {
                index: true,
                element: <FullReadingPage />,
              },
              {
                path: 'home',
                element: <FullReadingPage />,
              },
              {
                path: 'full-reading',
                element: <FullReadingPage />,
              },
              {
                path: 'daily-reading',
                element: <DailyReadingPage />,
              },
              // Add other protected routes here
            ],
          },
        ],
      },

      // You can add more layout wrappers as needed
    ],
  },
])

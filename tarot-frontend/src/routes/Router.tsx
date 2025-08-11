// src/routes/router.tsx
import * as React from 'react'
import { createBrowserRouter, Navigate } from 'react-router-dom'
import App from '../App'
import { ProtectedRoute } from '../Components/ProtectedRoute'
import { MainLayout } from '../layouts/MainLayout'
import DailyReadingPage from '../pages/DailyReadingPage'
import FullReadingPage from '../pages/FullReadingPage'
import LoginPage from '../pages/LoginPage'
import RegisterPage from '../pages/RegisterPage'

export const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      // Auth routes (unprotected)

      // Main routes (protected)
      {
        path: '/',
        element: <MainLayout />,
        children: [
          {
            index: true,
            element: <Navigate to='/home' replace />,
          },
          {
            path: 'home',
            element: (
              <ProtectedRoute>
                <FullReadingPage />
              </ProtectedRoute>
            ),
          },
          {
            path: 'full-reading',
            element: (
              <ProtectedRoute>
                <FullReadingPage />
              </ProtectedRoute>
            ),
          },
          {
            path: 'daily-reading',
            element: (
              <ProtectedRoute>
                <DailyReadingPage />
              </ProtectedRoute>
            ),
          },
        ],
      },

      // Public reading route
      {
        path: 'reading',
        element: <FullReadingPage />,
      },
      {
        path: 'login',
        element: <LoginPage />,
      },
      {
        path: 'register',
        element: <RegisterPage />,
      },

      // Redirect for old /login path
      // {
      //   path: 'login',
      //   element: <Navigate to='/auth/login' replace />,
      // },
    ],
  },
])

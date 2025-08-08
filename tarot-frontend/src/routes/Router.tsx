// src/router.tsx
import * as React from 'react'
import { createBrowserRouter } from 'react-router-dom'
import App from '../App'
import { ProtectedRoute } from '../Components/ProtectedRoute'
import { AuthLayout } from '../layouts/AuthLayout'
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
      // Public routes
      {
        path: 'login',
        element: <LoginPage />,
      },
      {
        path: 'register',
        element: <RegisterPage />,
      },

      // Protected routes
      {
        element: <AuthLayout />,
        children: [
          {
            element: <MainLayout />,
            children: [
              {
                path: '/',
                element: (
                  <ProtectedRoute>
                    <FullReadingPage />
                  </ProtectedRoute>
                ),
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
        ],
      },
    ],
  },
])

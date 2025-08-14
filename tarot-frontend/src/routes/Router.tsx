// src/routes/router.tsx
import * as React from 'react'
import { createBrowserRouter, Navigate } from 'react-router-dom'
import App from '../App'
import { ProtectedRoute } from '../Components/ProtectedRoute'
import StoryContainer from '../Components/StoryContainer'
import { MainLayout } from '../layouts/MainLayout'
import DailyReadingPage from '../pages/DailyReadingPage'
import ForgotPasswordPage from '../pages/ForgotPasswordPage'
import FullReadingPage from '../pages/FullReadingPage'
import HomePage from '../pages/HomePage'
import LoginPage from '../pages/LoginPage'
import PasswordResetPage from '../pages/PasswordResetPage'
import { ProfilePage } from '../pages/ProfilePage'
import RegisterPage from '../pages/RegisterPage'
import { StoryElementsPage } from '../pages/StoryElementsPage'

export const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,

    children: [
      // Auth routes (unprotected)
      {
        path: '/home',
        element: <HomePage />,
        children: [
          {
            path: 'login',
            element: <LoginPage />,
          },
          {
            path: 'register',
            element: <RegisterPage />,
          },
        ],
      },
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
          {
            path: 'story-elements',
            element: (
              <ProtectedRoute>
                <StoryElementsPage />
              </ProtectedRoute>
            ),
          },
          {
            path: 'story',
            element: (
              <ProtectedRoute>
                <StoryContainer />
              </ProtectedRoute>
            ),
          },
          {
            path: '/profile',
            element: (
              <ProtectedRoute>
                <ProfilePage />
              </ProtectedRoute>
            ),
          },
        ],
      },

      {
        path: 'login',
        element: <LoginPage />,
      },
      {
        path: 'register',
        element: <RegisterPage />,
      },
      {
        path: 'forgot-password',
        element: <ForgotPasswordPage />,
      },
      {
        path: 'reset-password',
        element: <PasswordResetPage />,
      },

      // Redirect for old /login path
      // {
      //   path: 'login',
      //   element: <Navigate to='/auth/login' replace />,
      // },
    ],
  },
])

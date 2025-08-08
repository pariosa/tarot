import * as React from 'react'
import { Navigate, Outlet } from 'react-router-dom'
import { useAuth } from '../hooks/UseAuth'

export function AuthLayout() {
  const { user } = useAuth()

  if (!user) {
    return <Navigate to='/login' replace />
  }

  return <Outlet />
}

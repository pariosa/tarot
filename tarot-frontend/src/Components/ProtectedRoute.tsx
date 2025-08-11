// src/components/ProtectedRoute.tsx
import * as React from 'react'
import { Navigate, useLocation } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth'

export const ProtectedRoute = ({ children }: { children: JSX.Element }) => {
  const { user, loading } = useAuth()
  const location = useLocation()

  if (loading) {
    return <div>Loading...</div> // Or a nice loading spinner
  }

  if (!user) {
    return <Navigate to='/login' state={{ from: location }} replace />
  }

  return children
}
export default ProtectedRoute

// src/components/ProtectedRoute.tsx
import * as React from 'react'
import { Navigate, useLocation } from 'react-router-dom'
import { useAuthContext } from '../hooks/useAuthContext'

export const ProtectedRoute = ({ children }: { children: JSX.Element }) => {
  const { user, loading } = useAuthContext()
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

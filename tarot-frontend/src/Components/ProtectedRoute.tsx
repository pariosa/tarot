import * as React from 'react'
import { PropsWithChildren } from 'react'
import { Navigate, Outlet } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth'

export function ProtectedRoute({
  children,
}: PropsWithChildren<{ children: unknown }>) {
  const { isAuthenticated } = useAuth()

  if (!isAuthenticated) {
    return <Navigate to='/login' />
  }

  // This forwards the context from the parent Outlet
  return (
    <>
      <Outlet />
      {children}
    </>
  )
}

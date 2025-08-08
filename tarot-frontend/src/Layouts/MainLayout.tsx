import * as React from 'react'
import { Outlet } from 'react-router-dom'
import { Navigation } from '../Components/Navigation/Navigation'

export function MainLayout() {
  return (
    <div className='main-layout'>
      <Navigation />
      <div className='content'>
        <Outlet />
      </div>
    </div>
  )
}

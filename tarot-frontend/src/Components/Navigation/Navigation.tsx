import * as React from 'react'
import { Link } from 'react-router-dom'
import './Navigation.css'

export function Navigation() {
  return (
    <nav className='main-nav'>
      <ul>
        <li>
          <Link to='/'>Home</Link>
        </li>
        <li>
          <Link to='/full-reading'>Full Reading</Link>
        </li>
        <li>
          <Link to='/daily-reading'>Daily Reading</Link>
        </li>
        <li>
          <Link to='/profile'>Profile</Link>
        </li>
        <li>
          <Link to='/profile'>Story Elements</Link>
        </li>
      </ul>
    </nav>
  )
}

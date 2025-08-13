import * as React from 'react'
import { Link } from 'react-router-dom'

export function Navigation() {
  const isLoggedIn = true // This would come from your auth state

  return (
    <nav className='bg-blue-50 p-4 shadow-sm'>
      <ul className='flex justify-center space-x-8'>
        <li>
          <Link
            to='/'
            className='flex items-center px-4 py-2 text-blue-800 hover:bg-blue-100 rounded-lg transition-colors duration-200'
          >
            <span className='mr-2'>🏰</span> Home
          </Link>
        </li>
        <li>
          <Link
            to='/full-reading'
            className='flex items-center px-4 py-2 text-blue-800 hover:bg-blue-100 rounded-lg transition-colors duration-200'
          >
            <span className='mr-2'>🔮</span> Full Reading
          </Link>
        </li>
        <li>
          <Link
            to='/daily-reading'
            className='flex items-center px-4 py-2 text-blue-800 hover:bg-blue-100 rounded-lg transition-colors duration-200'
          >
            <span className='mr-2'>✨</span> Daily Reading
          </Link>
        </li>
        <li>
          <Link
            to='/story-elements'
            className='flex items-center px-4 py-2 text-blue-800 hover:bg-blue-100 rounded-lg transition-colors duration-200'
          >
            <span className='mr-2'>📜</span> Story Elements
          </Link>
        </li>

        {isLoggedIn ? (
          <li className='relative group'>
            <button className='flex items-center px-4 py-2 text-blue-800 hover:bg-blue-100 rounded-lg transition-colors duration-200'>
              <span className='mr-2'>🧙‍♂️</span> Profile
              <svg
                className='w-4 h-4 ml-1'
                fill='none'
                stroke='currentColor'
                viewBox='0 0 24 24'
                xmlns='http://www.w3.org/2000/svg'
              >
                <path
                  strokeLinecap='round'
                  strokeLinejoin='round'
                  strokeWidth={2}
                  d='M19 9l-7 7-7-7'
                />
              </svg>
            </button>
            <div className='absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-200 z-10 border border-blue-100'>
              <div className='py-1'>
                <Link
                  to='/profile'
                  className='block px-4 py-2 text-blue-700 hover:bg-blue-50 hover:text-blue-900'
                >
                  <span className='mr-2'>👤</span> My Profile
                </Link>
                <Link
                  to='/logout'
                  className='block px-4 py-2 text-blue-700 hover:bg-blue-50 hover:text-blue-900'
                >
                  <span className='mr-2'>🚪</span> Logout
                </Link>
              </div>
            </div>
          </li>
        ) : (
          <li>
            <Link
              to='/login'
              className='flex items-center px-4 py-2 text-blue-800 hover:bg-blue-100 rounded-lg transition-colors duration-200'
            >
              <span className='mr-2'>🔑</span> Login
            </Link>
          </li>
        )}
      </ul>
    </nav>
  )
}

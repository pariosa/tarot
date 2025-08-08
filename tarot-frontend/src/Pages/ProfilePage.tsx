import * as React from 'react'
import { User } from '../types/User'

interface IProfilePageProps {
  user: User | null
  onLogout: () => void
}
export const ProfilePage = ({ user, onLogout }: IProfilePageProps) => {
  if (!user) {
    return <div>Please log in</div>
  }

  return (
    <div className='min-h-screen bg-gradient-to-br from-purple-50 via-indigo-50 to-pink-50'>
      <div className='max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-16'>
        <div className='bg-white rounded-2xl shadow-xl overflow-hidden'>
          <div className='bg-gradient-to-r from-purple-600 to-indigo-600 p-8 text-white'>
            <div className='flex items-center space-x-4'>
              <div className='w-16 h-16 bg-purple-500 rounded-full flex items-center justify-center'>
                <span className='text-2xl font-bold'>
                  {user.displayName?.[0] || user.email[0].toUpperCase()}
                </span>
              </div>
              <div>
                <h1 className='text-2xl font-bold'>
                  {user.displayName || 'User'}
                </h1>
                <p className='text-purple-100'>{user.email}</p>
              </div>
            </div>
          </div>

          <div className='p-8'>
            <div className='grid md:grid-cols-3 gap-6 mb-8'>
              <div className='bg-gray-50 p-6 rounded-xl text-center'>
                <div className='text-2xl font-bold text-purple-600'>12</div>
                <div className='text-gray-600'>Readings This Month</div>
              </div>
              <div className='bg-gray-50 p-6 rounded-xl text-center'>
                <div className='text-2xl font-bold text-purple-600'>47</div>
                <div className='text-gray-600'>Total Readings</div>
              </div>
              <div className='bg-gray-50 p-6 rounded-xl text-center'>
                <div className='text-2xl font-bold text-purple-600'>
                  Premium
                </div>
                <div className='text-gray-600'>Account Type</div>
              </div>
            </div>

            <div className='space-y-4'>
              <h3 className='text-lg font-semibold'>Recent Activity</h3>
              <div className='space-y-2'>
                <div className='p-4 bg-gray-50 rounded-lg flex justify-between items-center'>
                  <span>Daily Reading - The Moon</span>
                  <span className='text-gray-500 text-sm'>2 hours ago</span>
                </div>
                <div className='p-4 bg-gray-50 rounded-lg flex justify-between items-center'>
                  <span>Story Elements - Fantasy Theme</span>
                  <span className='text-gray-500 text-sm'>1 day ago</span>
                </div>
              </div>
            </div>

            <div className='mt-8 pt-8 border-t'>
              <button
                onClick={onLogout}
                className='bg-red-600 hover:bg-red-700 text-white px-6 py-2 rounded-lg transition-colors'
              >
                Logout
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
export default ProfilePage

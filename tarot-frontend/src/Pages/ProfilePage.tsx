// src/pages/ProfilePage.tsx
import * as React from 'react'
import { useEffect, useState } from 'react'
import { useAuthContext } from '../hooks/useAuthContext'
import { useApi } from '../services/api'
import { User } from '../types/User' // Import the User interface

export const ProfilePage = () => {
  const { user, logout } = useAuthContext()
  const api = useApi()
  const [profile, setProfile] = useState<User | null>(user || null)
  const [isEditing, setIsEditing] = useState(false)
  const [formData, setFormData] = useState({
    name: user?.name || '', // Changed from 'fullname' to 'name'
    email: user?.email || '',
  })

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const response = await api.get<User>('/users/me')
        if (response.data) {
          setProfile(response.data)
        }
      } catch (error) {
        console.error('Failed to fetch profile:', error)
        setProfile(null)
      }
    }
    if (user && !profile) {
      fetchProfile()
    }
  }, [user, profile, api])

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setFormData((prev) => ({ ...prev, [name]: value }))
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      const response = await api.put<User>('/users/me', formData)
      if (response.data) {
        setProfile(response.data)
        setIsEditing(false)
      }
    } catch (error) {
      console.error('Failed to update profile:', error)
    }
  }

  if (!user) {
    return <div>Please log in to view your profile</div>
  }

  return (
    <div className='max-w-2xl mx-auto p-4'>
      <h1 className='text-2xl font-bold mb-6'>Your Profile</h1>

      {isEditing ? (
        <form onSubmit={handleSubmit} className='space-y-4'>
          <div>
            <label className='block text-sm font-medium text-gray-700'>
              Name
            </label>
            <input
              type='text'
              name='name'
              value={formData.name}
              onChange={handleInputChange}
              className='mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2'
            />
          </div>
          <div>
            <label className='block text-sm font-medium text-gray-700'>
              Email
            </label>
            <input
              type='email'
              name='email'
              value={formData.email}
              onChange={handleInputChange}
              className='mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2'
              disabled
            />
          </div>
          <div className='flex space-x-2'>
            <button
              type='submit'
              className='px-4 py-2 bg-indigo-600 text-white rounded-md'
            >
              Save
            </button>
            <button
              type='button'
              onClick={() => setIsEditing(false)}
              className='px-4 py-2 bg-gray-200 rounded-md'
            >
              Cancel
            </button>
          </div>
        </form>
      ) : (
        <div className='space-y-4'>
          <div>
            <h2 className='text-lg font-medium'>Name</h2>
            <p>{profile?.name}</p> {/* Changed from 'fullname' to 'name' */}
          </div>
          <div>
            <h2 className='text-lg font-medium'>Email</h2>
            <p>{profile?.email}</p>
          </div>
          <button
            onClick={() => setIsEditing(true)}
            className='px-4 py-2 bg-indigo-600 text-white rounded-md'
          >
            Edit Profile
          </button>
          <button
            onClick={logout}
            className='ml-4 px-4 py-2 bg-red-600 text-white rounded-md'
          >
            Logout
          </button>
        </div>
      )}
    </div>
  )
}

import axios from 'axios'
import * as React from 'react'
import { useState } from 'react'
import apiService from '../services/api'

const PasswordChangePage = () => {
  const [activeMethod, setActiveMethod] = useState('direct') // 'direct' or 'email'

  // Direct change states
  const [currentPassword, setCurrentPassword] = useState('')
  const [newPassword, setNewPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')
  const [directLoading, setDirectLoading] = useState(false)
  const [directSuccess, setDirectSuccess] = useState(false)
  const [directError, setDirectError] = useState('')
  const [passwordError, setPasswordError] = useState('')

  // Email reset states
  const [emailLoading, setEmailLoading] = useState(false)
  const [emailSuccess, setEmailSuccess] = useState(false)
  const [emailError, setEmailError] = useState('')
  const [userEmail, setUserEmail] = useState('')

  const validatePasswords = () => {
    if (newPassword.length < 6) {
      setPasswordError('Password must be at least 6 characters long')
      return false
    }
    if (newPassword !== confirmPassword) {
      setPasswordError('New passwords do not match')
      return false
    }
    if (currentPassword === newPassword) {
      setPasswordError('New password must be different from current password')
      return false
    }
    setPasswordError('')
    return true
  }

  const handleDirectChange = async () => {
    setDirectError('')
    setDirectSuccess(false)

    if (!validatePasswords()) {
      return
    }

    setDirectLoading(true)

    try {
      // Replace with your API service call
      const response = await apiService.auth.changePassword({
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem(
            'tarot_app_auth_token'
          )}`,
        },
        body: JSON.stringify({
          currentPassword,
          newPassword,
        }),
      })

      if (response.ok) {
        setDirectSuccess(true)
        setCurrentPassword('')
        setNewPassword('')
        setConfirmPassword('')
        setTimeout(() => setDirectSuccess(false), 5000)
      } else {
        const data = await response.json()
        setDirectError(
          data.error || 'Failed to change password. Please try again.'
        )
      }
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        setDirectError(
          err.response?.data?.error ||
            'Failed to change password. Please try again.'
        )
      } else {
        console.error('Change password error:', err)
        setDirectError('Failed to change password. Please try again.')
      }
    } finally {
      setDirectLoading(false)
    }
  }

  const handleEmailReset = async () => {
    setEmailError('')
    setEmailSuccess(false)
    setEmailLoading(true)

    try {
      const response = await fetch(
        '/api/auth/issue-authed-user-password-reset-token',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${localStorage.getItem(
              'tarot_app_auth_token'
            )}`,
          },
        }
      )

      if (response.ok) {
        const data = await response.json()
        setEmailSuccess(true)
        setUserEmail(data.email)
        setTimeout(() => setEmailSuccess(false), 10000)
      } else {
        const data = await response.json()
        setEmailError(
          data.error || 'Failed to send reset email. Please try again.'
        )
      }
    } catch (err: unknown) {
      console.error('Email reset error:', err)
      if (axios.isAxiosError(err)) {
        setEmailError(
          err.response?.data?.error ||
            'Failed to send reset email. Please try again.'
        )
      } else {
        setEmailError('Failed to send reset email. Please try again.')
      }
    } finally {
      setEmailLoading(false)
    }
  }

  return (
    <div className='min-h-screen bg-gray-50 py-12 px-4 sm:px-6 lg:px-8'>
      <div className='max-w-2xl mx-auto'>
        {/* Header */}
        <div className='text-center mb-8'>
          <h1 className='text-3xl font-bold text-gray-900'>
            Change Your Password
          </h1>
          <p className='mt-2 text-gray-600'>
            Choose how you'd like to update your password
          </p>
        </div>

        <div className='bg-white shadow rounded-lg overflow-hidden'>
          {/* Method Selection Tabs */}
          <div className='border-b border-gray-200'>
            <nav className='flex -mb-px'>
              <button
                onClick={() => setActiveMethod('direct')}
                className={`w-1/2 py-4 px-1 text-center border-b-2 font-medium text-sm ${
                  activeMethod === 'direct'
                    ? 'border-indigo-500 text-indigo-600 bg-indigo-50'
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                }`}
              >
                <div className='flex items-center justify-center'>
                  <svg
                    className='w-5 h-5 mr-2'
                    fill='none'
                    viewBox='0 0 24 24'
                    stroke='currentColor'
                  >
                    <path
                      strokeLinecap='round'
                      strokeLinejoin='round'
                      strokeWidth={2}
                      d='M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z'
                    />
                  </svg>
                  Direct Change
                </div>
                <p className='text-xs text-gray-500 mt-1'>Change immediately</p>
              </button>
              <button
                onClick={() => setActiveMethod('email')}
                className={`w-1/2 py-4 px-1 text-center border-b-2 font-medium text-sm ${
                  activeMethod === 'email'
                    ? 'border-indigo-500 text-indigo-600 bg-indigo-50'
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                }`}
              >
                <div className='flex items-center justify-center'>
                  <svg
                    className='w-5 h-5 mr-2'
                    fill='none'
                    viewBox='0 0 24 24'
                    stroke='currentColor'
                  >
                    <path
                      strokeLinecap='round'
                      strokeLinejoin='round'
                      strokeWidth={2}
                      d='M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z'
                    />
                  </svg>
                  Email Reset
                </div>
                <p className='text-xs text-gray-500 mt-1'>Reset via email</p>
              </button>
            </nav>
          </div>

          <div className='p-6'>
            {/* Direct Password Change */}
            {activeMethod === 'direct' && (
              <div className='space-y-6'>
                <div className='bg-blue-50 border-l-4 border-blue-400 p-4'>
                  <div className='flex'>
                    <div className='flex-shrink-0'>
                      <svg
                        className='h-5 w-5 text-blue-400'
                        viewBox='0 0 20 20'
                        fill='currentColor'
                      >
                        <path
                          fillRule='evenodd'
                          d='M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z'
                          clipRule='evenodd'
                        />
                      </svg>
                    </div>
                    <div className='ml-3'>
                      <p className='text-sm text-blue-700'>
                        <strong>Instant Update:</strong> Change your password
                        immediately by providing your current password for
                        verification.
                      </p>
                    </div>
                  </div>
                </div>

                {directSuccess && (
                  <div className='bg-green-50 border border-green-200 rounded-md p-4'>
                    <div className='flex'>
                      <div className='flex-shrink-0'>
                        <svg
                          className='h-5 w-5 text-green-400'
                          viewBox='0 0 20 20'
                          fill='currentColor'
                        >
                          <path
                            fillRule='evenodd'
                            d='M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z'
                            clipRule='evenodd'
                          />
                        </svg>
                      </div>
                      <div className='ml-3'>
                        <h3 className='text-sm font-medium text-green-800'>
                          Password Changed Successfully!
                        </h3>
                        <p className='text-sm text-green-700 mt-1'>
                          Your password has been updated.
                        </p>
                      </div>
                    </div>
                  </div>
                )}

                <div className='space-y-4'>
                  <div>
                    <label
                      htmlFor='current-password'
                      className='block text-sm font-medium text-gray-700'
                    >
                      Current Password
                    </label>
                    <input
                      id='current-password'
                      type='password'
                      required
                      value={currentPassword}
                      onChange={(e) => setCurrentPassword(e.target.value)}
                      className='mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm'
                      placeholder='Enter your current password'
                    />
                  </div>

                  <div>
                    <label
                      htmlFor='new-password'
                      className='block text-sm font-medium text-gray-700'
                    >
                      New Password
                    </label>
                    <input
                      id='new-password'
                      type='password'
                      required
                      value={newPassword}
                      onChange={(e) => setNewPassword(e.target.value)}
                      className='mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm'
                      placeholder='Enter your new password'
                    />
                  </div>

                  <div>
                    <label
                      htmlFor='confirm-password'
                      className='block text-sm font-medium text-gray-700'
                    >
                      Confirm New Password
                    </label>
                    <input
                      id='confirm-password'
                      type='password'
                      required
                      value={confirmPassword}
                      onChange={(e) => setConfirmPassword(e.target.value)}
                      className='mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm'
                      placeholder='Confirm your new password'
                    />
                  </div>
                </div>

                {passwordError && (
                  <div className='text-red-600 text-sm bg-red-50 border border-red-200 rounded-md p-3'>
                    {passwordError}
                  </div>
                )}

                {directError && (
                  <div className='bg-red-50 border border-red-200 rounded-md p-4'>
                    <div className='text-red-600 text-sm'>{directError}</div>
                  </div>
                )}

                <button
                  onClick={handleDirectChange}
                  disabled={
                    directLoading ||
                    !currentPassword ||
                    !newPassword ||
                    !confirmPassword
                  }
                  className='w-full flex justify-center py-3 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:bg-gray-400 disabled:cursor-not-allowed'
                >
                  {directLoading ? (
                    <>
                      <div className='animate-spin rounded-full h-4 w-4 border-b-2 border-white mr-2'></div>
                      Changing Password...
                    </>
                  ) : (
                    'Change Password'
                  )}
                </button>
              </div>
            )}

            {/* Email Reset Method */}
            {activeMethod === 'email' && (
              <div className='space-y-6'>
                <div className='bg-amber-50 border-l-4 border-amber-400 p-4'>
                  <div className='flex'>
                    <div className='flex-shrink-0'>
                      <svg
                        className='h-5 w-5 text-amber-400'
                        viewBox='0 0 20 20'
                        fill='currentColor'
                      >
                        <path
                          fillRule='evenodd'
                          d='M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z'
                          clipRule='evenodd'
                        />
                      </svg>
                    </div>
                    <div className='ml-3'>
                      <p className='text-sm text-amber-700'>
                        <strong>Enhanced Security:</strong> We'll send a secure
                        password reset link to your email address. This method
                        is ideal for extra security verification.
                      </p>
                    </div>
                  </div>
                </div>

                {emailSuccess && (
                  <div className='bg-green-50 border border-green-200 rounded-md p-4'>
                    <div className='flex'>
                      <div className='flex-shrink-0'>
                        <svg
                          className='h-5 w-5 text-green-400'
                          viewBox='0 0 20 20'
                          fill='currentColor'
                        >
                          <path
                            fillRule='evenodd'
                            d='M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z'
                            clipRule='evenodd'
                          />
                        </svg>
                      </div>
                      <div className='ml-3'>
                        <h3 className='text-sm font-medium text-green-800'>
                          Reset Email Sent!
                        </h3>
                        <p className='text-sm text-green-700 mt-1'>
                          We've sent a password reset link to{' '}
                          <strong>{userEmail}</strong>. Check your inbox and
                          follow the instructions to reset your password. The
                          link will expire in 1 hour.
                        </p>
                      </div>
                    </div>
                  </div>
                )}

                {emailError && (
                  <div className='bg-red-50 border border-red-200 rounded-md p-4'>
                    <div className='text-red-600 text-sm'>{emailError}</div>
                  </div>
                )}

                <div className='text-center py-8'>
                  <div className='mb-6'>
                    <svg
                      className='mx-auto h-16 w-16 text-gray-400'
                      fill='none'
                      viewBox='0 0 24 24'
                      stroke='currentColor'
                    >
                      <path
                        strokeLinecap='round'
                        strokeLinejoin='round'
                        strokeWidth={1.5}
                        d='M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z'
                      />
                    </svg>
                  </div>
                  <h3 className='text-lg font-medium text-gray-900 mb-2'>
                    Send Password Reset Email
                  </h3>
                  <p className='text-gray-600 text-sm mb-8 max-w-md mx-auto'>
                    We'll send a secure password reset link to your registered
                    email address. You can then follow the link to create a new
                    password.
                  </p>

                  <button
                    onClick={handleEmailReset}
                    disabled={emailLoading}
                    className='inline-flex justify-center items-center px-6 py-3 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:bg-gray-400 disabled:cursor-not-allowed'
                  >
                    {emailLoading ? (
                      <>
                        <div className='animate-spin rounded-full h-4 w-4 border-b-2 border-white mr-2'></div>
                        Sending Email...
                      </>
                    ) : (
                      <>
                        <svg
                          className='w-4 h-4 mr-2'
                          fill='none'
                          viewBox='0 0 24 24'
                          stroke='currentColor'
                        >
                          <path
                            strokeLinecap='round'
                            strokeLinejoin='round'
                            strokeWidth={2}
                            d='M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z'
                          />
                        </svg>
                        Send Reset Email
                      </>
                    )}
                  </button>
                </div>
              </div>
            )}
          </div>
        </div>

        {/* Navigation */}
        <div className='mt-6 text-center'>
          <button
            onClick={() => window.history.back()}
            className='text-indigo-600 hover:text-indigo-500 text-sm font-medium'
          >
            ← Back to Profile
          </button>
        </div>
      </div>
    </div>
  )
}

export default PasswordChangePage

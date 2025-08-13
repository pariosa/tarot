import * as React from 'react'
import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import apiService from '../services/api'

const ResetPasswordPage = () => {
  const [token, setToken] = useState('')
  const [newPassword, setNewPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')
  const [loading, setLoading] = useState(false)
  const [validatingToken, setValidatingToken] = useState(true)
  const [isTokenValid, setIsTokenValid] = useState(false)
  const [success, setSuccess] = useState(false)
  const [error, setError] = useState('')
  const [passwordError, setPasswordError] = useState('')

  useEffect(() => {
    // Extract token from URL query parameters
    const urlParams = new URLSearchParams(window.location.search)
    const tokenFromUrl = urlParams.get('token')

    if (tokenFromUrl) {
      setToken(tokenFromUrl)
      validateToken(tokenFromUrl)
    } else {
      setError('No reset token provided')
      setValidatingToken(false)
    }
  }, [])

  const validateToken = async (tokenToValidate: string) => {
    try {
      const response = await apiService.auth.validateResetToken(tokenToValidate)
      console.log('Token validation response:', response)
      setIsTokenValid(response.data.valid)
      if (!response.data.valid) {
        setError(response.data.message || 'Invalid or expired token')
      }
    } catch (err) {
      console.error('Token validation error:', err)
      setError('Invalid or expired reset token')
      setIsTokenValid(false)
    } finally {
      setValidatingToken(false)
    }
  }

  const validatePasswords = () => {
    if (newPassword.length < 6) {
      setPasswordError('Password must be at least 6 characters long')
      return false
    }
    if (newPassword !== confirmPassword) {
      setPasswordError('Passwords do not match')
      return false
    }
    setPasswordError('')
    return true
  }

  const handleSubmit = async () => {
    setError('')

    if (!validatePasswords()) {
      return
    }

    setLoading(true)

    try {
      const response = await apiService.auth.resetPassword({
        token,
        newPassword,
      })
      console.log('Reset password response:', response)
      setSuccess(true)
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (err: any) {
      console.error('Reset password error:', err)
      setError(
        err.response?.data?.message ||
          'Failed to reset password. Please try again.'
      )
    } finally {
      setLoading(false)
    }
  }

  // Loading state while validating token
  if (validatingToken) {
    return (
      <div className='min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8'>
        <div className='max-w-md w-full space-y-8 bg-white p-8 rounded-lg shadow-md'>
          <div className='text-center'>
            <div className='animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600 mx-auto'></div>
            <h2 className='mt-6 text-2xl font-bold text-gray-900'>
              Validating reset link...
            </h2>
          </div>
        </div>
      </div>
    )
  }

  // Success state
  if (success) {
    return (
      <div className='min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8'>
        <div className='max-w-md w-full space-y-8 bg-white p-8 rounded-lg shadow-md'>
          <div className='text-center'>
            <div className='mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-green-100'>
              <svg
                className='h-8 w-8 text-green-600'
                fill='none'
                viewBox='0 0 24 24'
                stroke='currentColor'
              >
                <path
                  strokeLinecap='round'
                  strokeLinejoin='round'
                  strokeWidth='2'
                  d='M5 13l4 4L19 7'
                />
              </svg>
            </div>
            <h2 className='mt-6 text-3xl font-extrabold text-gray-900'>
              Password Reset Successful!
            </h2>
            <p className='mt-2 text-sm text-gray-600'>
              Your password has been successfully updated. You can now sign in
              with your new password.
            </p>
          </div>

          <div className='mt-8'>
            <Link
              to='/login'
              className='group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500'
            >
              Sign in with new password
            </Link>
          </div>
        </div>
      </div>
    )
  }

  // Invalid token state
  if (!isTokenValid) {
    return (
      <div className='min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8'>
        <div className='max-w-md w-full space-y-8 bg-white p-8 rounded-lg shadow-md'>
          <div className='text-center'>
            <div className='mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-red-100'>
              <svg
                className='h-8 w-8 text-red-600'
                fill='none'
                viewBox='0 0 24 24'
                stroke='currentColor'
              >
                <path
                  strokeLinecap='round'
                  strokeLinejoin='round'
                  strokeWidth='2'
                  d='M6 18L18 6M6 6l12 12'
                />
              </svg>
            </div>
            <h2 className='mt-6 text-3xl font-extrabold text-gray-900'>
              Invalid Reset Link
            </h2>
            <p className='mt-2 text-sm text-gray-600'>
              {error || 'This password reset link is invalid or has expired.'}
            </p>
          </div>

          <div className='space-y-4'>
            <Link
              to='/forgot-password'
              className='group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500'
            >
              Request new reset link
            </Link>
            <Link
              to='/login'
              className='group relative w-full flex justify-center py-2 px-4 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500'
            >
              Back to login
            </Link>
          </div>
        </div>
      </div>
    )
  }

  // Main password reset form
  return (
    <div className='min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8'>
      <div className='max-w-md w-full space-y-8 bg-white p-8 rounded-lg shadow-md'>
        <div>
          <h2 className='mt-6 text-center text-3xl font-extrabold text-gray-900'>
            Reset your password
          </h2>
          <p className='mt-2 text-center text-sm text-gray-600'>
            Enter your new password below
          </p>
        </div>

        <div className='mt-8 space-y-6'>
          <div className='space-y-4'>
            <div>
              <label
                htmlFor='new-password'
                className='block text-sm font-medium text-gray-700'
              >
                New Password
              </label>
              <input
                id='new-password'
                name='new-password'
                type='password'
                required
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                className='mt-1 appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm'
                placeholder='Enter your new password'
              />
            </div>

            <div>
              <label
                htmlFor='confirm-password'
                className='block text-sm font-medium text-gray-700'
              >
                Confirm Password
              </label>
              <input
                id='confirm-password'
                name='confirm-password'
                type='password'
                required
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                className='mt-1 appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm'
                placeholder='Confirm your new password'
              />
            </div>
          </div>

          {passwordError && (
            <div className='text-red-600 text-sm'>{passwordError}</div>
          )}

          {error && (
            <div className='bg-red-50 border border-red-200 rounded-md p-4'>
              <div className='text-red-600 text-sm'>{error}</div>
            </div>
          )}

          <div>
            <button
              onClick={handleSubmit}
              disabled={loading || !newPassword || !confirmPassword}
              className='group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:bg-gray-400 disabled:cursor-not-allowed'
            >
              {loading ? (
                <>
                  <div className='animate-spin rounded-full h-4 w-4 border-b-2 border-white mr-2'></div>
                  Resetting password...
                </>
              ) : (
                'Reset password'
              )}
            </button>
          </div>

          <div className='text-center'>
            <Link
              to='/login'
              className='text-sm text-indigo-600 hover:text-indigo-500'
            >
              Back to login
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ResetPasswordPage

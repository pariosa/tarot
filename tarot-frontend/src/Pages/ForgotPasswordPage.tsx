import * as React from 'react'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import apiService from '../services/api'

const ForgotPasswordPage = () => {
  const [email, setEmail] = useState('')
  const [loading, setLoading] = useState(false)
  const [success, setSuccess] = useState(false)
  const [error, setError] = useState('')

  const handleSubmit = async (e: { preventDefault: () => void }) => {
    e.preventDefault()
    setLoading(true)
    setError('')

    try {
      const response = await apiService.auth.forgotPassword({ email })
      console.log('Forgot password response:', response)
      setSuccess(true)
    } catch (err) {
      console.error('Forgot password error:', err)
      setError('Failed to send reset email. Please try again.')
    } finally {
      setLoading(false)
    }
  }

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
              Check Your Email
            </h2>
            <p className='mt-2 text-sm text-gray-600'>
              If an account with that email address exists, we've sent you
              password reset instructions.
            </p>
          </div>

          <div className='mt-8'>
            <div className='rounded-md bg-blue-50 p-4'>
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
                  <h3 className='text-sm font-medium text-blue-800'>
                    What's next?
                  </h3>
                  <div className='mt-2 text-sm text-blue-700'>
                    <ul className='list-disc pl-5 space-y-1'>
                      <li>
                        Check your inbox (and spam folder) for an email from us
                      </li>
                      <li>Click the reset link in the email</li>
                      <li>Create a new password</li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div className='text-center'>
            <Link
              to='/login'
              className='font-medium text-indigo-600 hover:text-indigo-500'
            >
              ← Back to sign in
            </Link>
          </div>

          <div className='text-center text-sm'>
            <p className='text-gray-500'>
              Didn't receive an email?{' '}
              <button
                onClick={() => {
                  setSuccess(false)
                  setEmail('')
                }}
                className='font-medium text-indigo-600 hover:text-indigo-500'
              >
                Try again
              </button>
            </p>
          </div>
        </div>
      </div>
    )
  }

  return (
    <div className='min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8'>
      <div className='max-w-md w-full space-y-8 bg-white p-8 rounded-lg shadow-md'>
        <div className='text-center'>
          <h2 className='mt-6 text-3xl font-extrabold text-gray-900'>
            Forgot your password?
          </h2>
          <p className='mt-2 text-sm text-gray-600'>
            Enter your email address and we'll send you a link to reset your
            password.
          </p>
        </div>

        {error && (
          <div className='rounded-md bg-red-50 p-4'>
            <div className='flex'>
              <div className='flex-shrink-0'>
                <svg
                  className='h-5 w-5 text-red-400'
                  viewBox='0 0 20 20'
                  fill='currentColor'
                >
                  <path
                    fillRule='evenodd'
                    d='M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z'
                    clipRule='evenodd'
                  />
                </svg>
              </div>
              <div className='ml-3'>
                <h3 className='text-sm font-medium text-red-800'>{error}</h3>
              </div>
            </div>
          </div>
        )}

        <div className='mt-8 space-y-6'>
          <div>
            <label
              htmlFor='email'
              className='block text-sm font-medium text-gray-700 mb-1'
            >
              Email address
            </label>
            <input
              id='email'
              name='email'
              type='email'
              autoComplete='email'
              required
              className='appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm'
              placeholder='you@example.com'
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div>
            <button
              type='button'
              onClick={handleSubmit}
              disabled={loading}
              className={`group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 ${
                loading ? 'opacity-75 cursor-not-allowed' : ''
              }`}
            >
              {loading ? 'Sending...' : 'Send reset email'}
            </button>
          </div>
        </div>

        <div className='text-center'>
          <Link
            to='/login'
            className='font-medium text-indigo-600 hover:text-indigo-500'
          >
            ← Back to sign in
          </Link>
        </div>

        <div className='text-center text-sm'>
          <p className='text-gray-600'>
            Don't have an account?{' '}
            <Link
              to='/register'
              className='font-medium text-indigo-600 hover:text-indigo-500'
            >
              Sign up
            </Link>
          </p>
        </div>
      </div>
    </div>
  )
}

export default ForgotPasswordPage

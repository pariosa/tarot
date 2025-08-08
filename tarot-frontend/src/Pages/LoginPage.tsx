// src/pages/Login.tsx

import { FirebaseError } from 'firebase/app'
import * as React from 'react'
import { useState } from 'react'
import { Link, useLocation, useNavigate } from 'react-router'
import { useAuthContext } from '../hooks/useAuthContext'

export function LoginPage() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [isLoading, setIsLoading] = useState(false)
  const { loginWithEmail, loginWithGoogle } = useAuthContext()
  const navigate = useNavigate()
  const location = useLocation()

  const from = location.state?.from?.pathname || '/home'

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')
    setIsLoading(true)

    try {
      await loginWithEmail(email, password)
      navigate(from, { replace: true })
    } catch (err) {
      let errorMessage = 'Login failed'

      if (err instanceof FirebaseError) {
        switch (err.code) {
          case 'auth/invalid-email':
            errorMessage = 'Invalid email address'
            break
          case 'auth/user-disabled':
            errorMessage = 'Account disabled'
            break
          case 'auth/user-not-found':
            errorMessage = 'User not found'
            break
          case 'auth/wrong-password':
            errorMessage = 'Incorrect password'
            break
          default:
            errorMessage = err.message
        }
      } else if (err instanceof Error) {
        errorMessage = err.message
      }

      setError(errorMessage)
    } finally {
      setIsLoading(false)
    }
  }

  const handleGoogleLogin = async () => {
    try {
      await loginWithGoogle()
      navigate(from, { replace: true })
    } catch (err) {
      setError('Google login failed. Please try again.')
    }
  }

  return (
    <div className='min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8'>
      <div className='max-w-md w-full space-y-8 bg-white p-8 rounded-lg shadow-md'>
        <div className='text-center'>
          <h2 className='mt-6 text-3xl font-extrabold text-gray-900'>
            Sign in to your account
          </h2>
        </div>

        {error && (
          <div className='rounded-md bg-red-50 p-4'>
            <div className='flex'>
              <div className='ml-3'>
                <h3 className='text-sm font-medium text-red-800'>{error}</h3>
              </div>
            </div>
          </div>
        )}

        <form className='mt-8 space-y-6' onSubmit={handleSubmit}>
          <div className='rounded-md shadow-sm space-y-4'>
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
              <label
                htmlFor='password'
                className='block text-sm font-medium text-gray-700 mb-1'
              >
                Password
              </label>
              <input
                id='password'
                name='password'
                type='password'
                autoComplete='current-password'
                required
                className='appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm'
                placeholder='••••••••'
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
          </div>

          <div className='flex items-center justify-between'>
            <div className='flex items-center'>
              <input
                id='remember-me'
                name='remember-me'
                type='checkbox'
                className='h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded'
              />
              <label
                htmlFor='remember-me'
                className='ml-2 block text-sm text-gray-700'
              >
                Remember me
              </label>
            </div>

            <div className='text-sm'>
              <Link
                to='/forgot-password'
                className='font-medium text-indigo-600 hover:text-indigo-500'
              >
                Forgot your password?
              </Link>
            </div>
          </div>

          <div>
            <button
              type='submit'
              disabled={isLoading}
              className={`group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 ${
                isLoading ? 'opacity-75 cursor-not-allowed' : ''
              }`}
            >
              {isLoading ? 'Signing in...' : 'Sign in'}
            </button>
          </div>
        </form>

        <div className='mt-6'>
          <div className='relative'>
            <div className='absolute inset-0 flex items-center'>
              <div className='w-full border-t border-gray-300' />
            </div>
            <div className='relative flex justify-center text-sm'>
              <span className='px-2 bg-white text-gray-500'>
                Or continue with
              </span>
            </div>
          </div>

          <div className='mt-6 grid grid-cols-1 gap-3'>
            <button
              onClick={handleGoogleLogin}
              className='w-full inline-flex justify-center py-2 px-4 border border-gray-300 rounded-md shadow-sm bg-white text-sm font-medium text-gray-500 hover:bg-gray-50'
            >
              <svg className='w-5 h-5' fill='currentColor' viewBox='0 0 24 24'>
                <path d='M12.545 10.239v3.821h5.445c-0.712 2.315-2.647 3.972-5.445 3.972-3.332 0-6.033-2.701-6.033-6.032s2.701-6.032 6.033-6.032c1.498 0 2.866 0.549 3.921 1.453l2.814-2.814c-1.786-1.664-4.153-2.675-6.735-2.675-5.522 0-10 4.477-10 10s4.478 10 10 10c8.396 0 10-7.524 10-10 0-0.668-0.068-1.324-0.182-1.972h-9.818z' />
              </svg>
              <span className='ml-2'>Sign in with Google</span>
            </button>
          </div>
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

export default LoginPage

/* eslint-disable @typescript-eslint/no-explicit-any */
// src/context/AuthContext.tsx
import * as React from 'react'
import {
  ReactNode,
  createContext,
  useCallback,
  useContext,
  useEffect,
  useState,
} from 'react'
import apiService from '../services/api'
import { User } from '../types/User.types'

interface AuthContextType {
  user: User | null
  loading: boolean
  token: string | null
  isAuthenticated: boolean
  register: (
    email: string,
    password: string,
    name: string
  ) => Promise<AuthResult>
  login: (email: string, password: string) => Promise<LoginResponse>
  logout: () => Promise<void>
  checkAuth: () => Promise<void>
}

interface AuthResult {
  success: boolean
  message?: string
  user?: User
}

interface LoginResponse {
  token: string
  user: User
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null)
  const [loading, setLoading] = useState(true)
  const [token, setToken] = useState<string | null>(null)

  // Constants
  const AUTH_TOKEN_KEY = 'tarot_app_auth_token'
  const isAuthenticated = !!token && !!user

  // Initialize auth state
  useEffect(() => {
    checkAuth()
  }, [])

  // Core authentication functions
  const checkAuth = useCallback(async () => {
    try {
      setLoading(true)
      const storedToken = localStorage.getItem(AUTH_TOKEN_KEY)

      if (!storedToken) {
        setLoading(false)
        return
      }

      // Verify token by fetching user data
      const response = await apiService.users.getMe()
      setUser(response.data)
      setToken(storedToken)
    } catch (error) {
      console.error('Auth check failed:', error)
      await logout()
    } finally {
      setLoading(false)
    }
  }, [])

  const register = async (
    email: string,
    password: string,
    name: string
  ): Promise<AuthResult> => {
    try {
      // Check if email exists
      const emailExists = await apiService.users.checkEmailExists(email)
      if (emailExists.data.exists) {
        return {
          success: false,
          message: 'Email already registered',
        }
      }

      // Register new user
      const response = await apiService.auth.register({
        email,
        password,
        name,
      })
      const { token, user } = response.data

      localStorage.setItem(AUTH_TOKEN_KEY, token)
      setToken(token)
      setUser(user)

      return {
        success: true,
        message: 'Registration successful',
        user,
      }
    } catch (error: any) {
      console.error('Registration error:', error)
      return {
        success: false,
        message: error.response?.data?.message || 'Registration failed',
      }
    }
  }

  const login = async (
    email: string,
    password: string
  ): Promise<LoginResponse> => {
    try {
      // Get token from login
      const loginResponse = await apiService.auth.login({ email, password })
      const { token } = loginResponse.data

      if (!token) {
        throw new Error('No token received from server')
      }

      // Store token first so subsequent API calls are authenticated
      localStorage.setItem(AUTH_TOKEN_KEY, token)
      setToken(token)

      // Now fetch user data with the token
      const userResponse = await apiService.users.getMe()
      const user = userResponse.data

      setUser(user)

      return { token, user }
    } catch (error) {
      console.error('Login error:', error)
      // Clean up on error
      localStorage.removeItem(AUTH_TOKEN_KEY)
      setToken(null)
      setUser(null)
      throw error
    }
  }

  const logout = async (): Promise<void> => {
    try {
      // Only attempt backend logout if we have a token
      if (token) {
        await apiService.auth.logout()
      }
    } catch (error) {
      console.error('Logout error:', error)
    } finally {
      localStorage.removeItem(AUTH_TOKEN_KEY)
      setToken(null)
      setUser(null)
    }
  }

  // Context value
  const value = {
    user,
    loading,
    token,
    isAuthenticated,
    register,
    login,
    logout,
    checkAuth,
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export const useAuth = () => {
  const context = useContext(AuthContext)
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider')
  }
  return context
}

// src/context/AuthContext.tsx
import * as React from 'react'
import {
  ReactNode,
  createContext,
  useContext,
  useEffect,
  useState,
} from 'react'
import { User } from '../types/User.types'

interface AuthContextType {
  user: User | null
  loading: boolean
  token: string | null
  authFetch: (url: string, options?: RequestInit) => Promise<Response>
  register: (
    email: string,
    password: string,
    name: string
  ) => Promise<AuthResult>
  login: (email: string, password: string) => Promise<void>
  logout: () => Promise<void>
  checkAuth: () => Promise<void>
}

interface AuthResult {
  success: boolean
  message?: string
  user?: User
}

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api'

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null)
  const [loading, setLoading] = useState(true)
  const [token, setToken] = useState<string | null>(null)
  // Check authentication status on initial load
  useEffect(() => {
    checkAuth()
  }, [])
  useEffect(() => {
    const storedToken = localStorage.getItem('jwt')
    if (storedToken) {
      validateToken(storedToken)
    } else {
      setLoading(false)
    }
  }, [])
  const validateToken = async (token: string) => {
    try {
      const response = await fetch(`${API_URL}/auth/validate`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })

      if (response.ok) {
        const userData = await response.json()
        setUser(userData)
        setToken(token)
      } else {
        logout()
      }
    } catch (error) {
      logout()
    } finally {
      setLoading(false)
    }
  }
  const checkAuth = async () => {
    try {
      const token = localStorage.getItem('jwt')
      if (!token) {
        setLoading(false)
        return
      }

      const response = await fetch(`${API_URL}/auth/me`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })

      if (response.ok) {
        const userData = await response.json()
        setUser(userData)
      } else {
        localStorage.removeItem('jwt')
        setUser(null)
      }
    } catch (error) {
      console.error('Authentication check failed:', error)
      localStorage.removeItem('jwt')
      setUser(null)
    } finally {
      setLoading(false)
    }
  }

  const register = async (
    email: string,
    password: string,
    name: string
  ): Promise<AuthResult> => {
    try {
      // Check if email exists
      const emailCheck = await fetch(
        `${API_URL}/users/check-email?email=${encodeURIComponent(email)}`
      )

      if (!emailCheck.ok) {
        throw new Error('Email check failed')
      }

      const emailData = await emailCheck.json()
      if (emailData.exists) {
        return {
          success: false,
          message: 'Email already registered',
        }
      }

      // Register new user
      const registerResponse = await fetch(`${API_URL}/auth/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password, name }),
      })

      if (!registerResponse.ok) {
        const errorData = await registerResponse.json()
        throw new Error(errorData.message || 'Registration failed')
      }

      const { token, user } = await registerResponse.json()
      localStorage.setItem('jwt', token)
      setUser(user)

      return {
        success: true,
        message: 'Registration successful',
        user,
      }
    } catch (error) {
      console.error('Registration error:', error)
      return {
        success: false,
        message: error instanceof Error ? error.message : 'Registration failed',
      }
    }
  }
  const authFetch = async (url: string, options: RequestInit = {}) => {
    const headers = {
      ...options.headers,
      Authorization: `Bearer ${token}`,
    }

    return fetch(`${API_URL}${url}`, { ...options, headers })
  }

  const login = async (email: string, password: string): Promise<void> => {
    try {
      const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      })

      if (!response.ok) {
        const errorData = await response.json()
        throw new Error(errorData.message || 'Login failed')
      }

      const { token, user } = await response.json()
      localStorage.setItem('jwt', token)
      setToken(token)
      setUser(user)
    } catch (error) {
      console.error('Login error:', error)
      throw error
    }
  }

  const logout = async (): Promise<void> => {
    try {
      // Optional: Call backend logout if needed
      await fetch(`${API_URL}/auth/logout`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwt')}`,
        },
      })
    } catch (error) {
      console.error('Logout error:', error)
    } finally {
      localStorage.removeItem('jwt')
      setUser(null)
    }
  }

  const value = {
    user,
    loading,
    register,
    token,
    authFetch,
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

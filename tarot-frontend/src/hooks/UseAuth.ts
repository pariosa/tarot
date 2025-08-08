// src/hooks/useAuth.ts
import {
  User as FirebaseUser,
  createUserWithEmailAndPassword,
  onAuthStateChanged,
  signInWithEmailAndPassword,
  signInWithPopup,
  signOut,
} from 'firebase/auth'
import { useEffect, useState } from 'react'
import { auth, googleProvider } from '../firebaseConfig'
import { User } from '../types/User'

export const useAuth = () => {
  const [user, setUser] = useState<User | null>(null)
  const [firebaseUser, setFirebaseUser] = useState<FirebaseUser | null>(null)
  const [loading, setLoading] = useState(true)
  const [token, setToken] = useState<string | null>(null)

  // Get JWT token from backend after Firebase auth
  const getBackendToken = async (firebaseToken: string) => {
    try {
      const response = await fetch('/api/auth/token', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ token: firebaseToken }),
      })

      if (!response.ok) throw new Error('Failed to get backend token')

      const data = await response.json()
      localStorage.setItem('jwtToken', data.token)
      return data.token
    } catch (error) {
      console.error('Token exchange failed:', error)
      throw error
    }
  }

  // Listen for auth state changes
  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, async (firebaseUser) => {
      setFirebaseUser(firebaseUser)

      if (firebaseUser) {
        try {
          // Get Firebase token
          const firebaseToken = await firebaseUser.getIdToken()

          // Exchange for backend JWT
          const backendToken = await getBackendToken(firebaseToken)
          setToken(backendToken)

          // Get user profile from backend
          const response = await fetch(`/api/users/me`, {
            headers: {
              Authorization: `Bearer ${backendToken}`,
            },
          })

          if (response.ok) {
            const user = await response.json()
            setUser(user)
          } else {
            // If user doesn't exist in backend, create one
            await register(
              {
                firebaseUid: firebaseUser.uid,
                email: firebaseUser.email || '',
                name: firebaseUser.displayName || 'New User',
              },
              backendToken
            )
          }
        } catch (error) {
          console.error('Authentication failed:', error)
          await logout()
        }
      } else {
        setUser(null)
        setToken(null)
        localStorage.removeItem('jwtToken')
      }
      setLoading(false)
    })

    // Initialize token from storage if available
    const storedToken = localStorage.getItem('jwtToken')
    if (storedToken) {
      setToken(storedToken)
    }

    return unsubscribe
  }, [])

  const register = async (
    userData: {
      firebaseUid: string
      email: string
      name: string
    },
    token: string // Make this optional if needed
  ) => {
    try {
      const response = await fetch('/api/users', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          ...(token && { Authorization: `Bearer ${token}` }), // Only include if token exists
        },
        body: JSON.stringify(userData),
      })

      if (!response.ok) throw new Error('Failed to create user')

      const user = await response.json()
      setUser(user)
      return user
    } catch (error) {
      console.error('Registration failed:', error)
      throw error
    }
  }

  const loginWithEmail = async (email: string, password: string) => {
    try {
      const userCredential = await signInWithEmailAndPassword(
        auth,
        email,
        password
      )
      const token = await userCredential.user.getIdToken()
      const backendToken = await getBackendToken(token)
      setToken(backendToken)
      return backendToken
    } catch (error) {
      console.error('Login failed:', error)
      throw error
    }
  }

  const loginWithGoogle = async () => {
    try {
      const userCredential = await signInWithPopup(auth, googleProvider)
      const token = await userCredential.user.getIdToken()
      const backendToken = await getBackendToken(token)
      setToken(backendToken)
      return backendToken
    } catch (error) {
      console.error('Google login failed:', error)
      throw error
    }
  }

  const registerWithEmail = async (
    email: string,
    password: string,
    name: string
  ) => {
    try {
      const userCredential = await createUserWithEmailAndPassword(
        auth,
        email,
        password
      )
      const firebaseToken = await userCredential.user.getIdToken()
      const backendToken = await getBackendToken(firebaseToken)

      await register(
        {
          firebaseUid: userCredential.user.uid,
          email,
          name,
        },
        backendToken
      )

      return backendToken
    } catch (error) {
      console.error('Registration failed:', error)
      throw error
    }
  }

  const logout = async () => {
    try {
      await signOut(auth)
      setUser(null)
      setToken(null)
      localStorage.removeItem('jwtToken')
    } catch (error) {
      console.error('Logout failed:', error)
      throw error
    }
  }

  return {
    user,
    firebaseUser,
    token,
    loading,
    register, // Now exposed in the return value
    loginWithEmail,
    loginWithGoogle,
    registerWithEmail,
    logout,
  }
}

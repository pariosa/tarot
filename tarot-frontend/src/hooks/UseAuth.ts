// src/hooks/useAuth.ts
import {
  User as FirebaseUser,
  GoogleAuthProvider,
  createUserWithEmailAndPassword,
  onAuthStateChanged,
  signInWithEmailAndPassword,
  signInWithPopup,
  signOut,
} from 'firebase/auth'
import { useEffect, useState } from 'react'
import { auth } from '../firebaseConfig'
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
        // Use proxy path
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ idToken: firebaseToken }),
        credentials: 'include', // Important for cookies/sessions
      })

      if (!response.ok) {
        const error = await response.json()
        throw new Error(error.message || 'Failed to get backend token')
      }

      return await response.json()
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
      const provider = new GoogleAuthProvider()
      const result = await signInWithPopup(auth, provider)

      // Get the ID token directly from the credential
      const credential = GoogleAuthProvider.credentialFromResult(result)
      const firebaseToken = credential?.idToken

      if (!firebaseToken) {
        throw new Error('No ID token available')
      }

      // Exchange with backend
      const backendResponse = await fetch('/api/auth/token', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ token: firebaseToken }),
      })

      if (!backendResponse.ok) {
        throw new Error('Backend token exchange failed')
      }

      const { jwt } = await backendResponse.json()
      return jwt
    } catch (error) {
      console.error('Authentication failed:', error)
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
  const exchangeToken = async (user: User) => {
    try {
      const idToken = await user.getIdToken()
      const response = await fetch('/api/auth/token', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${idToken}`,
        },
        body: JSON.stringify({ token: idToken }),
      })

      if (!response.ok) {
        throw new Error('Token exchange failed')
      }

      return await response.json()
    } catch (error) {
      console.error('Token exchange error:', error)
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
    exchangeToken,
    register, // Now exposed in the return value
    loginWithEmail,
    loginWithGoogle,
    registerWithEmail,
    logout,
  }
}

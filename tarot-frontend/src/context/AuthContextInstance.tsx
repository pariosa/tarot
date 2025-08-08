// src/context/AuthContextInstance.ts
import { createContext } from 'react'
import { useAuth } from '../hooks/useAuth'

export const AuthContext = createContext<
  ReturnType<typeof useAuth> | undefined
>(undefined)

// store/authSlice.ts
import { createSlice } from '@reduxjs/toolkit'
import { User } from '../types/User'

interface AuthState {
  user: User
  isLoading: boolean
  error: string | null
}
const defaultUser = {
  username: '',
  displayName: '',
  email: '',
}
const initialState: AuthState = {
  user: defaultUser,
  isLoading: false,
  error: null,
}

export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    loginStart: (state) => {
      state.isLoading = true
      state.error = null
    },
    loginSuccess: (state, action) => {
      state.isLoading = false
      state.user = action.payload
    },
    loginFailure: (state, action) => {
      state.isLoading = false
      state.error = action.payload
    },
    logout: (state) => {
      state.user = defaultUser
    },
  },
})

export const { loginStart, loginSuccess, loginFailure, logout } =
  authSlice.actions
export default authSlice.reducer

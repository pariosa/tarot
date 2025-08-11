/* eslint-disable @typescript-eslint/no-explicit-any */
// src/api.ts
import axios, { AxiosInstance, InternalAxiosRequestConfig } from 'axios'
import { User } from '../types/User.types'

// Define your API response type
export interface ApiResponse<T> {
  data: T
  status: number
  statusText: string
}

// Define your DTOs
export interface UserRegistrationDTO {
  firebaseUid: string
  email: string
  name: string
}

export interface UserUpdateDTO {
  name: string
  email: string
}

// Create axios instance
const createApiInstance = (): AxiosInstance => {
  const instance = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080',
    headers: {
      'Content-Type': 'application/json',
    },
  })

  // Add request interceptor to include auth token
  instance.interceptors.request.use(
    async (config: InternalAxiosRequestConfig<any>) => {
      const token = await getAuthToken()
      if (token) {
        config.headers.Authorization = `Bearer ${token}`
      }
      return config
    }
  )

  return instance
}

// Helper function to get auth token (implement based on your auth system)
const getAuthToken = async (): Promise<string | null> => {
  // Implement based on your auth system (Firebase, JWT, etc.)
  // Example: return localStorage.getItem('token')
  return null
}

// Create the API instance
const api = createApiInstance()

// API methods
const apiService = {
  // User endpoints
  users: {
    // Create a new user (registration)
    create: (data: UserRegistrationDTO): Promise<ApiResponse<User>> =>
      api.post('/api/users', data).then((response: any) => response),

    // Get current user profile
    getMe: (): Promise<ApiResponse<User>> =>
      api.get('/api/users/me').then((response: any) => response),

    // Update current user profile
    updateMe: (data: UserUpdateDTO): Promise<ApiResponse<User>> =>
      api.put('/api/users/me', data).then((response: any) => response),

    // Get user by Firebase UID (admin or own user)
    getByFirebaseUid: (firebaseUid: string): Promise<ApiResponse<User>> =>
      api.get(`/api/users/${firebaseUid}`).then((response: any) => response),

    // Add role to user (admin only)
    addRole: (userId: number, role: string): Promise<ApiResponse<User>> =>
      api
        .post(`/api/users/${userId}/roles`, { role })
        .then((response: any) => response),
  },

  // Add other API endpoints here as needed
}

export default apiService

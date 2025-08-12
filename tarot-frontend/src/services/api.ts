/* eslint-disable @typescript-eslint/no-explicit-any */
// src/api.ts
import axios, {
  AxiosInstance,
  AxiosResponse,
  InternalAxiosRequestConfig,
} from 'axios'
import { User } from '../types/User.types'

// Define your API response type
export interface ApiResponse<T> {
  data: T
  status: number
  statusText: string
}

// Define your DTOs
export interface UserRegistrationDTO {
  firebaseUid?: string
  email: string
  name: string
  password: string
}

export interface UserUpdateDTO {
  name: string
  email: string
}

export interface LoginRequest {
  email: string
  password: string
}

export interface LoginResponse {
  token: string
  user: User
}

export interface StoryElementRequest {
  // Define based on your Java DTOs
  theme?: string
  location?: string
  characterTrait?: string
  moralValue?: string
  pointOfView?: string
  style?: string
  climaxEvent?: string
  keyword?: string
}
export interface CardNamesRequest {
  cardNames: string
}
// Create axios instance
const createApiInstance = (): AxiosInstance => {
  const instance = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080',
    headers: {
      'Content-Type': 'application/json',
    },
    withCredentials: true,
  })

  // Request interceptor
  instance.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
      const token = localStorage.getItem('tarot_app_auth_token')
      if (token) {
        config.headers.Authorization = `Bearer ${token}`
      }
      return config
    },
    (error) => {
      return Promise.reject(error)
    }
  )

  // Response interceptor
  instance.interceptors.response.use(
    (response) => response,
    (error) => {
      if (error.response?.status === 401) {
        localStorage.removeItem('tarot_app_auth_token')
        window.location.href = '/login'
      }
      return Promise.reject(error)
    }
  )

  return instance
}

// Create the API instance
const api = createApiInstance()

// API methods
const apiService = {
  // Auth endpoints
  auth: {
    login: (data: LoginRequest): Promise<AxiosResponse<LoginResponse>> =>
      api.post('/api/auth/login', data),
    register: (
      data: UserRegistrationDTO
    ): Promise<AxiosResponse<LoginResponse>> =>
      api.post('/api/auth/register', data),
    validate: (): Promise<AxiosResponse<void>> => api.get('/api/auth/validate'),
    logout: (): Promise<AxiosResponse<void>> => api.post('/api/auth/logout'),
  },

  // User endpoints
  users: {
    create: (data: UserRegistrationDTO): Promise<AxiosResponse<User>> =>
      api.post('/api/users', data),
    getMe: (): Promise<AxiosResponse<User>> => api.get('/api/users/me'),
    updateMe: (data: UserUpdateDTO): Promise<AxiosResponse<User>> =>
      api.put('/api/users/me', data),
    checkEmailExists: (
      email: string
    ): Promise<AxiosResponse<{ exists: boolean }>> =>
      api.get(`/api/users/check-email?email=${encodeURIComponent(email)}`),
    getByFirebaseUid: (firebaseUid: string): Promise<AxiosResponse<User>> =>
      api.get(`/api/users/${firebaseUid}`),
    addRole: (userId: number, role: string): Promise<AxiosResponse<User>> =>
      api.post(`/api/users/${userId}/roles`, { role }),
  },

  // Card endpoints
  cards: {
    getDailyCard: (): Promise<AxiosResponse<any>> =>
      api.get('/api/cards/daily'),
    getAllCards: (): Promise<AxiosResponse<any[]>> => api.get('/api/cards'),
    getCardById: (id: number): Promise<AxiosResponse<any>> =>
      api.get(`/api/cards/${id}`),
  },

  // Reading endpoints
  readings: {
    createReading: (data: any): Promise<AxiosResponse<any>> =>
      api.post('/api/readings', data),
    getUserReadings: (): Promise<AxiosResponse<any[]>> =>
      api.get('/api/readings'),
    getReadingById: (id: number): Promise<AxiosResponse<any>> =>
      api.get(`/api/readings/${id}`),
  },

  // Spread endpoints
  spread: {
    weightedSpread: (data: any): Promise<AxiosResponse<any>> =>
      api.post('/api/spread/weighted', data),
    parallelSpread: (data: any): Promise<AxiosResponse<any>> =>
      api.post('/api/spread/parallel', data),
    weightedParallelSpread: (count: number): Promise<AxiosResponse<any>> =>
      api.get(`/api/spread/parallel/weighted/${count}`),
  },

  // Draw endpoints
  draw: {
    parallelDraw: (): Promise<AxiosResponse<any>> =>
      api.get('/api/draw/parallel'),
    weightedParallelDraw: (): Promise<AxiosResponse<any>> =>
      api.get('/api/draw/parallel/weighted'),
  },

  // Story endpoints
  story: {
    getLocation: (): Promise<AxiosResponse<string>> =>
      api.get('/api/story/location'),
    getCharacterTrait: (): Promise<AxiosResponse<string>> =>
      api.get('/api/story/character-trait'),
    getTheme: (): Promise<AxiosResponse<string>> => api.get('/api/story/theme'),
    getKeyword: (): Promise<AxiosResponse<string>> =>
      api.get('/api/story/keyword'),
    getMoralValue: (): Promise<AxiosResponse<string>> =>
      api.get('/api/story/moral-value'),
    getPointOfView: (): Promise<AxiosResponse<string>> =>
      api.get('/api/story/point-of-view'),
    getStyle: (): Promise<AxiosResponse<string>> => api.get('/api/story/style'),
    getClimaxEvent: (): Promise<AxiosResponse<string>> =>
      api.get('/api/story/climax-event'),
    getRandomKeyword: (): Promise<AxiosResponse<string>> =>
      api.get('/api/story/random-keyword'),

    // FIXED: Now accepts data parameter and sends it to the correct endpoint
    getStoryDTO: (data: CardNamesRequest): Promise<AxiosResponse<any>> =>
      api.post('/getStoryDTO', data), // Note: endpoint path updated to match your Spring Boot controller

    fullReading: (data: StoryElementRequest): Promise<AxiosResponse<any>> =>
      api.post('/api/story/full-reading', data),
    completeElement: (data: StoryElementRequest): Promise<AxiosResponse<any>> =>
      api.post('/api/story/complete-element', data),
  },

  // Admin endpoints (protected)
  admin: {
    // Add admin-specific endpoints here
    getAllUsers: (): Promise<AxiosResponse<User[]>> =>
      api.get('/api/admin/users'),
  },
}

export default apiService

// src/services/api.ts
import { useAuthContext } from '../hooks/useAuthContext'
import { ApiResponse } from '../types/api.types'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api'

export const useApi = () => {
  const { token } = useAuthContext()

  const request = async <T>(
    endpoint: string,
    options: RequestInit = {}
  ): Promise<ApiResponse<T>> => {
    const headers = {
      'Content-Type': 'application/json',
      ...(token && { Authorization: `Bearer ${token}` }),
      ...options.headers,
    }

    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
      ...options,
      headers,
    })

    if (!response.ok) {
      const error = await response.json()
      throw new Error(error.message || 'Request failed')
    }

    return response.json()
  }

  return {
    get: <T>(endpoint: string) => request<T>(endpoint, { method: 'GET' }),
    post: <T>(endpoint: string, body: unknown) =>
      request<T>(endpoint, {
        method: 'POST',
        body: JSON.stringify(body),
      }),
    put: <T>(endpoint: string, body: unknown) =>
      request<T>(endpoint, {
        method: 'PUT',
        body: JSON.stringify(body),
      }),
    delete: <T>(endpoint: string) => request<T>(endpoint, { method: 'DELETE' }),
  }
}

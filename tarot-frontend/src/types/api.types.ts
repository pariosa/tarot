// src/types/api.ts
export interface ApiResponse<T> {
  data?: T
  error?: string
  success: boolean
}

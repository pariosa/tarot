// src/types/User.ts
export interface User {
  id: string
  firebaseUid: string
  email: string
  name: string // Changed from 'fullname' to 'name' to match your form
  createdAt?: string
  updatedAt?: string
}

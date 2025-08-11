// src/types/User.ts
export interface User {
  status: string
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  user: any
  id: string
  firebaseUid: string
  email: string
  name: string // Changed from 'fullname' to 'name' to match your form
  createdAt?: string
  updatedAt?: string
}

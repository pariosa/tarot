import * as React from 'react'
import { FC } from 'react'
interface ILoginPageProps {}

export const LoginPage: FC<ILoginPageProps> = () => {
  const onLogin = () => {
    console.log()
  }
  return (
    <div>
      <form onSubmit={onLogin}>
        <input type='text' />
        <input type='password' />
      </form>
    </div>
  )
}

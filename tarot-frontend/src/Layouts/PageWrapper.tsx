import * as React from 'react'
import { FC } from 'react'
import './page-wrapper.css'
interface IPageWrapperProps {
  children: JSX.Element
}

export const PageWrapper: FC<IPageWrapperProps> = ({ children }) => (
  <div className='page-wrapper w-full'>
    <header className='header w-full'>
      <h1>Tarot application</h1>
    </header>
    {children}
  </div>
)

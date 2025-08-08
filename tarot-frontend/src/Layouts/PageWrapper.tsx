import * as React from 'react'
import { ReactNode } from 'react'
import './PageWrapper.css'

type PageWrapperProps = {
  children: ReactNode
}

export function PageWrapper({ children }: PageWrapperProps) {
  return <div className='page-wrapper'>{children}</div>
}

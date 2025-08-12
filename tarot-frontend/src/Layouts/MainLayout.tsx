// MainLayout.tsx
import * as React from 'react'
import { useState } from 'react'
import { Outlet } from 'react-router-dom'
import { CardType } from '../Components/Card'
import { Navigation } from '../Components/Navigation/Navigation'
import apiService from '../services/api'

type AppContextType = {
  count: number
  cards: CardType[]
  activeSpreadType: string
  setCardsFlipped: (flipped: boolean) => void
  fetchTarotSpread: (num: number) => void
  handleSpreadTypeChange: (value: string, count: number) => void
  setCount: (count: number) => void
}

export function MainLayout() {
  const [count, setCount] = useState(3)
  const [cards, setCards] = useState<CardType[]>([])
  const [activeSpreadType, setActiveSpreadType] = useState('three-card')

  const setCardsFlipped = (flipped: boolean) => {
    // Your implementation here
    setCards((prevCards) => prevCards.map((card) => ({ ...card, flipped })))
  }

  async function fetchTarotSpread(num: number) {
    setCardsFlipped(true)

    const myHeaders = new Headers()
    myHeaders.append('Content-Type', 'application/json')

    try {
      const response = await apiService.spread.weightedParallelSpread(num)
      const jsonData: CardType[] = response.data // Axios stores data in `.data`
      setCards(jsonData)
      localStorage.setItem(
        'cardsDrawn',
        JSON.stringify(jsonData.map((card) => card.name))
      )
    } catch (error) {
      console.error('Failed to fetch tarot spread:', error)
    }
  }

  const handleSpreadTypeChange = (value: string, count: number) => {
    setActiveSpreadType(value)
    setCount(count)
  }

  const contextValue: AppContextType = {
    count,
    cards,
    activeSpreadType,
    setCardsFlipped,
    fetchTarotSpread,
    handleSpreadTypeChange,
    setCount,
  }

  return (
    <div className='main-layout'>
      <Navigation />
      {/* Your layout components here */}
      <Outlet context={contextValue} />
    </div>
  )
}

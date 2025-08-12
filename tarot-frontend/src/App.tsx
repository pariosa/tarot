import * as React from 'react'
import { useState } from 'react'
import { Outlet } from 'react-router-dom'
import { CardType } from './Components/Card'
import { PageWrapper } from './layouts/PageWrapper'
import apiService from './services/api'

function App() {
  const [count, setCount] = useState<number>(0)
  const [cards, setCards] = useState<CardType[]>([])
  const [activeSpreadType, setActiveSpreadType] = useState<string>('none')

  const setCardsFlipped = (flipped: boolean) => {
    console.log('setCardFlipped', flipped)
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
    fetchTarotSpread(count)
  }

  return (
    <PageWrapper>
      <Outlet
        context={{
          count,
          cards,
          activeSpreadType,
          setCardsFlipped,
          fetchTarotSpread,
          handleSpreadTypeChange,
          setCount,
        }}
      />
    </PageWrapper>
  )
}

export default App

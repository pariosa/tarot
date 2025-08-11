import * as React from 'react'
import { useState } from 'react'
import { Outlet } from 'react-router-dom'
import { CardType } from './Components/Card'
import { useAuth } from './hooks/useAuth'
import { PageWrapper } from './layouts/PageWrapper'

function App() {
  const [count, setCount] = useState<number>(0)
  const [cards, setCards] = useState<CardType[]>([])
  const [activeSpreadType, setActiveSpreadType] = useState<string>('none')

  const { authFetch } = useAuth()

  const setCardsFlipped = (flipped: boolean) => {
    console.log('setCardFlipped', flipped)
  }

  function fetchTarotSpread(num: number) {
    setCardsFlipped(true)

    const myHeaders = new Headers()
    myHeaders.append('Content-Type', 'application/json')

    authFetch(`/spread/parallel/weighted/${num}`)
      .then((response) => response.body as ReadableStream<Uint8Array>)
      .then(async (data) => {
        const reader = data.getReader()
        const { value } = await reader.read()
        const decodedText = new TextDecoder().decode(value)
        const jsonData: CardType[] = JSON.parse(decodedText)

        setCards(jsonData)
        localStorage.setItem(
          'cardsDrawn',
          JSON.stringify(jsonData.map((card) => card.name))
        )
      })
      .catch(console.error)
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

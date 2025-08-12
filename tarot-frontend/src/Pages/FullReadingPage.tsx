import * as React from 'react'
import { useOutletContext } from 'react-router-dom'
import { CardType } from '../Components/Card'
import { CardContainer } from '../Components/CardContainer'
import { SpreadSelector } from '../Components/SpreadSelector/SpreadSelector'
import StoryContainer from '../Components/StoryContainer'

type AppContextType = {
  count: number
  cards: CardType[]
  activeSpreadType: string
  setCardsFlipped: (flipped: boolean) => void
  fetchTarotSpread: (num: number) => void
  handleSpreadTypeChange: (value: string, count: number) => void
  setCount: (count: number) => void
}

export function FullReadingPage() {
  const context = useOutletContext<AppContextType>()

  // Handle case where context is not available
  if (!context) {
    console.error('FullReadingPage: Outlet context is not available')
    return (
      <div className='flex items-center justify-center min-h-screen'>
        <div className='text-center'>
          <h2 className='text-xl font-semibold text-gray-800 mb-2'>
            Loading...
          </h2>
          <p className='text-gray-600'>
            Setting up your tarot reading experience
          </p>
        </div>
      </div>
    )
  }

  const {
    count,
    cards,
    activeSpreadType,
    setCardsFlipped,
    fetchTarotSpread,
    handleSpreadTypeChange,
    setCount,
  } = context

  return (
    <div className='w-full' id='app'>
      <SpreadSelector
        activeSpreadType={activeSpreadType}
        count={count}
        onSpreadTypeChange={handleSpreadTypeChange}
        onCountChange={setCount}
        onDrawCards={() => fetchTarotSpread(count)}
      />

      <CardContainer
        activeSpreadType={activeSpreadType}
        setCardFlipped={setCardsFlipped}
        cards={cards}
      />
      <StoryContainer />
    </div>
  )
}

export default FullReadingPage

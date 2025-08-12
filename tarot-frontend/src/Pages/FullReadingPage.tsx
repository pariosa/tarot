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
  const {
    count,
    cards,
    activeSpreadType,
    setCardsFlipped,
    fetchTarotSpread,
    handleSpreadTypeChange,
    setCount,
  } = useOutletContext<AppContextType>()
  const context = useOutletContext<AppContextType>()

  if (!context) {
    return <div>Loading or error message...</div>
  }
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

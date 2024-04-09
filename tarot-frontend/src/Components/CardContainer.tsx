import * as React from 'react'
import { Card, CardType } from './Card'

export type CardContainerType = {
  cards: CardType[]
  setCardFlipped: (flipped: boolean) => void
}
export function CardContainer({ cards, setCardFlipped }: CardContainerType) {
  console.log(cards)
  return (
    <div onClick={() => console.log(cards)} className='card-container'>
      {cards?.map((card: CardType) => (
        <Card
          name={card.name}
          description={card.description}
          reversed={card.reversed}
          card_value={card.card_value}
          story={card.story}
          setCardFlipped={setCardFlipped}
        />
      ))}
    </div>
  )
}

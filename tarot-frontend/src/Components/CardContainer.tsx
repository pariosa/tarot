import * as React from 'react'
import { Card, CardType } from './Card'

export type CardContainerType = {
  cards: CardType[]
}
export function CardContainer({ cards }: CardContainerType) {
  console.log(cards)
  return (
    <div onClick={() => console.log(cards)} className='card-container'>
      {cards?.map((card: CardType) => (
        <Card
          name={card.name}
          description={card.description}
          reversed={card.reversed}
          story={card.story}
        />
      ))}
    </div>
  )
}

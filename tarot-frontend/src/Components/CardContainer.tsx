import * as React from 'react'
import { Card, CardType } from './Card'

export type CardContainerType = {
  cards: CardType[]
  activeSpreadType: string
  setCardFlipped: (flipped: boolean) => void
}
export function CardContainer({
  cards,
  activeSpreadType,
  setCardFlipped,
}: CardContainerType) {
  console.log(cards)

  return (
    <div
      onClick={() => console.log(cards)}
      className={` card-container ${activeSpreadType}`}
    >
      {cards?.map((card: CardType, num) => (
        <Card
          name={card.name}
          num={num}
          description={card.description}
          reversed={card.reversed}
          reversedDescription={card.reversedDescription}
          card_value={card.card_value}
          story={card.story}
          setCardFlipped={setCardFlipped}
        />
      ))}
    </div>
  )
}
export default CardContainer

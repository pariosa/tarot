import * as React from 'react'
import { cardsArray } from '../CardHelper'

export type CardType = {
  name: string
  description: string
  reversed: boolean
  story: string
}
export function Card({ name, description, reversed, story }: CardType) {
  const [flipped, setFlipped] = React.useState(false)
  const [isReversed, setIsReversed] = React.useState(false)
  console.log(name)
  console.log()
  React.useEffect(() => {
    setIsReversed(reversed)
  })
  return (
    <div onClick={() => setFlipped(!flipped)} className='card-container'>
      <div className=''>
        {name} - {description}
        <div>{story}</div>
        <div className={`card ${flipped ? 'flipped' : ''}`}>
          {cardsArray?.find((c) => c.name === name)?.image !== undefined && (
            <div>
              {' '}
              <img
                src={cardsArray.find((c) => c.name === name)?.image}
                alt={name}
                className={`card-image ${isReversed ? 'reversed' : ''}`}
              />{' '}
              {isReversed ? 'REVERSED' : ''}
            </div>
          )}
        </div>
      </div>
    </div>
  )
}

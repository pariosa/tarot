import * as React from 'react'
import { cardsArray } from '../CardHelper'

export type CardType = {
  name: string
  description: string
  reversed: boolean
  story: string
  reversedDescription: string
  card_value: string
  setCardFlipped: (flipped: boolean) => void
}
export function Card({
  name,
  description,
  reversedDescription,
  reversed,
  story,
  card_value,
  setCardFlipped,
}: CardType) {
  const [flipped, setFlipped] = React.useState(false)
  const [isReversed, setIsReversed] = React.useState(false)
  const [cardImgSrc, setCardImgSrc] = React.useState<string | undefined>('')
  console.log(name)
  console.log()
  React.useEffect(() => {
    setIsReversed(reversed)
    if (cardsArray.find((c) => c.name == card_value)?.image !== undefined) {
      setCardImgSrc(cardsArray.find((c) => c.name == card_value)?.image)
    }
  }, [isReversed, reversed, card_value])
  React.useEffect(() => {
    setFlipped(false)
  }, [setCardFlipped])
  console.log('Card value', card_value)
  console.log(cardsArray.find((c) => c.name == card_value))
  return (
    <div onClick={() => setFlipped(!flipped)} className='card w-1/5'>
      <div style={{}}>
        <div className={`card ${flipped ? 'flipped' : ''}`}>
          <div
            className='card-image'
            style={{
              display: 'grid',
              //   backgroundSize: 'contain',
              //   backgroundImage: 'url(./src/assets/card-back.png)',
            }}
          >
            <img
              src={flipped ? cardImgSrc : './src/assets/card-back.png'}
              alt={name}
              className={`card-image image1 ${isReversed ? 'reversed' : ''}`}
            />{' '}
          </div>
          {flipped && (
            <div className='text-1'>
              {name}({card_value}) {isReversed ? 'Reversed' : ''}-{' '}
              <p>
                {isReversed
                  ? `(Reversed): ${reversedDescription}`
                  : description}
              </p>
              <div>{story}</div>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}

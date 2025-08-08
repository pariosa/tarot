import * as React from 'react'
import { cardsArray } from '../CardHelper'
import './HolographicStyles.css'

export type CardType = {
  name: string
  description: string
  reversed: boolean
  story: string
  reversedDescription: string
  card_value: string
  num: number
  setCardFlipped: (flipped: boolean) => void
}

export function Card({
  name,
  description,
  reversedDescription,
  reversed,
  story,
  num,
  card_value,
  setCardFlipped,
}: CardType) {
  const nums = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j']
  const [flipped, setFlipped] = React.useState(false)
  const [isReversed, setIsReversed] = React.useState(false)
  const [cardImgSrc, setCardImgSrc] = React.useState<string | undefined>('')

  React.useEffect(() => {
    setIsReversed(reversed)
    if (cardsArray.find((c) => c.name == card_value)?.image !== undefined) {
      setCardImgSrc(cardsArray.find((c) => c.name == card_value)?.image)
    }
  }, [isReversed, reversed, card_value])

  React.useEffect(() => {
    setFlipped(false)
  }, [setCardFlipped])

  React.useEffect(() => {
    const perspectiveElements = [
      ...document.getElementsByClassName('perspective-0'),
      ...document.getElementsByClassName('perspective-1'),
      ...document.getElementsByClassName('perspective-2'),
      ...document.getElementsByClassName('perspective-3'),
      ...document.getElementsByClassName('perspective-4'),
      ...document.getElementsByClassName('perspective-5'),
      ...document.getElementsByClassName('perspective-6'),
    ] as HTMLElement[]

    const hoverListener = (event: MouseEvent) => {
      const parent = event.currentTarget as HTMLElement
      if (!parent) return

      const cardEl = parent.getElementsByClassName('card')[0] as HTMLElement
      const specularEl = parent.getElementsByClassName(
        'specular'
      )[0] as HTMLElement

      if (!cardEl || !specularEl) return

      const rect = parent.getBoundingClientRect()
      const x = event.clientX - rect.left
      const y = event.clientY - rect.top

      const xDirection = x / rect.width <= 0.5 ? -1 : 1
      const yDirection = y / rect.height <= 0.5 ? 1 : -1

      cardEl.style.setProperty('--rotateX', `${yDirection * 15}deg`)
      cardEl.style.setProperty('--rotateY', `${xDirection * 15}deg`)

      cardEl.style.setProperty(
        '--box-shadow-offset-x-1',
        `${-xDirection * 20}px`
      )
      cardEl.style.setProperty(
        '--box-shadow-offset-y-1',
        `${yDirection * 40}px`
      )
      cardEl.style.setProperty(
        '--box-shadow-offset-x-2',
        `${-xDirection * 15}px`
      )
      cardEl.style.setProperty(
        '--box-shadow-offset-y-2',
        `${yDirection * 30}px`
      )

      specularEl.style.setProperty('--top', `${yDirection === 1 ? 0 : -50}%`)
    }

    const mouseOutListener = (event: MouseEvent) => {
      const parent = event.currentTarget as HTMLElement
      if (!parent) return

      const cardEl = parent.getElementsByClassName('card')[0] as HTMLElement
      const specularEl = parent.getElementsByClassName(
        'specular'
      )[0] as HTMLElement

      if (!cardEl || !specularEl) return

      cardEl.style.setProperty('--rotateX', `0`)
      cardEl.style.setProperty('--rotateY', `0`)
      cardEl.style.setProperty('--box-shadow-offset-x-1', '0px')
      cardEl.style.setProperty('--box-shadow-offset-y-1', '20px')
      cardEl.style.setProperty('--box-shadow-offset-x-2', '0px')
      cardEl.style.setProperty('--box-shadow-offset-y-2', '15px')

      specularEl.style.setProperty('--top', '-25%')
    }

    perspectiveElements.forEach((el) => {
      el.addEventListener('mousemove', hoverListener)
      el.addEventListener('mouseout', mouseOutListener)
    })

    return () => {
      perspectiveElements.forEach((el) => {
        el.removeEventListener('mousemove', hoverListener)
        el.removeEventListener('mouseout', mouseOutListener)
      })
    }
  }, [])

  return (
    <div onClick={() => setFlipped(!flipped)} className={`${nums[num]}`}>
      <div className='demo'>
        <div className={`perspective perspective-${num}`}>
          <div
            className={`${nums[num]} card ${reversed ? 'reversed' : ''} ${
              flipped ? 'flipped' : ''
            }`}
          >
            <div className='card-image' style={{ display: 'grid' }}>
              <img
                src={flipped ? cardImgSrc : './src/assets/card-back.png'}
                className={`card-image image1q card-image`}
              />
              <div className='photo'></div>
              <div className='specular'></div>
            </div>
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

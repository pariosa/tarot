import * as React from 'react'
import { cardsArray } from '../CardHelper'
 
import  './HolographicStyles.css';
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
  }, [setCardFlipped]);
  const onMouseOver = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => { 
    const perspectiveEl = document.getElementsByClassName('perspective-0')[0] as unknown as HTMLElement;

    const perspectiveEl0 = document.getElementsByClassName('perspective-1')[0] as unknown as HTMLElement;
    const perspectiveEl1 = document.getElementsByClassName('perspective-2')[0] as unknown as HTMLElement;

    const perspectiveEl2 = document.getElementsByClassName('perspective-3')[0] as unknown as HTMLElement;

    const perspectiveEl3 = document.getElementsByClassName('perspective-4')[0] as unknown as HTMLElement;

    const perspectiveEl4 = document.getElementsByClassName('perspective-5')[0] as unknown as HTMLElement;

    const perspectiveEl5 = document.getElementsByClassName('perspective-6')[0] as unknown as HTMLElement;

    const perspectiveEl6 = document.getElementsByClassName('perspective-7')[0] as unknown as HTMLElement;
  
    const hoverListener =({ currentTarget, clientX, clientY }:MouseEvent) => {
      const parent = currentTarget as HTMLElement;
      console.log(`parent`,parent);
      if(!parent){
        return;
      }
      const cardEl = parent.getElementsByClassName('card')[0] as unknown  as HTMLElement;
      console.log(`cardEl`,cardEl);
      const specularEl = parent.getElementsByClassName('specular')[0] as unknown  as HTMLElement;
      if(!perspectiveEl || !cardEl || !specularEl) {
          return;
      }
      if(!currentTarget){ 
          return; 
      }
      const current = currentTarget as HTMLElement;
      const rect = current.getBoundingClientRect();
      const x = clientX - rect.left
      const y = clientY - rect.top

      const xDirection = x/rect.width <= 0.5 ? -1 : 1
      const yDirection = y/rect.height <= 0.5 ? 1 : -1
      console.log(`x`,yDirection);
      console.log(`y`, yDirection);
      cardEl.style.setProperty('--rotateX', `${yDirection*15}deg`)
      cardEl.style.setProperty('--rotateY', `${xDirection*15}deg`)

      cardEl.style.setProperty('--box-shadow-offset-x-1', `${-xDirection*20}px`)
      cardEl.style.setProperty('--box-shadow-offset-y-1', `${yDirection*40}px`)
      cardEl.style.setProperty('--box-shadow-offset-x-2', `${-xDirection*15}px`)
      cardEl.style.setProperty('--box-shadow-offset-y-2', `${yDirection*30}px`)

      specularEl.style.setProperty('--top', `${yDirection === 1 ? 0 : -50}%`)
  }
  const mouseOutListener= (event:any) => {
    const parent = event.currentTarget;
    console.log(`parent`,parent);
    const cardEl = parent.getElementsByClassName('card')[0] as unknown  as HTMLElement;
    console.log(`cardEl`,cardEl);
    const specularEl = parent.getElementsByClassName('specular')[0] as unknown  as HTMLElement;
    if(!perspectiveEl || !cardEl || !specularEl) {
        return;
    }
    if(!event.currentTarget){ 
        return; 
    }
 
    cardEl.style.setProperty('--rotateX', `0`)
    cardEl.style.setProperty('--rotateY', `0`)
    console.log(`event from perspective EL`,event);
    cardEl.style.setProperty('--box-shadow-offset-x-1', '0px')
    cardEl.style.setProperty('--box-shadow-offset-y-1', '20px')
    cardEl.style.setProperty('--box-shadow-offset-x-2', '0px')
    cardEl.style.setProperty('--box-shadow-offset-y-2', '15px')

    specularEl.style.setProperty('--top', '-25%')
};

  perspectiveEl.addEventListener('mouseout', mouseOutListener)
    
    perspectiveEl.addEventListener('mousemove', hoverListener)
    perspectiveEl0.addEventListener('mouseout', mouseOutListener)
    
    perspectiveEl0.addEventListener('mousemove', hoverListener)
    
  perspectiveEl1.addEventListener('mouseout', mouseOutListener)
    
  perspectiveEl1.addEventListener('mousemove', hoverListener) 
  
  perspectiveEl2.addEventListener('mouseout', mouseOutListener)
    
    perspectiveEl2.addEventListener('mousemove', hoverListener) 
    
  perspectiveEl3.addEventListener('mouseout', mouseOutListener)
    
  perspectiveEl3.addEventListener('mousemove', hoverListener) 
  
  perspectiveEl4.addEventListener('mouseout', mouseOutListener)
    
    perspectiveEl4.addEventListener('mousemove', hoverListener) 
    
  perspectiveEl5.addEventListener('mouseout', mouseOutListener)
    
  perspectiveEl5.addEventListener('mousemove', hoverListener) 
  
  perspectiveEl6.addEventListener('mouseout', mouseOutListener)
    
    perspectiveEl6.addEventListener('mousemove', hoverListener)  
};

  console.log('Card value', card_value)
  console.log(cardsArray.find((c) => c.name == card_value));
  return (
    <div  onClick={() => setFlipped(!flipped)} className={`${nums[num]}`}>
      <div className='demo'>
      <div className={`perspective perspective-${num}`} onMouseOver={onMouseOver}>
        <div className={`${nums[num]} card ${flipped ? 'flipped' : ''}`}>
          <div
            className='card-image'
            style={{
              display: 'grid',
            }}
          >
            <img
              src={flipped ? cardImgSrc : './src/assets/card-back.png'}
              className={`card-image image1q card-image`}
            /> 
               
            
            <div className="photo"></div>
            <div className="specular"></div>
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

import * as React from 'react'
import { useState } from 'react'
import './App.css'
import { CardType } from './Components/Card'
import { CardContainer } from './Components/CardContainer'
import StoryContainer from './Components/StoryContainer'
import { PageWrapper } from './Layouts/PageWrapper'

function App() {
  const [count, setCount] = useState(0)
  const [cards, setCards] = useState<CardType[]>([])
  const [activeSpreadType, setActiveSpreadType] = useState('none')
  const setCardsFlipped = (flipped: boolean) => {
    console.log('setCardFlipped', flipped)
  }
  const show = () => {
    setCardsFlipped(false)
  }
  function fetchTarotSpread(num: number) {
    // Flip all the cards back when shuffling the deck and drawing a new spread
    setCardsFlipped(true)

    const myHeaders = new Headers()
    myHeaders.append('Content-Type', 'application/json')

    const requestOptions = {
      method: 'GET',
      headers: myHeaders,
    }

    fetch(`api/spread/parallel/weighted/${num}`, requestOptions)
      .then((response) => response.body as ReadableStream<Uint8Array>)
      .then(async (data) => {
        const reader = data.getReader()
        const { value } = await reader.read()
        const decodedText = new TextDecoder().decode(value)
        const jsonData: CardType[] = JSON.parse(decodedText)

        // Save to state
        setCards(jsonData)

        // Extract names and store in localStorage
        const cardNames = jsonData.map((card) => card.name)
        localStorage.setItem('cardsDrawn', JSON.stringify(cardNames))

        console.log(jsonData)
      })
      .catch((error) => {
        console.error(error)
      })
  }
  // useEffect(() => {
  //   console.log('setCount', count)
  //   if (setCount) fetchTarotSpread()
  // }, [fetchTarotSpread, setCount])
  return (
    <PageWrapper>
      <div className='w-full' id='app'>
        <p>
          I want you to enter a number of tarot cards to draw. I will then
          return a list of cards. and let you make a story prompt based on those
          cards.
        </p>
        <div className='flex items-center space-x-2 rounded-md bg-gray-50 p-2'>
          <input
            onBlur={(e: React.ChangeEvent<HTMLInputElement>) =>
              setCount(parseInt(e.target?.value, 10))
            }
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setCount(parseInt(e.target?.value, 10))
            }
            placeholder='Number of Cards'
            type='number'
            value={count}
            className='border-none bg-transparent text-lg text-gray-900 focus:outline-none'
          />
          <button className='block'>
            <div>
              <i className='fas fa-eye text-lg'></i>
            </div>
            <div onClick={() => show()}>
              <i className='fas fa-eye-slash text-lg'></i>
            </div>
          </button>
        </div>
        <button onClick={() => fetchTarotSpread(count)}>Draw Cards</button>

        <div
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
            let newCount = 1
            setActiveSpreadType(e.target?.value)
            switch (e.target?.value) {
              case 'card-container-one':
                newCount = 1
                break
              case 'card-container-two-vertical':
                newCount = 2
                break
              case 'card-container-two-horizontal':
                newCount = 2
                break
              case 'card-container-three-story':
                newCount = 3
                break
              case 'card-container-three-triangle':
                newCount = 3
                break
              case 'card-container-three-inverted-triangle':
                newCount = 3
                break
              case 'card-container-four-cross':
                newCount = 4
                break
              case 'card-container-five-cross':
                newCount = 5
                break
              case 'card-container-five-pentagram':
                newCount = 5
                break
              case 'card-container-six-david':
                newCount = 6
                break
              case 'card-container-seven-david':
                newCount = 7
                break
              default:
                setCount(0)
            }
            setCount(newCount)
            fetchTarotSpread(newCount)
            console.log(e.target)
          }}
        >
          <input
            type='radio'
            value='card-container-one'
            data-value='1'
            name='spreadType'
            onClick={() => {
              setCount(1)
            }}
          />
          One
          <input
            type='radio'
            value='card-container-two-vertical'
            name='spreadType'
            onClick={() => {
              setCount(2)
            }}
          />
          Two Vertical
          <input
            type='radio'
            value='card-container-two-horizontal'
            name='spreadType'
            onClick={() => {
              setCount(2)
            }}
          />
          Two Horizontal
          <input
            type='radio'
            value='card-container-three-story'
            name='spreadType'
            onClick={() => {
              setCount(3)
            }}
          />{' '}
          Three Story
          <input
            type='radio'
            value='card-container-three-triangle'
            name='spreadType'
            onClick={() => {
              setCount(3)
            }}
          />{' '}
          Three Triangle
          <input
            type='radio'
            value='card-container-three-inverted-triangle'
            name='spreadType'
            onClick={() => {
              setCount(3)
            }}
          />{' '}
          Three Inverted Triangle
          <input
            type='radio'
            value='card-container-four-cross'
            name='spreadType'
            onSelect={() => {
              setCount(4)
            }}
          />{' '}
          Four Cross
          <input
            type='radio'
            value='card-container-five-pentagram'
            name='spreadType'
            onSelect={() => {
              setCount(5)
            }}
          />{' '}
          Five Pentagram
          <input
            type='radio'
            value='card-container-five-cross'
            name='spreadType'
            onClick={() => {
              setCount(5)
            }}
          />{' '}
          Five Cross
          <input
            type='radio'
            value='card-container-six-david'
            name='spreadType'
            onSelect={() => {
              setCount(6)
            }}
          />{' '}
          Six David
          <input
            type='radio'
            value='card-container-seven-david'
            name='spreadType'
            onSelect={() => {
              setCount(7)
            }}
          />{' '}
          Seven David
        </div>
        <CardContainer
          activeSpreadType={activeSpreadType}
          setCardFlipped={() => {}}
          cards={cards}
        />
        <StoryContainer />
      </div>
    </PageWrapper>
  )
}

export default App

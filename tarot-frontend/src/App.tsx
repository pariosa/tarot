import * as React from 'react'
import { useState } from 'react'
import './App.css'
import { CardType } from './Components/Card'
import { CardContainer } from './Components/CardContainer'

function App() {
  const [count, setCount] = useState(9)
  const [cards, setCards] = useState<CardType[]>([])
  const setCardsFlipped = (flipped: boolean) => {
    console.log('setCardFlipped', flipped)
  }
  const revealThemAll = () => {
    setCardsFlipped(false)
  }
  function fetchTarotSpread() {
    //flip all the cards back when shuffling the deck and drawing a new spread
    setCardsFlipped(true)
    const myHeaders = new Headers()
    myHeaders.append('Content-Type', 'application/json')
    const requestOptions = {
      method: 'GET',
      headers: myHeaders,
    }
    fetch(`/api/spread/weighted/${count}`, requestOptions)
      .then((response) => response.body as ReadableStream<Uint8Array>)
      .then((data) => {
        const reader = data.getReader()
        return reader.read().then(({ value, done }) => {
          const decodedText = new TextDecoder().decode(value)
          const jsonData: CardType[] = JSON.parse(decodedText)
          console.log(jsonData)
          setCards(jsonData)
        })
      })
      .catch((error) => {
        console.error(error)
      })
  }
  return (
    <div className='App w-full' id='app'>
      <p>
        I want you to enter a number of tarot cards to draw. I will then return
        a list of cards. and let you make a story prompt based on those cards.
      </p>
      <div className='flex items-center space-x-2 rounded-md bg-gray-50 p-2'>
        <input
          onBlur={(e: any) => setCount(e.target.value)}
          placeholder='Number of Cards'
          type='number'
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
      <button onClick={() => fetchTarotSpread()}>Draw Cards</button>
      <CardContainer setCardFlipped={() => {}} cards={cards} />
    </div>
  )
}

export default App

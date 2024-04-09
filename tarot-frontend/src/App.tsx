import * as React from 'react'
import { useState } from 'react'
import './App.css'
import { CardType } from './Components/Card'
import { CardContainer } from './Components/CardContainer'

function App() {
  const [count, setCount] = useState(3)
  const [cards, setCards] = useState<CardType[]>([])
  function fetchTarotSpread() {
    var myHeaders = new Headers()
    myHeaders.append('Content-Type', 'application/json')
    var requestOptions = {
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
    <>
      <p>
        I want you to enter a number of tarot cards to draw. I will then return
        a list of cards. and let you make a story prompt based on those cards.
      </p>
      <input
        type='number'
        onBlur={(e: any) => setCount(e.target.value)}
        defaultValue={3}
      />
      <button onClick={() => fetchTarotSpread()}>Draw Cards</button>
      <CardContainer cards={cards} />
    </>
  )
}

export default App

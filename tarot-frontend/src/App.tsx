import * as React from 'react'
import { useState } from 'react'
import './App.css'

function App() {
  const [count, setCount] = useState(3)
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
          const jsonData = JSON.parse(decodedText)
          console.log(jsonData)
          resolve(jsonData)
        })
      })
      .catch((error) => {
        reject(error)
      })
  }
  // const main = async()=>{
  //   console.log(await fetchTarotSpread());
  //   console.log("foo bar")
  //   }
  return (
    <>
      {/* </div>
      <h1>Vite + React</h1>
      <div className='card'>
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.tsx</code> and save to test HMR
        </p>
      </div>
      <p className='read-the-docs'>
        Click on the Vite and React logos to learn more
      </p> */}
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
    </>
  )
}

export default App
function resolve(data: any) {
  console.log(data)
}

function reject(error: any) {
  console.log(error)
}

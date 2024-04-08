import * as React from 'react'
import { useState } from 'react'
import './App.css'

function App() {
  const [count, setCount] = useState(0)
  function fetchTarotSpread() {
    var myHeaders = new Headers()
    myHeaders.append('Content-Type', 'application/json')
    var requestOptions = {
      method: 'GET',
      headers: myHeaders,
    }

    fetch(`//127.0.0.1:8080/api/spread/weighted/${count}`, requestOptions)
      .then((response) => {
        console.log(response.body)
        response.json()
      })
      .then((data) => {
        debugger
        resolve(data)
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

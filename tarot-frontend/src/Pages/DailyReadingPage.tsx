import * as React from 'react'
import { useEffect, useState } from 'react'
import { Card } from '../Components/Card'
import apiService from '../services/api'

// Define the CardDTO interface to match your Java DTO
interface CardDTO {
  name: string
  description: string
  reversed: boolean
  story: string
  card_value: string
  reversedDescription: string
}

// Define the response type for your API endpoint
type ApiResponse = CardDTO[]

const DailyReadingPage = () => {
  const [card, setCard] = useState<CardDTO | null>(null)
  const [loading, setLoading] = useState<boolean>(false)
  const [error, setError] = useState<string | null>(null)
  const [cardFlipped, setCardFlipped] = useState<boolean>(false)

  const fetchDailyCard = async (): Promise<void> => {
    setLoading(true)
    setError(null)
    try {
      const response = await apiService.cards.getDailyCard()
      if (!response.status) {
        throw new Error(`HTTP error! status: ${response}`)
      }
      const data: ApiResponse = await response.data
      if (!data || data.length === 0) {
        throw new Error('No card data received')
      }
      setCard(data[0])
    } catch (error) {
      console.error('Error fetching daily card:', error)
      setError(
        error instanceof Error ? error.message : 'Unknown error occurred'
      )
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchDailyCard()
  }, [])

  return (
    <div className='min-h-screen bg-gradient-to-br from-purple-50 via-indigo-50 to-pink-50'>
      <div className='max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-16'>
        <div className='text-center mb-12'>
          <h1 className='text-4xl font-bold text-gray-900 mb-4'>
            🌅 Daily Reading
          </h1>
          <p className='text-lg text-gray-600'>
            Your mystical guidance for today
          </p>
        </div>

        {loading ? (
          <div className='text-center py-12'>
            <div className='w-12 h-12 border-4 border-purple-200 border-t-purple-600 rounded-full animate-spin mx-auto mb-4'></div>
            <p className='text-gray-600'>Drawing your daily card...</p>
          </div>
        ) : error ? (
          <div className='text-center py-12'>
            <p className='text-gray-600'>{error}</p>
            <button
              onClick={fetchDailyCard}
              className='mt-4 bg-purple-600 text-white px-6 py-2 rounded-lg hover:bg-purple-700 transition-colors'
            >
              Retry
            </button>
          </div>
        ) : card ? (
          <div className='bg-gradient-to-r from-purple-600 to-indigo-600 rounded-2xl shadow-xl overflow-hidden max-w-2xl mx-auto'>
            <div className='bg-gradient-to-r from-purple-600 to-indigo-600 p-8 text-white text-center'>
              <h2 className='text-3xl font-bold mb-2'>
                {!cardFlipped && (
                  <>
                    {card.name} {card.reversed ? '(Reversed)' : ''}
                  </>
                )}
              </h2>
              <p className='text-purple-100'>
                {cardFlipped ? (
                  <div></div>
                ) : (
                  'Click the card to reveal the image'
                )}
              </p>
            </div>
            <div className='pt-6 pb-24 pl-28 background-black'>
              <Card
                name={card.name}
                num={0}
                setCardFlipped={setCardFlipped}
                description={card.description}
                reversed={card.reversed}
                reversedDescription={card.reversedDescription}
                card_value={card.card_value}
                story={card.story}
              />
            </div>

            <div className='p-8 bg-white  '>
              <div className='mb-6'>
                <h3 className='text-xl font-semibold mb-3 text-gray-800'>
                  Meaning
                </h3>
                <p className='text-gray-600 leading-relaxed'>
                  {card.reversed ? card.reversedDescription : card.description}
                </p>
              </div>
              {card.story && (
                <div className='mb-6'>
                  <h3 className='text-xl font-semibold mb-3 text-gray-800'>
                    Story
                  </h3>
                  <p className='text-gray-600 leading-relaxed'>{card.story}</p>
                </div>
              )}
              <button
                onClick={fetchDailyCard}
                className='w-full bg-gradient-to-r from-purple-600 to-indigo-600 text-white py-3 rounded-lg font-medium hover:from-purple-700 hover:to-indigo-700 transition-colors'
              >
                Draw New Card
              </button>
            </div>
          </div>
        ) : (
          <div className='text-center py-12'>
            <p className='text-gray-600'>
              Unable to load daily card. Please try again.
            </p>
            <button
              onClick={fetchDailyCard}
              className='mt-4 bg-purple-600 text-white px-6 py-2 rounded-lg hover:bg-purple-700 transition-colors'
            >
              Retry
            </button>
          </div>
        )}
      </div>
    </div>
  )
}

export default DailyReadingPage

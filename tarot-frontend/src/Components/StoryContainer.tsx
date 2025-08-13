import { AxiosError } from 'axios'
import * as React from 'react'
import { useState } from 'react'
import apiService from '../services/api'
import { TarotStoryElementsDTO } from '../types'
import StoryPrompt from './StoryPrompt'

const StoryContainer: React.FC = () => {
  const [storyElements, setStoryElements] =
    useState<TarotStoryElementsDTO | null>(null)
  const [loading, setLoading] = useState<boolean>(false)
  const [error, setError] = useState<string | null>(null)

  const handleGenerateStory = async () => {
    setLoading(true)
    setError(null)

    try {
      const raw = localStorage.getItem('cardsDrawn')
      const cardsDrawn: string[] = raw ? JSON.parse(raw) : []

      console.log('Cards from localStorage:', cardsDrawn) // Debug log
      if (!Array.isArray(cardsDrawn) || cardsDrawn.length === 0) {
        setError('No cards drawn. Please draw cards first.')
        setLoading(false)
        return
      }

      // Create the request payload that matches your Spring Boot endpoint
      const requestPayload = {
        cardNames: cardsDrawn.join(', '), // Join array into comma-separated string
      }

      console.log('Sending request payload:', requestPayload) // Debug log

      // Call your API service - this should match your endpoint structure
      const response = await apiService.story.getStoryDTO(requestPayload)
      console.log('API response:', response) // Debug log

      // Assuming your API service returns response.data like your other endpoints
      const data: TarotStoryElementsDTO = response.data || response
      setStoryElements(data)
    } catch (err) {
      const error = err as AxiosError
      console.error('Story generation error:', err) // Debug log
      let message = 'Failed to generate story'

      if (error.response) {
        if (error.message) {
          message = error.message
        }
      }
      setError(message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className='story-section'>
      <button
        className='story-button'
        onClick={handleGenerateStory}
        disabled={loading}
        style={{
          padding: '12px 24px',
          backgroundColor: loading ? '#ccc' : '#6366f1',
          color: 'white',
          border: 'none',
          borderRadius: '8px',
          cursor: loading ? 'not-allowed' : 'pointer',
          fontSize: '16px',
          fontWeight: '500',
          transition: 'background-color 0.2s',
        }}
      >
        {loading ? 'Generating Story...' : 'Turn Reading into Story'}
      </button>

      {error && (
        <div
          style={{
            color: '#ef4444',
            backgroundColor: '#fef2f2',
            padding: '12px',
            borderRadius: '8px',
            margin: '16px 0',
            border: '1px solid #fecaca',
          }}
        >
          {error}
        </div>
      )}

      {storyElements && <StoryPrompt storyElements={storyElements} />}
    </div>
  )
}

export default StoryContainer

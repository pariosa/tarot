import * as React from 'react'
import { useState } from 'react'
import { useAuth } from '../hooks/useAuth'
import { TarotStoryElementsDTO } from '../types'
import StoryPrompt from './StoryPrompt'

const StoryContainer: React.FC = () => {
  const [storyElements, setStoryElements] =
    useState<TarotStoryElementsDTO | null>(null)
  const [loading, setLoading] = useState<boolean>(false)
  const [error, setError] = useState<string | null>(null)
  const { authFetch } = useAuth() // Get the authFetch function from your auth context

  const handleGenerateStory = async () => {
    setLoading(true)
    setError(null)

    try {
      const raw = localStorage.getItem('cardsDrawn')
      const cardsDrawn: string[] = raw ? JSON.parse(raw) : []

      if (!Array.isArray(cardsDrawn) || cardsDrawn.length === 0) {
        setError('No cards drawn.')
        setLoading(false)
        return
      }

      // Using authFetch instead of regular fetch
      const response = await authFetch('/getStoryDTO', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          // Authorization header will be automatically added by authFetch
        },
        body: JSON.stringify({ cardNames: cardsDrawn.join(', ') }),
      })

      if (!response.ok) {
        throw new Error(`API error: ${response.status} ${response.statusText}`)
      }

      const data: TarotStoryElementsDTO = await response.json()
      setStoryElements(data)
    } catch (err) {
      const message = err instanceof Error ? err.message : 'Unknown error'
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
      >
        {loading ? 'Generating Story...' : 'Turn Reading into Story'}
      </button>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      {storyElements && <StoryPrompt storyElements={storyElements} />}
    </div>
  )
}

export default StoryContainer

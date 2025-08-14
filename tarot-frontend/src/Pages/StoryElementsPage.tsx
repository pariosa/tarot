import * as React from 'react'
import { useState } from 'react'
import api from '../services/api' // Adjust import path as needed

type StoryElement = {
  type: string
  source: string
  element: string
  loading: boolean
}

export function StoryElementsPage() {
  const [elements, setElements] = useState<StoryElement[]>([])
  const [isAnimating, setIsAnimating] = useState(false)

  const fetchElement = async (type: string, endpoint: string) => {
    // Add new element with loading state
    setElements((prev) => [...prev, { type, value: '', loading: true }])

    setIsAnimating(true)

    try {
      let response
      if (endpoint == '/api/story/character-trait') {
        response = await api.story.getCharacterTrait()
      } else if (endpoint == '/api/story/location') {
        response = await api.story.getLocation()
      } else if (endpoint == '/api/story/theme') {
        response = await api.story.getTheme()
      } else if (endpoint == '/api/story/keyword') {
        response = await api.story.getKeyword()
      } else if (endpoint == '/api/story/climax-event') {
        response = await api.story.getClimaxEvent()
      } else if (endpoint == '/api/story/point-of-view') {
        response = await api.story.getPointOfView()
      } else {
        response = await api.story.getRandomKeyword()
      }

      const newValue = response.data

      // Update the element with the fetched value
      setElements((prev) => {
        const updated = [...prev]
        const lastIndex = updated.length - 1
        updated[lastIndex] = {
          ...updated[lastIndex],
          source: newValue,
          loading: false,
        }
        return updated
      })
    } catch (error) {
      console.error(`Error fetching ${type}:`, error)
      setElements((prev) => {
        const updated = [...prev]
        const lastIndex = updated.length - 1
        updated[lastIndex] = {
          ...updated[lastIndex],
          source: 'Failed to load. Try again!',
          element: '',
          loading: false,
        }
        return updated
      })
    } finally {
      setIsAnimating(false)
    }
  }

  const buttonConfigs = [
    {
      type: 'Location',
      endpoint: '/api/story/location',
      emoji: '🌌',
      color: 'bg-blue-200 hover:bg-blue-300',
    },
    {
      type: 'Character Trait',
      endpoint: '/api/story/character-trait',
      emoji: '🧙',
      color: 'bg-purple-200 hover:bg-purple-300',
    },
    {
      type: 'Theme',
      endpoint: '/api/story/theme',
      emoji: '✨',
      color: 'bg-indigo-200 hover:bg-indigo-300',
    },
    {
      type: 'Keyword',
      endpoint: '/api/story/keyword',
      emoji: '🔑',
      color: 'bg-teal-200 hover:bg-teal-300',
    },
    {
      type: 'Moral Value',
      endpoint: '/api/story/moral-value',
      emoji: '⚖️',
      color: 'bg-green-200 hover:bg-green-300',
    },
    {
      type: 'Point of View',
      endpoint: '/api/story/point-of-view',
      emoji: '👁️',
      color: 'bg-amber-200 hover:bg-amber-300',
    },
    {
      type: 'Style',
      endpoint: '/api/story/style',
      emoji: '🎨',
      color: 'bg-pink-200 hover:bg-pink-300',
    },
    {
      type: 'Climax Event',
      endpoint: '/api/story/climax-event',
      emoji: '⚡',
      color: 'bg-red-200 hover:bg-red-300',
    },
    {
      type: 'Random Keyword',
      endpoint: '/api/story/random-keyword',
      emoji: '🎲',
      color: 'bg-yellow-200 hover:bg-yellow-300',
    },
  ]

  return (
    <div className='min-h-screen bg-blue-50 p-6'>
      <h1 className='text-3xl font-bold text-center mb-8 text-blue-800 flex justify-center items-center'>
        <span className='mr-3'>📖</span> Story Elements Generator
      </h1>

      <div className='max-w-4xl mx-auto'>
        {/* Buttons Grid */}
        <div className='grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 mb-8'>
          {buttonConfigs.map((config) => (
            <button
              key={config.type}
              onClick={() => fetchElement(config.type, config.endpoint)}
              disabled={isAnimating}
              className={`${config.color} p-4 rounded-lg shadow-md transition-colors duration-200 flex flex-col items-center justify-center h-24`}
            >
              <span className='text-2xl mb-2'>{config.emoji}</span>
              <span className='text-sm font-medium'>{config.type}</span>
            </button>
          ))}
        </div>

        {/* Results Section */}
        <div className='bg-white rounded-xl shadow-md p-6'>
          <h2 className='text-xl font-semibold mb-4 text-blue-700 flex items-center'>
            <span className='mr-2'>🧚</span> Your Story Elements
          </h2>

          {elements.length === 0 ? (
            <div className='text-center py-8 text-gray-500'>
              <p className='text-lg'>
                Click buttons above to generate story elements!
              </p>
              <p className='mt-2 text-sm'>
                ✨ Each click will add a new random element
              </p>
            </div>
          ) : (
            <div className='space-y-4'>
              {elements.map((element, index) => (
                <div
                  key={index}
                  className={`p-4 rounded-lg border ${
                    element.loading
                      ? 'border-blue-200 bg-blue-50'
                      : 'border-gray-200'
                  }`}
                >
                  <div className='flex justify-between items-start'>
                    <div>
                      <h3 className='font-medium text-blue-800'>
                        {element.type}
                      </h3>
                      {element.loading ? (
                        <div className='flex items-center mt-1'>
                          <div className='animate-pulse flex space-x-2'>
                            <div className='h-4 w-4 bg-blue-300 rounded-full'></div>
                            <div className='h-4 w-4 bg-blue-300 rounded-full'></div>
                            <div className='h-4 w-4 bg-blue-300 rounded-full'></div>
                          </div>
                        </div>
                      ) : (
                        <p className='mt-1 text-gray-700'>{element.element}</p>
                      )}
                    </div>
                    {!element.loading && (
                      <button
                        onClick={() => {
                          setElements((prev) =>
                            prev.filter((_, i) => i !== index)
                          )
                        }}
                        className='text-gray-400 hover:text-red-500 transition-colors'
                        title='Remove'
                      >
                        ✕
                      </button>
                    )}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Clear All Button */}
        {elements.length > 0 && (
          <div className='mt-6 flex justify-center'>
            <button
              onClick={() => setElements([])}
              className='px-4 py-2 bg-gray-200 hover:bg-gray-300 rounded-lg text-gray-700 transition-colors flex items-center'
            >
              <span className='mr-2'>🧹</span> Clear All Elements
            </button>
          </div>
        )}
      </div>
    </div>
  )
}

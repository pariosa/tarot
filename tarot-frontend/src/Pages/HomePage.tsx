import * as React from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth'
import apiService from '../services/api'

// Using standard anchor tags for navigation - replace with your routing solution
const HomePage = () => {
  const { isAuthenticated, user } = useAuth()

  return (
    <div className='min-h-screen bg-gradient-to-br from-purple-900 via-blue-900 to-indigo-900'>
      {/* Header */}
      <header className='relative z-10 bg-black bg-opacity-20 backdrop-blur-sm'>
        <div className='max-w-7xl mx-auto px-4 sm:px-6 lg:px-8'>
          <div className='flex justify-between items-center py-6'>
            <div className='flex items-center'>
              <h1 className='text-2xl font-bold text-white'>✨ Tarot</h1>
            </div>

            <nav className='flex items-center space-x-4'>
              {isAuthenticated ? (
                <>
                  <span className='text-white'>Welcome, {user?.name}</span>
                  <Link
                    to='/daily-reading'
                    className='bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded-md font-medium transition-colors'
                  >
                    Daily Card
                  </Link>
                  <Link
                    to='/full-reading'
                    className='bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded-md font-medium transition-colors'
                  >
                    Do a Reading
                  </Link>
                  <Link
                    to='/story'
                    className='bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded-md font-medium transition-colors'
                  >
                    Story About Reading
                  </Link>

                  <div
                    onClick={() => {
                      apiService.auth.logout()
                    }}
                    className='bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded-md font-medium transition-colors cursor-pointer'
                  >
                    logout
                  </div>
                </>
              ) : (
                <>
                  <Link
                    to='/login'
                    className='text-white hover:text-purple-200 px-3 py-2 rounded-md font-medium transition-colors'
                  >
                    Sign In
                  </Link>
                  <Link
                    to='/register'
                    className='bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded-md font-medium transition-colors'
                  >
                    Get Started
                  </Link>
                </>
              )}
            </nav>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <main className='relative z-10'>
        <div className='max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pt-20 pb-16'>
          <div className='text-center'>
            <h2 className='text-5xl md:text-6xl font-bold text-white mb-6'>
              Discover Your Path
            </h2>
            <p className='text-xl text-purple-200 mb-8 max-w-2xl mx-auto'>
              <ul className='space-x-2 space-y-6'>
                <li>
                  Unlock the wisdom of the cards with a tarot spread, or daily
                  card reading.
                </li>

                <li>
                  Get a randomized story, seeded by your tarot reading to help
                  you draw insights and guidance for your journey ahead.
                </li>

                <li>
                  Stuck writing a story? get key concepts, keywords, plot
                  devices, and character attributes from your tarot reading, to
                  enhance and enrich your writing processs.
                </li>
                <li>
                  building generative story elements into your game or creative
                  project? try applying to access to our API!{' '}
                  <Link to={'https://github.com/pariosa/tarot'}>
                    Check out the documentation
                  </Link>
                </li>
              </ul>
            </p>

            {!isAuthenticated && (
              <div className='flex flex-col sm:flex-row gap-4 justify-center items-center'>
                <Link
                  to='/register'
                  className='bg-purple-600 hover:bg-purple-700 text-white px-8 py-3 rounded-lg font-semibold text-lg transition-colors transform hover:scale-105'
                >
                  Start Your Reading
                </Link>
                <Link
                  to='/login'
                  className='bg-transparent border-2 border-purple-400 text-purple-200 hover:bg-purple-400 hover:text-white px-8 py-3 rounded-lg font-semibold text-lg transition-all transform hover:scale-105'
                >
                  Sign In
                </Link>
              </div>
            )}
          </div>
        </div>

        {/* Features Section */}
        <div className='max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16'>
          <h3 className='text-3xl font-bold text-white text-center mb-12'>
            Features
          </h3>

          <div className='grid md:grid-cols-3 gap-8'>
            <div className='bg-white bg-opacity-10 backdrop-blur-sm rounded-xl p-6 text-center'>
              <div className='text-4xl mb-4'>🔮</div>
              <h4 className='text-xl font-semibold text-white mb-3'>
                Personalized Readings
              </h4>
              <p className='text-purple-200'>
                Get customized tarot spreads tailored to your specific questions
                and life situations.
              </p>
            </div>

            <div className='bg-white bg-opacity-10 backdrop-blur-sm rounded-xl p-6 text-center'>
              <div className='text-4xl mb-4'>📚</div>
              <h4 className='text-xl font-semibold text-white mb-3'>
                Story Integration
              </h4>
              <p className='text-purple-200'>
                Transform your readings into meaningful narratives that provide
                deeper insights.
              </p>
            </div>

            <div className='bg-white bg-opacity-10 backdrop-blur-sm rounded-xl p-6 text-center'>
              <div className='text-4xl mb-4'>⭐</div>
              <h4 className='text-xl font-semibold text-white mb-3'>
                Daily Guidance
              </h4>
              <p className='text-purple-200'>
                Receive daily card draws and insights to guide your spiritual
                journey.
              </p>
            </div>
          </div>
        </div>

        {/* News/Updates Section */}
        <div className='max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-16'>
          <h3 className='text-3xl font-bold text-white text-center mb-12'>
            Latest Updates
          </h3>

          <div className='space-y-6'>
            <div className='bg-white bg-opacity-10 backdrop-blur-sm rounded-xl p-6'>
              <div className='flex items-start space-x-4'>
                <div className='text-2xl'>🆕</div>
                <div>
                  <h4 className='text-lg font-semibold text-white mb-2'>
                    New Story Integration Feature
                  </h4>
                  <p className='text-purple-200 mb-2'>
                    Transform your tarot readings into personalized stories with
                    our generative story prompt narrative feature.
                  </p>
                  <span className='text-sm text-purple-300'>January 2025</span>
                </div>
              </div>
            </div>

            <div className='bg-white bg-opacity-10 backdrop-blur-sm rounded-xl p-6'>
              <div className='flex items-start space-x-4'>
                <div className='text-2xl'>🌟</div>
                <div>
                  <h4 className='text-lg font-semibold text-white mb-2'>
                    Enhanced Card Spreads
                  </h4>
                  <p className='text-purple-200 mb-2'>
                    Discover new spread patterns and deeper interpretation
                    methods for more accurate readings.
                  </p>
                  <span className='text-sm text-purple-300'>December 2024</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className='relative z-10 bg-black bg-opacity-30 backdrop-blur-sm'>
        <div className='max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8'>
          <div className='text-center text-purple-200'>
            <p>&copy; 2025 Mystic Tarot. All rights reserved.</p>
            <p className='mt-2 text-sm'>
              Discover your destiny through the ancient wisdom of tarot.
            </p>
          </div>
        </div>
      </footer>
    </div>
  )
}

export default HomePage

// import * as React from 'react'
// import { useState } from 'react'
// import { Link } from 'react-router-dom'

// const StoryElementsPage = () => {
//   const storyElements = [
//     {
//       title: 'Location',
//       endpoint: 'location',
//       icon: '🏰',
//       description: 'Mystical places',
//     },
//     {
//       title: 'Character Trait',
//       endpoint: 'character-trait',
//       icon: '🎭',
//       description: 'Character qualities',
//     },
//     {
//       title: 'Theme',
//       endpoint: 'theme',
//       icon: '📖',
//       description: 'Story themes',
//     },
//     {
//       title: 'Keyword',
//       endpoint: 'keyword',
//       icon: '🔑',
//       description: 'Story keywords',
//     },
//   ]

//   const StoryElementCard = ({ title, endpoint, icon, description }) => {
//     const [element, setElement] = useState(null)
//     const [loading, setLoading] = useState(false)

//     const fetchElement = async () => {
//       setLoading(true)
//       try {
//         const response = await fetch(`/api/story/${endpoint}`)
//         if (!response.ok) throw new Error('Failed to fetch')
//         const data = await response.json()
//         setElement(data)
//       } catch (error) {
//         console.error('Error:', error)
//       } finally {
//         setLoading(false)
//       }
//     }

//     return (
//       <div className='bg-white rounded-xl shadow-lg hover:shadow-xl transition-all'>
//         <div className='p-6'>
//           <div className='text-center mb-4'>
//             <span className='text-4xl'>{icon}</span>
//             <h3 className='text-xl font-bold mt-2'>{title}</h3>
//             <p className='text-gray-600'>{description}</p>
//           </div>
//           <button
//             onClick={fetchElement}
//             disabled={loading}
//             className='w-full bg-purple-600 hover:bg-purple-700 text-white py-2 px-4 rounded-lg transition-colors disabled:opacity-50'
//           >
//             {loading ? 'Loading...' : `Get ${title}`}
//           </button>
//           {element && (
//             <div className='mt-4 p-4 bg-gray-50 rounded-lg'>
//               <p className='font-medium'>"{element.element}"</p>
//               <p className='text-sm text-gray-600 mt-1'>
//                 Source: {element.source}
//               </p>
//             </div>
//           )}
//         </div>
//       </div>
//     )
//   }

//   return (
//     <div className='min-h-screen bg-gradient-to-br from-purple-50 via-indigo-50 to-pink-50'>
//       <div className='max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-16'>
//         <div className='text-center mb-12'>
//           <h1 className='text-4xl font-bold text-gray-900 mb-4'>
//             📚 Free Story Elements
//           </h1>
//           <p className='text-lg text-gray-600'>
//             Get individual story elements for free!
//           </p>
//           <div className='inline-block bg-green-100 text-green-800 px-4 py-2 rounded-full mt-4'>
//             🎁 No login required
//           </div>
//         </div>

//         <div className='grid md:grid-cols-2 lg:grid-cols-4 gap-6'>
//           {storyElements.map((element, index) => (
//             <StoryElementCard key={index} {...element} />
//           ))}
//         </div>

//         <div className='mt-16 bg-gradient-to-r from-purple-600 to-pink-600 rounded-2xl p-8 text-white text-center'>
//           <h3 className='text-2xl font-bold mb-4'>Want Complete Stories?</h3>
//           <p className='mb-6'>
//             Get fully coordinated story elements with character development and
//             plot structure!
//           </p>
//           <Link
//             to='/login'
//             className='inline-block bg-white text-purple-600 px-8 py-3 rounded-lg font-bold hover:bg-gray-100 transition-colors'
//           >
//             Sign Up for Full Stories
//           </Link>
//         </div>
//       </div>
//     </div>
//   )
// }
// export default StoryElementsPage

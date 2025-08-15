import * as React from 'react'
import { Tooltip } from 'react-tooltip'
import 'react-tooltip/dist/react-tooltip.css'
import { TarotStoryElementsDTO } from '../types/TarotStoryElementsDTO.types'

// Card colors mapping (same as your existing component)
const cardColors: Record<string, string> = {
  // 🟦 Cups
  'Ace Of Cups': '#1e90ff',
  'Two Of Cups': '#5dade2',
  'Three Of Cups': '#85c1e9',
  'Four Of Cups': '#3498db',
  'Five Of Cups': '#2e86c1',
  'Six Of Cups': '#2874a6',
  'Seven Of Cups': '#21618c',
  'Eight Of Cups': '#1b4f72',
  'Nine Of Cups': '#154360',
  'Ten Of Cups': '#0e3c60',
  'Page Of Cups': '#6495ed',
  'Knight Of Cups': '#7b68ee',
  'Queen Of Cups': '#9370db',
  'King Of Cups': '#7b68ee',

  // 🟥 Swords
  'Ace Of Swords': '#e74c3c',
  'Two Of Swords': '#cb4335',
  'Three Of Swords': '#c0392b',
  'Four Of Swords': '#a93226',
  'Five Of Swords': '#922b21',
  'Six Of Swords': '#7b241c',
  'Seven Of Swords': '#641e16',
  'Eight Of Swords': '#78281f',
  'Nine Of Swords': '#943126',
  'Ten Of Swords': '#b22222',
  'Page Of Swords': '#e74c3c',
  'Knight Of Swords': '#cb4335',
  'Queen Of Swords': '#c0392b',
  'King Of Swords': '#a93226',

  // 🟩 Pentacles
  'Ace Of Pentacles': '#27ae60',
  'Two Of Pentacles': '#229954',
  'Three Of Pentacles': '#1e8449',
  'Four Of Pentacles': '#196f3d',
  'Five Of Pentacles': '#145a32',
  'Six Of Pentacles': '#0e6655',
  'Seven Of Pentacles': '#117864',
  'Eight Of Pentacles': '#148f77',
  'Nine Of Pentacles': '#17a589',
  'Ten Of Pentacles': '#1abc9c',
  'Page Of Pentacles': '#48c9b0',
  'Knight Of Pentacles': '#2ecc71',
  'Queen Of Pentacles': '#27ae60',
  'King Of Pentacles': '#229954',

  // 🟫 Wands
  'Ace Of Wands': '#d35400',
  'Two Of Wands': '#ba4a00',
  'Three Of Wands': '#a04000',
  'Four Of Wands': '#873600',
  'Five Of Wands': '#6e2c00',
  'Six Of Wands': '#935116',
  'Seven Of Wands': '#a35f1e',
  'Eight Of Wands': '#b66d26',
  'Nine Of Wands': '#cd853f',
  'Ten Of Wands': '#deb887',
  'Page Of Wands': '#f4a460',
  'Knight Of Wands': '#f1c40f',
  'Queen Of Wands': '#d35400',
  'King Of Wands': '#ba4a00',

  // 🌟 Major Arcana
  'The Fool': '#f1c40f',
  'The Magician': '#8e44ad',
  'The High Priestess': '#2980b9',
  'The Empress': '#e67e22',
  'The Emperor': '#c0392b',
  'The Hierophant': '#2c3e50',
  'The Lovers': '#ec407a',
  'The Chariot': '#2e86c1',
  Strength: '#d35400',
  'The Hermit': '#7f8c8d',
  'Wheel of Fortune': '#27ae60',
  Justice: '#d4ac0d',
  'The Hanged Man': '#16a085',
  Death: '#000000',
  Temperance: '#f39c12',
  'The Devil': '#8b0000',
  'The Tower': '#e74c3c',
  'The Star': '#85c1e9',
  'The Moon': '#34495e',
  'The Sun': '#f39c12',
  Judgement: '#f4d03f',
  'The World': '#1abc9c',
}

function getContrastColor(hexColor?: string): string {
  if (!hexColor) return '#000'
  const r = parseInt(hexColor.slice(1, 3), 16)
  const g = parseInt(hexColor.slice(3, 5), 16)
  const b = parseInt(hexColor.slice(5, 7), 16)
  const yiq = (r * 299 + g * 587 + b * 114) / 1000
  return yiq >= 128 ? '#000' : '#fff'
}

function normalizeCardName(cardName: string): string {
  return cardName
    .toLowerCase()
    .replace(/\b(\w)/g, (char) => char.toUpperCase())
    .replace(/\s+of\s+/i, ' Of ')
    .trim()
}

interface AIStoryGeneratorProps {
  storyElements: TarotStoryElementsDTO
}

interface EnhancedStory {
  title: string
  premise: string
  plotOutline: string
  characterArcs: string
  thematicElements: string
  suggestedGenre: string
}

const AIStoryGenerator: React.FC<AIStoryGeneratorProps> = ({
  storyElements,
}) => {
  const [enhancedStory, setEnhancedStory] =
    React.useState<EnhancedStory | null>(null)
  const [loading, setLoading] = React.useState(false)
  const [error, setError] = React.useState<string | null>(null)

  // OpenAI API call
  const generateEnhancedStory = async () => {
    setLoading(true)
    setError(null)

    try {
      // Construct prompt from story elements
      const prompt = `
Create an enhanced story prompt and plot outline based on these tarot-inspired story elements:

**Story Elements:**
- Main Character: ${
        storyElements.mainCharacterDescriptors?.storyElement || 'Unknown'
      } (Deficit: ${
        storyElements.mainCharacterDeficit?.storyElement || 'Unknown'
      })
- Main Character Goal: ${
        storyElements.mainCharacterGoal?.storyElement || 'Unknown'
      }
- Call to Action: ${storyElements.callToAction?.storyElement || 'Unknown'}
- Ally: ${storyElements.allyTrait?.storyElement || 'Unknown'} (Goal: ${
        storyElements.allyGoal?.storyElement || 'Unknown'
      }, Deficit: ${storyElements.allyDeficit?.storyElement || 'Unknown'})
- Enemy: ${storyElements.enemyTrait?.storyElement || 'Unknown'} (Goal: ${
        storyElements.enemyGoal?.storyElement || 'Unknown'
      }, Deficit: ${storyElements.enemyDeficit?.storyElement || 'Unknown'})
- Setting: ${storyElements.location?.storyElement || 'Unknown'}
- Point of View: ${storyElements.pointOfView?.storyElement || 'Unknown'}
- Theme: ${storyElements.theme?.storyElement || 'Unknown'}
- Moral Value: ${storyElements.moralValue?.storyElement || 'Unknown'}
- Style: ${storyElements.style?.storyElement || 'Unknown'}
- Climax Event: ${storyElements.climaxEvent?.storyElement || 'Unknown'}
- Climax Location: ${storyElements.climaxLocation?.storyElement || 'Unknown'}
- Climax Description: ${
        storyElements.climaxDescription?.storyElement || 'Unknown'
      }
- Keywords: ${storyElements.keyword?.storyElement || 'Unknown'}

Please create:
1. A compelling title
2. A one-paragraph premise
3. A detailed plot outline with beginning, middle, and end
4. Character development arcs
5. Thematic elements and symbolism
6. Suggested genre and tone

Format as JSON with keys: title, premise, plotOutline, characterArcs, thematicElements, suggestedGenre
`

      const response = await fetch('/api/generate-story', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ prompt }),
      })

      if (!response.ok) {
        throw new Error('Failed to generate enhanced story')
      }

      const data = await response.json()
      setEnhancedStory(data)
    } catch (err) {
      console.error('AI Story generation error:', err)
      setError(
        err instanceof Error ? err.message : 'Failed to generate enhanced story'
      )
    } finally {
      setLoading(false)
    }
  }

  // Helper function to highlight text with card colors
  const highlightTextWithSources = (
    text: string,
    elementMapping: Record<string, { source: string }>
  ) => {
    let highlightedText = text
    const highlights: Array<{ text: string; source: string; id: string }> = []

    // Find relevant story elements mentioned in the text
    Object.entries(elementMapping).forEach(([key, value]) => {
      if (value && value.source) {
        const element = value.storyElement
        if (element && text.toLowerCase().includes(element.toLowerCase())) {
          const normalizedCard = normalizeCardName(value.source)
          const bgColor = cardColors[normalizedCard] || '#ccc'
          const textColor = getContrastColor(bgColor)
          const highlightId = `highlight-${key}-${Math.random()
            .toString(36)
            .substr(2, 9)}`

          highlights.push({
            text: element,
            source: normalizedCard,
            id: highlightId,
          })

          const regex = new RegExp(
            `(${element.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')})`,
            'gi'
          )
          highlightedText = highlightedText.replace(
            regex,
            `<span id="${highlightId}" style="background-color: ${bgColor}; color: ${textColor}; padding: 2px 4px; border-radius: 3px; text-decoration: underline dotted; cursor: help;">$1</span>`
          )
        }
      }
    })

    return { highlightedText, highlights }
  }

  // Create element mapping for easy lookup
  const elementMapping = {
    mainCharacterTrait: storyElements.mainCharacterTrait,
    mainCharacterDeficit: storyElements.mainCharacterDeficit,
    mainCharacterGoal: storyElements.mainCharacterGoal,
    callToAction: storyElements.callToAction,
    allyTrait: storyElements.allyTrait,
    allyGoal: storyElements.allyGoal,
    allyDeficit: storyElements.allyDeficit,
    enemyTrait: storyElements.enemyTrait,
    enemyGoal: storyElements.enemyGoal,
    enemyDeficit: storyElements.enemyDeficit,
    location: storyElements.location,
    pointOfView: storyElements.pointOfView,
    theme: storyElements.theme,
    moralValue: storyElements.moralValue,
    style: storyElements.style,
    climaxEvent: storyElements.climaxEvent,
    climaxLocation: storyElements.climaxLocation,
    climaxDescription: storyElements.climaxDescription,
    keyword: storyElements.keyword,
  }

  return (
    <div
      style={{
        marginTop: '2rem',
        padding: '1rem',
        border: '1px solid #e5e7eb',
        borderRadius: '8px',
      }}
    >
      <h3 style={{ marginBottom: '1rem', color: '#374151' }}>
        AI-Enhanced Story Generator
      </h3>

      <button
        onClick={generateEnhancedStory}
        disabled={loading}
        style={{
          padding: '12px 24px',
          backgroundColor: loading ? '#ccc' : '#8b5cf6',
          color: 'white',
          border: 'none',
          borderRadius: '8px',
          cursor: loading ? 'not-allowed' : 'pointer',
          fontSize: '16px',
          fontWeight: '500',
          transition: 'background-color 0.2s',
          marginBottom: '1rem',
        }}
      >
        {loading ? 'Generating Enhanced Story...' : 'Generate AI Story Outline'}
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

      {enhancedStory && (
        <div style={{ marginTop: '1rem' }}>
          <h2 style={{ color: '#1f2937', marginBottom: '1rem' }}>
            {enhancedStory.title}
          </h2>

          <div style={{ marginBottom: '1.5rem' }}>
            <h4 style={{ color: '#374151', marginBottom: '0.5rem' }}>
              Premise:
            </h4>
            <p
              style={{ lineHeight: '1.6', color: '#4b5563' }}
              dangerouslySetInnerHTML={{
                __html: highlightTextWithSources(
                  enhancedStory.premise,
                  elementMapping
                ).highlightedText,
              }}
            />
          </div>

          <div style={{ marginBottom: '1.5rem' }}>
            <h4 style={{ color: '#374151', marginBottom: '0.5rem' }}>
              Plot Outline:
            </h4>
            <div
              style={{
                lineHeight: '1.6',
                color: '#4b5563',
                whiteSpace: 'pre-line',
              }}
              dangerouslySetInnerHTML={{
                __html: highlightTextWithSources(
                  enhancedStory.plotOutline,
                  elementMapping
                ).highlightedText,
              }}
            />
          </div>

          <div style={{ marginBottom: '1.5rem' }}>
            <h4 style={{ color: '#374151', marginBottom: '0.5rem' }}>
              Character Arcs:
            </h4>
            <div
              style={{
                lineHeight: '1.6',
                color: '#4b5563',
                whiteSpace: 'pre-line',
              }}
              dangerouslySetInnerHTML={{
                __html: highlightTextWithSources(
                  enhancedStory.characterArcs,
                  elementMapping
                ).highlightedText,
              }}
            />
          </div>

          <div style={{ marginBottom: '1.5rem' }}>
            <h4 style={{ color: '#374151', marginBottom: '0.5rem' }}>
              Thematic Elements:
            </h4>
            <p
              style={{ lineHeight: '1.6', color: '#4b5563' }}
              dangerouslySetInnerHTML={{
                __html: highlightTextWithSources(
                  enhancedStory.thematicElements,
                  elementMapping
                ).highlightedText,
              }}
            />
          </div>

          <div style={{ marginBottom: '1.5rem' }}>
            <h4 style={{ color: '#374151', marginBottom: '0.5rem' }}>
              Suggested Genre & Tone:
            </h4>
            <p style={{ lineHeight: '1.6', color: '#4b5563' }}>
              {enhancedStory.suggestedGenre}
            </p>
          </div>

          {/* Add tooltips for all highlights */}
          {Object.entries(elementMapping).map(([key, value]) => {
            if (value && value.source) {
              const normalizedCard = normalizeCardName(value.source)
              return (
                <Tooltip
                  key={key}
                  anchorSelect={`#highlight-${key}`}
                  content={`Inspired by: ${normalizedCard}`}
                />
              )
            }
            return null
          })}
        </div>
      )}
    </div>
  )
}

export default AIStoryGenerator

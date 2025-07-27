import * as React from 'react'
import { Tooltip } from 'react-tooltip'
import 'react-tooltip/dist/react-tooltip.css'
import { TarotStoryElementsDTO } from '../types/TarotStoryElementsDTO.types'

interface StoryPromptProps {
  storyElements: TarotStoryElementsDTO
}

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

const StoryPrompt: React.FC<StoryPromptProps> = ({ storyElements }) => {
  const elem = storyElements
  function normalizeCardName(cardName: string): string {
    return cardName
      .toLowerCase()
      .replace(/\b(\w)/g, (char) => char.toUpperCase()) // Capitalize first letter of each word
      .replace(/\s+of\s+/i, ' Of ') // Ensure "Of" is capitalized with spaces around
      .trim()
  }

  const renderElement = (
    label: string,
    value?: { storyElement: string; source: string }
  ) => {
    if (!value) return null

    const normalizedCard = normalizeCardName(value.source)
    const bgColor = cardColors[normalizedCard] || '#ccc'
    const textColor = getContrastColor(bgColor)

    return (
      <p key={label}>
        <strong>{label}:</strong>{' '}
        <span
          data-tooltip-id={`tooltip-${label}`}
          data-tooltip-content={`From: ${normalizedCard}`}
          style={{
            color: textColor,
            backgroundColor: bgColor,
            padding: '4px 8px',
            borderRadius: '6px',
            marginRight: 8,
            display: 'inline-block',
            fontSize: '0.9em',
            textDecoration: 'underline dotted',
            cursor: 'help',
          }}
        >
          {value.storyElement}
        </span>
        <Tooltip id={`tooltip-${label}`} />
      </p>
    )
  }
  return (
    <div>
      <h2>{elem.cardValue || 'Untitled'}</h2>

      {renderElement('Keywords', elem.keyword)}
      {renderElement('Main Character Trait', elem.mainCharacterDescriptors)}
      {renderElement('Main Character Deficit', elem.mainCharacterDeficit)}
      {renderElement('Main Character Goal', elem.mainCharacterGoal)}
      {renderElement('Call To Action', elem.callToAction)}
      {renderElement('Ally Trait', elem.allyTrait)}
      {renderElement('Ally Goal', elem.allyGoal)}
      {renderElement('Ally Deficit', elem.allyDeficit)}
      {renderElement('Enemy Trait', elem.enemyTrait)}
      {renderElement('Enemy Goal', elem.enemyGoal)}
      {renderElement('Enemy Deficit', elem.enemyDeficit)}
      {renderElement('Location', elem.location)}
      {renderElement('Point Of View', elem.pointOfView)}
      {renderElement('Moral Value', elem.moralValue)}
      {renderElement('Climax Event', elem.climaxEvent)}
      {renderElement('Climax Location', elem.climaxLocation)}
      {renderElement('Climax Description', elem.climaxDescription)}
      {renderElement('Theme', elem.theme)}
      {renderElement('Style', elem.style)}

      {elem.cardsUsed && elem.cardsUsed.length > 0 && (
        <p>
          <strong>Cards Used:</strong>{' '}
          {elem.cardsUsed.map((card, idx) => {
            const bgColor = cardColors[card] || '#ccc'
            const textColor = getContrastColor(bgColor)
            return (
              <span
                key={idx}
                title={`Card Source: ${card}`}
                style={{
                  backgroundColor: bgColor,
                  color: textColor,
                  padding: '4px 8px',
                  borderRadius: '6px',
                  marginRight: 8,
                  display: 'inline-block',
                  fontSize: '0.9em',
                }}
              >
                {card}
              </span>
            )
          })}
        </p>
      )}
    </div>
  )
}

export default StoryPrompt

import * as React from 'react'
import './SpreadSelector.css'

type SpreadOption = {
  value: string
  label: string
  cardCount: number
  icon?: string // Optional icon for visual enhancement
}

type SpreadSelectorProps = {
  activeSpreadType: string
  count: number
  onSpreadTypeChange: (value: string, count: number) => void
  onCountChange: (count: number) => void
  onDrawCards: () => void
}

const spreadOptions: SpreadOption[] = [
  {
    value: 'card-container-one',
    label: 'Single Card',
    cardCount: 1,
    icon: '1',
  },
  {
    value: 'card-container-two-vertical',
    label: 'Two Cards',
    cardCount: 2,
    icon: '↕️',
  },
  {
    value: 'card-container-two-horizontal',
    label: 'Side by Side',
    cardCount: 2,
    icon: '↔️',
  },
  {
    value: 'card-container-three-story',
    label: 'Past/Present/Future',
    cardCount: 3,
    icon: '🕒',
  },
  {
    value: 'card-container-three-triangle',
    label: 'Triangle',
    cardCount: 3,
    icon: '△',
  },
  {
    value: 'card-container-three-inverted-triangle',
    label: 'Inverted Triangle',
    cardCount: 3,
    icon: '▽',
  },
  {
    value: 'card-container-four-cross',
    label: 'Celtic Cross',
    cardCount: 4,
    icon: '✚',
  },
  {
    value: 'card-container-five-cross',
    label: 'Five Card Cross',
    cardCount: 5,
    icon: '✛',
  },
  {
    value: 'card-container-five-pentagram',
    label: 'Pentagram',
    cardCount: 5,
    icon: '⭐',
  },
  {
    value: 'card-container-six-david',
    label: 'Star of David',
    cardCount: 6,
    icon: '✡️',
  },
  {
    value: 'card-container-seven-david',
    label: 'Seven Point',
    cardCount: 7,
    icon: '🔯',
  },
]

export const SpreadSelector: React.FC<SpreadSelectorProps> = ({
  activeSpreadType,
  count,
  onSpreadTypeChange,
  onCountChange,
  onDrawCards,
}) => {
  const handleSpreadSelection = (value: string, cardCount: number) => {
    onSpreadTypeChange(value, cardCount)
    onCountChange(cardCount) // Update the count to match the spread
  }

  return (
    <div className='spread-selector-container'>
      <div className='spread-controls'>
        <div className='count-control'>
          <label htmlFor='cardCount'>Number of Cards:</label>
          <input
            id='cardCount'
            type='number'
            min='1'
            max='10'
            value={count}
            onChange={(e) =>
              onCountChange(Math.max(1, parseInt(e.target.value) || 1))
            }
            className='count-input'
          />
          <button onClick={onDrawCards} className='draw-button'>
            Draw Cards
          </button>
        </div>

        <div className='spread-buttons-container'>
          <h3>Or choose a spread:</h3>
          <div className='spread-buttons-grid'>
            {spreadOptions.map((option) => (
              <button
                key={option.value}
                className={`spread-button ${
                  activeSpreadType === option.value ? 'active' : ''
                }`}
                onClick={() =>
                  handleSpreadSelection(option.value, option.cardCount)
                }
                title={option.label}
              >
                {option.icon && (
                  <span className='spread-icon'>{option.icon}</span>
                )}
                <span className='spread-label'>{option.label}</span>
                <span className='spread-count'>
                  {option.cardCount} card{option.cardCount !== 1 ? 's' : ''}
                </span>
              </button>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}

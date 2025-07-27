import * as React from 'react'
import { TarotStoryElementsDTO } from '../types/TarotStoryElementsDTO.types'
interface StoryPromptProps {
  storyElements: TarotStoryElementsDTO // no longer an array
}

const StoryPrompt: React.FC<StoryPromptProps> = ({ storyElements }) => {
  const elem = storyElements

  return (
    <div>
      <h2>{elem.cardValue || 'Untitled'}</h2>

      {elem.keyword && (
        <p>
          <strong>Keywords:</strong> {elem.keyword.storyElement}
        </p>
      )}
      {elem.mainCharacterDeficit && (
        <p>
          <strong>Main Character Deficit:</strong>{' '}
          {elem.mainCharacterDeficit.storyElement}
        </p>
      )}
      {elem.mainCharacterGoal && (
        <p>
          <strong>Main Character Goal:</strong>{' '}
          {elem.mainCharacterGoal.storyElement}
        </p>
      )}
      {elem.callToAction && (
        <p>
          <strong>Call To Action:</strong> {elem.callToAction.storyElement}
        </p>
      )}
      {elem.allyTrait && (
        <p>
          <strong>Ally Trait:</strong> {elem.allyTrait.storyElement}
        </p>
      )}
      {elem.allyGoal && (
        <p>
          <strong>Ally Goal:</strong> {elem.allyGoal.storyElement}
        </p>
      )}
      {elem.allyDeficit && (
        <p>
          <strong>Ally Deficit:</strong> {elem.allyDeficit.storyElement}
        </p>
      )}
      {elem.enemyTrait && (
        <p>
          <strong>Enemy Trait:</strong> {elem.enemyTrait.storyElement}
        </p>
      )}
      {elem.enemyGoal && (
        <p>
          <strong>Enemy Goal:</strong> {elem.enemyGoal.storyElement}
        </p>
      )}
      {elem.enemyDeficit && (
        <p>
          <strong>Enemy Deficit:</strong> {elem.enemyDeficit.storyElement}
        </p>
      )}
      {elem.location && (
        <p>
          <strong>Location:</strong> {elem.location.storyElement}
        </p>
      )}
      {elem.pointOfView && (
        <p>
          <strong>Point Of View:</strong> {elem.pointOfView.storyElement}
        </p>
      )}
      {elem.moralValue && (
        <p>
          <strong>Moral Value:</strong> {elem.moralValue.storyElement}
        </p>
      )}
      {elem.climaxEvent && (
        <p>
          <strong>Climax Event:</strong> {elem.climaxEvent.storyElement}
        </p>
      )}
      {elem.climaxLocation && (
        <p>
          <strong>Climax Location:</strong> {elem.climaxLocation.storyElement}
        </p>
      )}
      {elem.climaxDescription && (
        <p>
          <strong>Climax Description:</strong>{' '}
          {elem.climaxDescription.storyElement}
        </p>
      )}
      {elem.theme && (
        <p>
          <strong>Theme:</strong> {elem.theme.storyElement}
        </p>
      )}
      {elem.style && (
        <p>
          <strong>Style:</strong> {elem.style.storyElement}
        </p>
      )}
      {elem.cardsUsed && (
        <p>
          <strong>Cards Used:</strong> {elem.cardsUsed.join(', ')}
        </p>
      )}
      {elem.keyword && (
        <p>
          <strong>Keyword:</strong> {elem.keyword.storyElement}
        </p>
      )}
    </div>
  )
}
export default StoryPrompt

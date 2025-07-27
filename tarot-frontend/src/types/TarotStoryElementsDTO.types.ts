export interface TarotStoryElementsDTO {
  cardValue?: string
  mainCharacterDeficit?: StoryField
  mainCharacterGoal?: StoryField
  callToAction?: StoryField
  allyTrait?: StoryField
  allyGoal?: StoryField
  allyDeficit?: StoryField
  enemyTrait?: StoryField
  enemyGoal?: StoryField
  enemyDeficit?: StoryField
  location?: StoryField
  climaxLocation?: StoryField
  climaxDescription?: StoryField
  climaxEvent?: StoryField
  pointOfView?: StoryField
  moralValue?: StoryField
  style?: StoryField
  theme?: StoryField
  keyword?: StoryField
  cardsUsed?: string[]
}

interface StoryField {
  storyElement: string
  source: string
}

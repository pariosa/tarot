package com.mercy.tarot.seeders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercy.tarot.models.Card;
import com.mercy.tarot.repositories.CardRepository;

@Component
public class DatabaseSeeder {

        @Autowired
        private CardRepository cardRepository;

        private List<Card> tarotDeck = new ArrayList<>(); // Define your Tarot card deck here

        // Major Arcana
        Card theFool = new Card("The Fool", "The Fool", "Major Arcana",
                        "The Fool represents new beginnings, innocence, and spontaneity.",
                        "This card encourages you to embrace the unknown and trust in the journey ahead.", true);
        Card theMagician = new Card("The Magician", "The Magician", "Major Arcana",
                        "The Magician represents manifestation, power, and resourcefulness.",
                        "This card reminds you of your ability to create your reality and harness your skills.", true);
        Card theHighPriestess = new Card("The High Priestess", "The High Priestess", "Major Arcana",
                        "The High Priestess represents intuition, mystery, and understanding the subconscious mind.",
                        "This card encourages you to trust your inner wisdom and explore the depths of your subconscious.",
                        true);
        Card theEmpress = new Card("The Empress", "The Empress", "Major Arcana",
                        "The Empress represents nurturing, abundance, and fertility.",
                        "This card symbolizes creativity, fertility, and the power of nurturing and growth.", true);
        Card theEmperor = new Card("The Emperor", "The Emperor", "Major Arcana",
                        "The Emperor represents authority, structure, and stability.",
                        "This card reminds you to take charge of your life and establish a solid foundation for success.",
                        true);
        Card theHierophant = new Card("The Hierophant", "The Hierophant", "Major Arcana",
                        "The Hierophant represents tradition, spirituality, and guidance.",
                        "This card encourages you to seek wisdom from spiritual teachings and traditional values.",
                        true);
        Card theLovers = new Card("The Lovers", "The Lovers", "Major Arcana",
                        "The Lovers represents love, relationships, and choices.",
                        "This card signifies important decisions regarding love, relationships, and personal values.",
                        true);
        Card theChariot = new Card("The Chariot", "The Chariot", "Major Arcana",
                        "The Chariot represents determination, willpower, and control.",
                        "This card reminds you to stay focused on your goals and overcome obstacles through determination.",
                        true);
        Card Strength = new Card("Strength", "Strength", "Major Arcana",
                        "Strength represents courage, inner strength, and resilience.",
                        "This card encourages you to tap into your inner strength and overcome challenges with grace.",
                        true);
        Card theHermit = new Card("The Hermit", "The Hermit", "Major Arcana",
                        "The Hermit represents introspection, soul-searching, and inner guidance.",
                        "This card signifies a period of self-reflection and inner wisdom.", true);
        Card wheelOfFortune = new Card("Wheel of Fortune", "Wheel of Fortune", "Major Arcana",
                        "Wheel of Fortune represents destiny, cycles, and change.",
                        "This card signifies the ups and downs of life and the cycles of change.", true);
        Card Justice = new Card("Justice", "Justice", "Major Arcana",
                        "Justice represents fairness, truth, and balance.",
                        "This card symbolizes fairness, truth, and karmic balance.", true);
        Card theHangedMan = new Card("The Hanged Man", "The Hanged Man", "Major Arcana",
                        "The Hanged Man represents surrender, release, and new perspectives.",
                        "This card encourages you to let go of control and gain new insights through surrender.", true);
        Card Death = new Card("Death", "Death", "Major Arcana",
                        "Death represents endings, transformation, and new beginnings.",
                        "This card symbolizes the end of one chapter and the beginning of another.", true);
        Card Temperance = new Card("Temperance", "Temperance", "Major Arcana",
                        "Temperance represents balance, harmony, and moderation.",
                        "This card encourages you to find balance and moderation in all aspects of life.", true);
        Card theDevil = new Card("The Devil", "The Devil", "Major Arcana",
                        "The Devil represents materialism, bondage, and illusions.",
                        "This card warns against being trapped by material desires and illusions.", true);
        Card theTower = new Card("The Tower", "The Tower", "Major Arcana",
                        "The Tower represents sudden change, upheaval, and revelation.",
                        "This card signifies the breakdown of old structures and the emergence of new insights.", true);
        Card theStar = new Card("The Star", "The Star", "Major Arcana",
                        "The Star represents hope, inspiration, and renewal.",
                        "This card signifies hope, inspiration, and the promise of new beginnings.", true);
        Card theMoon = new Card("The Moon", "The Moon", "Major Arcana",
                        "The Moon represents intuition, illusion, and the subconscious mind.",
                        "This card symbolizes the exploration of the unconscious and hidden aspects of yourself.",
                        true);
        Card theSun = new Card("The Sun", "The Sun", "Major Arcana", "The Sun represents success, joy, and vitality.",
                        "This card signifies success, happiness, and fulfillment in all areas of life.", true);
        Card Judgement = new Card("Judgement", "Judgement", "Major Arcana",
                        "Judgement represents awakening, rebirth, and inner calling.",
                        "This card symbolizes a spiritual awakening and the need to answer your inner calling.", true);
        Card theWorld = new Card("The World", "The World", "Major Arcana",
                        "The World represents completion, fulfillment, and achievement.",
                        "This card signifies the completion of a cycle and the attainment of goals.",
                        true);

        // Suit of Cups cards data
        Card aceOfCups = new Card("Ace of Cups", "Ace of Cups", "Cups",
                        "The Ace of Cups represents new beginnings, intuition, and emotional fulfillment.",
                        "This card often signifies a new phase of emotional growth or a deepening of existing emotional connections.",
                        false);
        Card twoOfCups = new Card("Two of Cups", "Two of Cups", "Cups",
                        "The Two of Cups represents partnership, harmony, and mutual attraction.",
                        "This card signifies a strong emotional bond and cooperation in relationships.", false);
        Card threeOfCups = new Card("Three of Cups", "Three of Cups", "Cups",
                        "The Three of Cups represents celebration, friendship, and joy.",
                        "This card signifies joyful gatherings, shared happiness, and emotional connections.", false);
        Card fourOfCups = new Card("Four of Cups", "Four of Cups", "Cups",
                        "The Four of Cups represents contemplation, apathy, and dissatisfaction.",
                        "This card warns against being stuck in a state of discontentment and encourages introspection.",
                        false);
        Card fiveOfCups = new Card("Five of Cups", "Five of Cups", "Cups",
                        "The Five of Cups represents loss, regret, and disappointment.",
                        "This card encourages you to acknowledge your feelings of loss and find a way to move forward.",
                        false);
        Card sixOfCups = new Card("Six of Cups", "Six of Cups", "Cups",
                        "The Six of Cups represents nostalgia, childhood memories, and innocence.",
                        "This card signifies reconnecting with the past and finding joy in simple pleasures.", false);
        Card sevenOfCups = new Card("Seven of Cups", "Seven of Cups", "Cups",
                        "The Seven of Cups represents choices, illusions, and fantasy.",
                        "This card warns against being overwhelmed by choices and encourages discernment.", false);
        Card eightOfCups = new Card("Eight of Cups", "Eight of Cups", "Cups",
                        "The Eight of Cups represents walking away, transition, and searching for deeper meaning.",
                        "This card signifies leaving behind what no longer serves you and embarking on a spiritual journey.",
                        false);
        Card nineOfCups = new Card("Nine of Cups", "Nine of Cups", "Cups",
                        "The Nine of Cups represents contentment, satisfaction, and emotional fulfillment.",
                        "This card signifies happiness and emotional abundance.", false);
        Card tenOfCups = new Card("Ten of Cups", "Ten of Cups", "Cups",
                        "The Ten of Cups represents harmony, family, and happiness.",
                        "This card signifies emotional fulfillment and blissful relationships within the family.",
                        false);
        Card pageOfCups = new Card("Page of Cups", "Page of Cups", "Cups",
                        "The Page of Cups represents creativity, intuition, and emotional messages.",
                        "This card signifies a new creative project or the emergence of intuitive insights.", false);
        Card knightOfCups = new Card("Knight of Cups", "Knight of Cups", "Cups",
                        "The Knight of Cups represents romance, charm, and emotional pursuit.",
                        "This card signifies a romantic and imaginative individual who follows their heart.", false);
        Card queenOfCups = new Card("Queen of Cups", "Queen of Cups", "Cups",
                        "The Queen of Cups represents compassion, intuition, and emotional stability.",
                        "This card signifies nurturing and empathetic qualities.", false);
        Card kingOfCups = new Card("King of Cups", "King of Cups", "Cups",
                        "The King of Cups represents emotional maturity, wisdom, and calmness.",
                        "This card signifies someone who is emotionally balanced and empathetic.", false);

        // Suit of Wands cards data
        Card aceOfWands = new Card("Ace of Wands", "Ace of Wands", "Wands",
                        "The Ace of Wands represents inspiration, new beginnings, and potential.",
                        "This card signifies new opportunities and creative endeavors.", false);
        Card twoOfWands = new Card("Two of Wands", "Two of Wands", "Wands",
                        "The Two of Wands represents planning, progress, and decisions.",
                        "This card signifies making decisions and taking action towards your goals.", false);
        Card threeOfWands = new Card("Three of Wands", "Three of Wands", "Wands",
                        "The Three of Wands represents foresight, expansion, and exploration.",
                        "This card signifies looking ahead and planning for the future.", false);
        Card fourOfWands = new Card("Four of Wands", "Four of Wands", "Wands",
                        "The Four of Wands represents celebration, harmony, and homecoming.",
                        "This card signifies joyful gatherings and a sense of accomplishment.", false);
        Card fiveOfWands = new Card("Five of Wands", "Five of Wands", "Wands",
                        "The Five of Wands represents competition, conflict, and struggle.",
                        "This card signifies challenges and conflicts that arise from differences in opinions or goals.",
                        false);
        Card sixOfWands = new Card("Six of Wands", "Six of Wands", "Wands",
                        "The Six of Wands represents victory, recognition, and success.",
                        "This card signifies achieving success and receiving recognition for your efforts.", false);
        Card sevenOfWands = new Card("Seven of Wands", "Seven of Wands", "Wands",
                        "The Seven of Wands represents courage, perseverance, and standing your ground.",
                        "This card signifies defending your beliefs and overcoming obstacles.", false);
        Card eightOfWands = new Card("Eight of Wands", "Eight of Wands", "Wands",
                        "The Eight of Wands represents swiftness, movement, and rapid changes.",
                        "This card signifies swift progress and opportunities coming your way.", false);
        Card nineOfWands = new Card("Nine of Wands", "Nine of Wands", "Wands",
                        "The Nine of Wands represents resilience, persistence, and inner strength.",
                        "This card signifies overcoming challenges and continuing to move forward.", false);
        Card tenOfWands = new Card("Ten of Wands", "Ten of Wands", "Wands",
                        "The Ten of Wands represents burden, responsibility, and hard work.",
                        "This card signifies feeling overwhelmed by responsibilities or carrying a heavy burden.",
                        false);
        Card pageOfWands = new Card("Page of Wands", "Page of Wands", "Wands",
                        "The Page of Wands represents enthusiasm, exploration, and new beginnings.",
                        "This card signifies a new phase of exploration and discovery.", false);
        Card knightOfWands = new Card("Knight of Wands", "Knight of Wands", "Wands",
                        "The Knight of Wands represents action, adventure, and impulsiveness.",
                        "This card signifies a bold and adventurous individual who pursues their passions.", false);
        Card queenOfWands = new Card("Queen of Wands", "Queen of Wands", "Wands",
                        "The Queen of Wands represents confidence, determination, and independence.",
                        "This card signifies a confident and charismatic leader who is independent and passionate.",
                        false);
        Card kingOfWands = new Card("King of Wands", "King of Wands", "Wands",
                        "The King of Wands represents leadership, vision, and charisma.",
                        "This card signifies a visionary leader who inspires others with their charisma and passion.",
                        false);

        // Suit of Pentacles cards data
        Card aceOfPentacles = new Card("Ace of Pentacles", "Ace of Pentacles", "Pentacles",
                        "The Ace of Pentacles represents prosperity, new opportunities, and abundance.",
                        "This card signifies new financial opportunities and the potential for material success.",
                        false);
        Card twoOfPentacles = new Card("Two of Pentacles", "Two of Pentacles", "Pentacles",
                        "The Two of Pentacles represents balance, adaptability, and juggling priorities.",
                        "This card signifies managing multiple responsibilities and finding harmony in chaos.", false);
        Card threeOfPentacles = new Card("Three of Pentacles", "Three of Pentacles", "Pentacles",
                        "The Three of Pentacles represents teamwork, collaboration, and craftsmanship.",
                        "This card signifies working together with others to achieve a common goal and recognition for your skills.",
                        false);
        Card fourOfPentacles = new Card("Four of Pentacles", "Four of Pentacles", "Pentacles",
                        "The Four of Pentacles represents stability, security, and conservation.",
                        "This card signifies holding onto your resources and being cautious with your finances.",
                        false);
        Card fiveOfPentacles = new Card("Five of Pentacles", "Five of Pentacles", "Pentacles",
                        "The Five of Pentacles represents hardship, poverty, and financial loss.",
                        "This card signifies facing tough times and seeking support during difficult situations.",
                        false);
        Card sixOfPentacles = new Card("Six of Pentacles", "Six of Pentacles", "Pentacles",
                        "The Six of Pentacles represents generosity, charity, and giving back.",
                        "This card signifies sharing your wealth and resources with others and finding balance in giving and receiving.",
                        false);
        Card sevenOfPentacles = new Card("Seven of Pentacles", "Seven of Pentacles", "Pentacles",
                        "The Seven of Pentacles represents patience, perseverance, and long-term vision.",
                        "This card signifies waiting for results and focusing on long-term goals.", false);
        Card eightOfPentacles = new Card("Eight of Pentacles", "Eight of Pentacles", "Pentacles",
                        "The Eight of Pentacles represents dedication, craftsmanship, and skill development.",
                        "This card signifies working diligently to improve your skills and craftsmanship.", false);
        Card nineOfPentacles = new Card("Nine of Pentacles", "Nine of Pentacles", "Pentacles",
                        "The Nine of Pentacles represents luxury, self-sufficiency, and financial independence.",
                        "This card signifies enjoying the fruits of your labor and feeling secure and independent.",
                        false);
        Card tenOfPentacles = new Card("Ten of Pentacles", "Ten of Pentacles", "Pentacles",
                        "The Ten of Pentacles represents wealth, inheritance, and family legacy.",
                        "This card signifies financial security and stability within the family.", false);
        Card pageOfPentacles = new Card("Page of Pentacles", "Page of Pentacles", "Pentacles",
                        "The Page of Pentacles represents ambition, opportunities, and practicality.",
                        "This card signifies a new opportunity for learning and growth in practical matters.", false);
        Card knightOfPentacles = new Card("Knight of Pentacles", "Knight of Pentacles", "Pentacles",
                        "The Knight of Pentacles represents diligence, reliability, and responsibility.",
                        "This card signifies a focused and disciplined individual who is dedicated to their goals.",
                        false);
        Card queenOfPentacles = new Card("Queen of Pentacles", "Queen of Pentacles", "Pentacles",
                        "The Queen of Pentacles represents nurturing, abundance, and practicality.",
                        "This card signifies a nurturing and resourceful individual who provides stability and support.",
                        false);
        Card kingOfPentacles = new Card("King of Pentacles", "King of Pentacles", "Pentacles",
                        "The King of Pentacles represents wealth, success, and material abundance.",
                        "This card signifies a successful and wealthy individual who is grounded and practical.",
                        false);

        // Suit of Swords cards data
        Card aceOfSwords = new Card("Ace of Swords", "Ace of Swords", "Swords",
                        "The Ace of Swords represents clarity, truth, and mental clarity.",
                        "This card signifies new ideas, mental breakthroughs, and cutting through illusions.", false);
        Card twoOfSwords = new Card("Two of Swords", "Two of Swords", "Swords",
                        "The Two of Swords represents indecision, stalemate, and avoidance.",
                        "This card signifies a decision that needs to be made or a need to confront a situation.",
                        false);
        Card threeOfSwords = new Card("Three of Swords", "Three of Swords", "Swords",
                        "The Three of Swords represents heartbreak, sorrow, and emotional pain.",
                        "This card signifies experiencing emotional pain and heartache.", false);
        Card fourOfSwords = new Card("Four of Swords", "Four of Swords", "Swords",
                        "The Four of Swords represents rest, recuperation, and relaxation.",
                        "This card signifies taking a break and finding inner peace and tranquility.", false);
        Card fiveOfSwords = new Card("Five of Swords", "Five of Swords", "Swords",
                        "The Five of Swords represents conflict, defeat, and betrayal.",
                        "This card signifies conflict and tension arising from differing perspectives or betrayal.",
                        false);
        Card sixOfSwords = new Card("Six of Swords", "Six of Swords", "Swords",
                        "The Six of Swords represents transition, moving on, and leaving the past behind.",
                        "This card signifies moving away from difficult situations and finding a path to healing.",
                        false);
        Card sevenOfSwords = new Card("Seven of Swords", "Seven of Swords", "Swords",
                        "The Seven of Swords represents deception, betrayal, and evasion.",
                        "This card signifies deceitful behavior or avoiding responsibilities.", false);
        Card eightOfSwords = new Card("Eight of Swords", "Eight of Swords", "Swords",
                        "The Eight of Swords represents restriction, confinement, and feeling trapped.",
                        "This card signifies feeling stuck in a difficult situation and unable to see a way out.",
                        false);
        Card nineOfSwords = new Card("Nine of Swords", "Nine of Swords", "Swords",
                        "The Nine of Swords represents anxiety, fear, and worry.",
                        "This card signifies experiencing overwhelming anxiety and fear.", false);
        Card tenOfSwords = new Card("Ten of Swords", "Ten of Swords", "Swords",
                        "The Ten of Swords represents betrayal, failure, and rock bottom.",
                        "This card signifies reaching a low point or experiencing a sudden and painful ending.", false);
        Card pageOfSwords = new Card("Page of Swords", "Page of Swords", "Swords",
                        "The Page of Swords represents curiosity, intellect, and new perspectives.",
                        "This card signifies a thirst for knowledge and a willingness to explore new ideas.", false);
        Card knightOfSwords = new Card("Knight of Swords", "Knight of Swords", "Swords",
                        "The Knight of Swords represents action, aggression, and determination.",
                        "This card signifies taking swift action and pursuing your goals with determination.", false);
        Card queenOfSwords = new Card("Queen of Swords", "Queen of Swords", "Swords",
                        "The Queen of Swords represents clarity, independence, and sharp intellect.",
                        "This card signifies a strong and independent woman with keen analytical skills.", false);
        Card kingOfSwords = new Card("King of Swords", "King of Swords", "Swords",
                        "The King of Swords represents authority, truth, and mental clarity.",
                        "This card signifies a strong and authoritative leader with sharp intellect and clear communication.",
                        false);

        // Constructor or tarotDeck.pushinitialization method to populate the tarotDeck
        // list
        public void seedDatabase() {
                tarotDeck.addAll(Arrays.asList(theFool, theMagician, theHighPriestess, theEmpress, theEmperor,
                                theHierophant, theLovers, theChariot, Strength, theHermit, wheelOfFortune, Justice,
                                theHangedMan, Death, Temperance, theDevil, theTower, theStar, theMoon, theSun,
                                Judgement, theWorld));
                tarotDeck.addAll(Arrays.asList(aceOfCups, twoOfCups, threeOfCups, fourOfCups, fiveOfCups, sixOfCups,
                                sevenOfCups, eightOfCups, nineOfCups, tenOfCups, pageOfCups, knightOfCups, queenOfCups,
                                kingOfCups));
                tarotDeck.addAll(Arrays.asList(aceOfPentacles, twoOfPentacles,
                                threeOfPentacles, fourOfPentacles, fiveOfPentacles, sixOfPentacles,
                                sevenOfPentacles, eightOfPentacles, nineOfPentacles, tenOfPentacles,
                                pageOfPentacles, knightOfPentacles, queenOfPentacles, kingOfPentacles));
                tarotDeck.addAll(Arrays.asList(aceOfSwords, twoOfSwords, threeOfSwords,
                                fourOfSwords, fiveOfSwords, sixOfSwords, sevenOfSwords, eightOfSwords,
                                nineOfSwords, tenOfSwords, pageOfSwords, knightOfSwords, queenOfSwords, kingOfSwords));
                for (Card card : tarotDeck) {
                        cardRepository.save(card); // Save each card to the database
                }
        }
}

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
        Card theFool = new Card("The Fool", "The Fool", true,
                        "The Fool represents new beginnings, innocence, and spontaneity.",
                        "This card encourages you to embrace the unknown and trust in the journey ahead.",
                        "Major Arcana");
        Card theMagician = new Card("The Magician", "The Magician", true,
                        "The Magician represents manifestation, power, and resourcefulness.",
                        "This card reminds you of your ability to create your reality and harness your skills.",
                        "Major Arcana");
        Card theHighPriestess = new Card("The High Priestess", "The High Priestess", true,
                        "The High Priestess represents intuition, mystery, and understanding the subconscious mind.",
                        "This card encourages you to trust your inner wisdom and explore the depths of your subconscious.",
                        "Major Arcana");
        Card theEmpress = new Card("The Empress", "The Empress", true,
                        "The Empress represents nurturing, abundance, and fertility.",
                        "This card symbolizes creativity, fertility, and the power of nurturing and growth.",
                        "Major Arcana");
        Card theEmperor = new Card("The Emperor", "The Emperor", true,
                        "The Emperor represents authority, structure, and stability.",
                        "This card reminds you to take charge of your life and establish a solid foundation for success.",
                        "Major Arcana");
        Card theHierophant = new Card("The Hierophant", "The Hierophant", true,
                        "The Hierophant represents tradition, spirituality, and guidance.",
                        "This card encourages you to seek wisdom from spiritual teachings and traditional values.",
                        "Major Arcana");
        Card theLovers = new Card("The Lovers", "The Lovers", true,
                        "The Lovers represents love, relationships, and choices.",
                        "This card signifies important decisions regarding love, relationships, and personal values.",
                        "Major Arcana");
        Card theChariot = new Card("The Chariot", "The Chariot", true,
                        "The Chariot represents determination, willpower, and control.",
                        "This card reminds you to stay focused on your goals and overcome obstacles through determination.",
                        "Major Arcana");
        Card Strength = new Card("Strength", "Strength", true,
                        "Strength represents courage, inner strength, and resilience.",
                        "This card encourages you to tap into your inner strength and overcome challenges with grace.",
                        "Major Arcana");
        Card theHermit = new Card("The Hermit", "The Hermit", true,
                        "The Hermit represents introspection, soul-searching, and inner guidance.",
                        "This card signifies a period of self-reflection and inner wisdom.", "Major Arcana");
        Card wheelOfFortune = new Card("Wheel of Fortune", "Wheel of Fortune", true,
                        "Wheel of Fortune represents destiny, cycles, and change.",
                        "This card signifies the ups and downs of life and the cycles of change.", "Major Arcana");
        Card Justice = new Card("Justice", "Justice", true,
                        "Justice represents fairness, truth, and balance.",
                        "This card symbolizes fairness, truth, and karmic balance.", "Major Arcana");
        Card theHangedMan = new Card("The Hanged Man", "The Hanged Man", true,
                        "The Hanged Man represents surrender, release, and new perspectives.",
                        "This card encourages you to let go of control and gain new insights through surrender.",
                        "Major Arcana");
        Card Death = new Card("Death", "Death", true,
                        "Death represents endings, transformation, and new beginnings.",
                        "This card symbolizes the end of one chapter and the beginning of another.", "Major Arcana");
        Card Temperance = new Card("Temperance", "Temperance", true,
                        "Temperance represents balance, harmony, and moderation.",
                        "This card encourages you to find balance and moderation in all aspects of life.",
                        "Major Arcana");
        Card theDevil = new Card("The Devil", "The Devil", true,
                        "The Devil represents materialism, bondage, and illusions.",
                        "This card warns against being trapped by material desires and illusions.", "Major Arcana");
        Card theTower = new Card("The Tower", "The Tower", true,
                        "The Tower represents sudden change, upheaval, and revelation.",
                        "This card signifies the breakdown of old structures and the emergence of new insights.",
                        "Major Arcana");
        Card theStar = new Card("The Star", "The Star", true,
                        "The Star represents hope, inspiration, and renewal.",
                        "This card signifies hope, inspiration, and the promise of new beginnings.", "Major Arcana");
        Card theMoon = new Card("The Moon", "The Moon", true,
                        "The Moon represents intuition, illusion, and the subconscious mind.",
                        "This card symbolizes the exploration of the unconscious and hidden aspects of yourself.",
                        "Major Arcana");
        Card theSun = new Card("The Sun", "The Sun", true, "The Sun represents success, joy, and vitality.",
                        "This card signifies success, happiness, and fulfillment in all areas of life.",
                        "The Sun");
        Card Judgement = new Card("Judgement", "Judgement", true,
                        "Judgement represents awakening, rebirth, and inner calling.",
                        "This card symbolizes a spiritual awakening and the need to answer your inner calling.",
                        "Judgement");
        Card theWorld = new Card("The World", "The World", true,
                        "The World represents completion, fulfillment, and achievement.",
                        "This card signifies the completion of a cycle and the attainment of goals.",
                        "Major Arcana");

        // Suit of Cups cards data
        Card aceOfCups = new Card("Ace of Cups", "Ace of Cups", false,
                        "The Ace of Cups represents new beginnings, intuition, and emotional fulfillment.",
                        "This card often signifies a new phase of emotional growth or a deepening of existing emotional connections.",
                        "Cups");
        Card twoOfCups = new Card("Two of Cups", "Two of Cups", false,
                        "The Two of Cups represents partnership, harmony, and mutual attraction.",
                        "This card signifies a strong emotional bond and cooperation in relationships.", "Cups");
        Card threeOfCups = new Card("Three of Cups", "Three of Cups", false,
                        "The Three of Cups represents celebration, friendship, and joy.",
                        "This card signifies joyful gatherings, shared happiness, and emotional connections.", "Cups");
        Card fourOfCups = new Card("Four of Cups", "Four of Cups", false,
                        "The Four of Cups represents contemplation, apathy, and dissatisfaction.",
                        "This card warns against being stuck in a state of discontentment and encourages introspection.",
                        "Cups");
        Card fiveOfCups = new Card("Five of Cups", "Five of Cups", false,
                        "The Five of Cups represents loss, regret, and disappointment.",
                        "This card encourages you to acknowledge your feelings of loss and find a way to move forward.",
                        "Cups");
        Card sixOfCups = new Card("Six of Cups", "Six of Cups", false,
                        "The Six of Cups represents nostalgia, childhood memories, and innocence.",
                        "This card signifies reconnecting with the past and finding joy in simple pleasures.", "Cups");
        Card sevenOfCups = new Card("Seven of Cups", "Seven of Cups", false,
                        "The Seven of Cups represents choices, illusions, and fantasy.",
                        "This card warns against being overwhelmed by choices and encourages discernment.", "Cups");
        Card eightOfCups = new Card("Eight of Cups", "Eight of Cups", false,
                        "The Eight of Cups represents walking away, transition, and searching for deeper meaning.",
                        "This card signifies leaving behind what no longer serves you and embarking on a spiritual journey.",
                        "Cups");
        Card nineOfCups = new Card("Nine of Cups", "Nine of Cups", false,
                        "The Nine of Cups represents contentment, satisfaction, and emotional fulfillment.",
                        "This card signifies happiness and emotional abundance.", "Cups");
        Card tenOfCups = new Card("Ten of Cups", "Ten of Cups", false,
                        "The Ten of Cups represents harmony, family, and happiness.",
                        "This card signifies emotional fulfillment and blissful relationships within the family.",
                        "Cups");
        Card pageOfCups = new Card("Page of Cups", "Page of Cups", false,
                        "The Page of Cups represents creativity, intuition, and emotional messages.",
                        "This card signifies a new creative project or the emergence of intuitive insights.", "Cups");
        Card knightOfCups = new Card("Knight of Cups", "Knight of Cups", false,
                        "The Knight of Cups represents romance, charm, and emotional pursuit.",
                        "This card signifies a romantic and imaginative individual who follows their heart.", "Cups");
        Card queenOfCups = new Card("Queen of Cups", "Queen of Cups", false,
                        "The Queen of Cups represents compassion, intuition, and emotional stability.",
                        "This card signifies nurturing and empathetic qualities.", "Cups");
        Card kingOfCups = new Card("King of Cups", "King of Cups", false,
                        "The King of Cups represents emotional maturity, wisdom, and calmness.",
                        "This card signifies someone who is emotionally balanced and empathetic.", "Cups");

        // Suit of Wands cards data
        Card aceOfWands = new Card("Ace of Wands", "Ace of Wands", false,
                        "The Ace of Wands represents inspiration, new beginnings, and potential.",
                        "This card signifies new opportunities and creative endeavors.", "Wands");
        Card twoOfWands = new Card("Two of Wands", "Two of Wands", false,
                        "The Two of Wands represents planning, progress, and decisions.",
                        "This card signifies making decisions and taking action towards your goals.", "Wands");
        Card threeOfWands = new Card("Three of Wands", "Three of Wands", false,
                        "The Three of Wands represents foresight, expansion, and exploration.",
                        "This card signifies looking ahead and planning for the future.", "Wands");
        Card fourOfWands = new Card("Four of Wands", "Four of Wands", false,
                        "The Four of Wands represents celebration, harmony, and homecoming.",
                        "This card signifies joyful gatherings and a sense of accomplishment.", "Wands");
        Card fiveOfWands = new Card("Five of Wands", "Five of Wands", false,
                        "The Five of Wands represents competition, conflict, and struggle.",
                        "This card signifies challenges and conflicts that arise from differences in opinions or goals.",
                        "Wands");
        Card sixOfWands = new Card("Six of Wands", "Six of Wands", false,
                        "The Six of Wands represents victory, recognition, and success.",
                        "This card signifies achieving success and receiving recognition for your efforts.", "Wands");
        Card sevenOfWands = new Card("Seven of Wands", "Seven of Wands", false,
                        "The Seven of Wands represents courage, perseverance, and standing your ground.",
                        "This card signifies defending your beliefs and overcoming obstacles.", "Wands");
        Card eightOfWands = new Card("Eight of Wands", "Eight of Wands", false,
                        "The Eight of Wands represents swiftness, movement, and rapid changes.",
                        "This card signifies swift progress and opportunities coming your way.", "Wands");
        Card nineOfWands = new Card("Nine of Wands", "Nine of Wands", false,
                        "The Nine of Wands represents resilience, persistence, and inner strength.",
                        "This card signifies overcoming challenges and continuing to move forward.", "Wands");
        Card tenOfWands = new Card("Ten of Wands", "Ten of Wands", false,
                        "The Ten of Wands represents burden, responsibility, and hard work.",
                        "This card signifies feeling overwhelmed by responsibilities or carrying a heavy burden.",
                        "Wands");
        Card pageOfWands = new Card("Page of Wands", "Page of Wands", false,
                        "The Page of Wands represents enthusiasm, exploration, and new beginnings.",
                        "This card signifies a new phase of exploration and discovery.", "Wands");
        Card knightOfWands = new Card("Knight of Wands", "Knight of Wands", false,
                        "The Knight of Wands represents action, adventure, and impulsiveness.",
                        "This card signifies a bold and adventurous individual who pursues their passions.", "Wands");
        Card queenOfWands = new Card("Queen of Wands", "Queen of Wands", false,
                        "The Queen of Wands represents confidence, determination, and independence.",
                        "This card signifies a confident and charismatic leader who is independent and passionate.",
                        "Wands");
        Card kingOfWands = new Card("King of Wands", "King of Wands", false,
                        "The King of Wands represents leadership, vision, and charisma.",
                        "This card signifies a visionary leader who inspires others with their charisma and passion.",
                        "Wands");

        // Suit of Pentacles cards data
        Card aceOfPentacles = new Card("Ace of Pentacles", "Ace of Pentacles", false,
                        "The Ace of Pentacles represents prosperity, new opportunities, and abundance.",
                        "This card signifies new financial opportunities and the potential for material success.",
                        "Pentacles");
        Card twoOfPentacles = new Card("Two of Pentacles", "Two of Pentacles", false,
                        "The Two of Pentacles represents balance, adaptability, and juggling priorities.",
                        "This card signifies managing multiple responsibilities and finding harmony in chaos.",
                        "Pentacles");
        Card threeOfPentacles = new Card("Three of Pentacles", "Three of Pentacles", false,
                        "The Three of Pentacles represents teamwork, collaboration, and craftsmanship.",
                        "This card signifies working together with others to achieve a common goal and recognition for your skills.",
                        "Pentacles");
        Card fourOfPentacles = new Card("Four of Pentacles", "Four of Pentacles", false,
                        "The Four of Pentacles represents stability, security, and conservation.",
                        "This card signifies holding onto your resources and being cautious with your finances.",
                        "Pentacles");
        Card fiveOfPentacles = new Card("Five of Pentacles", "Five of Pentacles", false,
                        "The Five of Pentacles represents hardship, poverty, and financial loss.",
                        "This card signifies facing tough times and seeking support during difficult situations.",
                        "Pentacles");
        Card sixOfPentacles = new Card("Six of Pentacles", "Six of Pentacles", false,
                        "The Six of Pentacles represents generosity, charity, and giving back.",
                        "This card signifies sharing your wealth and resources with others and finding balance in giving and receiving.",
                        "Pentacles");
        Card sevenOfPentacles = new Card("Seven of Pentacles", "Seven of Pentacles", false,
                        "The Seven of Pentacles represents patience, perseverance, and long-term vision.",
                        "This card signifies waiting for results and focusing on long-term goals.", "Pentacles");
        Card eightOfPentacles = new Card("Eight of Pentacles", "Eight of Pentacles", false,
                        "The Eight of Pentacles represents dedication, craftsmanship, and skill development.",
                        "This card signifies working diligently to improve your skills and craftsmanship.",
                        "Pentacles");
        Card nineOfPentacles = new Card("Nine of Pentacles", "Nine of Pentacles", false,
                        "The Nine of Pentacles represents luxury, self-sufficiency, and financial independence.",
                        "This card signifies enjoying the fruits of your labor and feeling secure and independent.",
                        "Pentacles");
        Card tenOfPentacles = new Card("Ten of Pentacles", "Ten of Pentacles", false,
                        "The Ten of Pentacles represents wealth, inheritance, and family legacy.",
                        "This card signifies financial security and stability within the family.", "Pentacles");
        Card pageOfPentacles = new Card("Page of Pentacles", "Page of Pentacles", false,
                        "The Page of Pentacles represents ambition, opportunities, and practicality.",
                        "This card signifies a new opportunity for learning and growth in practical matters.",
                        "Pentacles");
        Card knightOfPentacles = new Card("Knight of Pentacles", "Knight of Pentacles", false,
                        "The Knight of Pentacles represents diligence, reliability, and responsibility.",
                        "This card signifies a focused and disciplined individual who is dedicated to their goals.",
                        "Pentacles");
        Card queenOfPentacles = new Card("Queen of Pentacles", "Queen of Pentacles", false,
                        "The Queen of Pentacles represents nurturing, abundance, and practicality.",
                        "This card signifies a nurturing and resourceful individual who provides stability and support.",
                        "Pentacles");
        Card kingOfPentacles = new Card("King of Pentacles", "King of Pentacles", false,
                        "The King of Pentacles represents wealth, success, and material abundance.",
                        "This card signifies a successful and wealthy individual who is grounded and practical.",
                        "Pentacles");

        // Suit of Swords cards data
        Card aceOfSwords = new Card("Ace of Swords", "Ace of Swords", false,
                        "The Ace of Swords represents clarity, truth, and mental clarity.",
                        "This card signifies new ideas, mental breakthroughs, and cutting through illusions.",
                        "Swords");
        Card twoOfSwords = new Card("Two of Swords", "Two of Swords", false,
                        "The Two of Swords represents indecision, stalemate, and avoidance.",
                        "This card signifies a decision that needs to be made or a need to confront a situation.",
                        "Swords");
        Card threeOfSwords = new Card("Three of Swords", "Three of Swords", false,
                        "The Three of Swords represents heartbreak, sorrow, and emotional pain.",
                        "This card signifies experiencing emotional pain and heartache.", "Swords");
        Card fourOfSwords = new Card("Four of Swords", "Four of Swords", false,
                        "The Four of Swords represents rest, recuperation, and relaxation.",
                        "This card signifies taking a break and finding inner peace and tranquility.", "Swords");
        Card fiveOfSwords = new Card("Five of Swords", "Five of Swords", false,
                        "The Five of Swords represents conflict, defeat, and betrayal.",
                        "This card signifies conflict and tension arising from differing perspectives or betrayal.",
                        "Swords");
        Card sixOfSwords = new Card("Six of Swords", "Six of Swords", false,
                        "The Six of Swords represents transition, moving on, and leaving the past behind.",
                        "This card signifies moving away from difficult situations and finding a path to healing.",
                        "Swords");
        Card sevenOfSwords = new Card("Seven of Swords", "Seven of Swords", false,
                        "The Seven of Swords represents deception, betrayal, and evasion.",
                        "This card signifies deceitful behavior or avoiding responsibilities.", "Swords");
        Card eightOfSwords = new Card("Eight of Swords", "Eight of Swords", false,
                        "The Eight of Swords represents restriction, confinement, and feeling trapped.",
                        "This card signifies feeling stuck in a difficult situation and unable to see a way out.",
                        "Swords");
        Card nineOfSwords = new Card("Nine of Swords", "Nine of Swords", false,
                        "The Nine of Swords represents anxiety, fear, and worry.",
                        "This card signifies experiencing overwhelming anxiety and fear.", "Swords");
        Card tenOfSwords = new Card("Ten of Swords", "Ten of Swords", false,
                        "The Ten of Swords represents betrayal, failure, and rock bottom.",
                        "This card signifies reaching a low point or experiencing a sudden and painful ending.",
                        "Swords");
        Card pageOfSwords = new Card("Page of Swords", "Page of Swords", false,
                        "The Page of Swords represents curiosity, intellect, and new perspectives.",
                        "This card signifies a thirst for knowledge and a willingness to explore new ideas.", "Swords");
        Card knightOfSwords = new Card("Knight of Swords", "Knight of Swords", false,
                        "The Knight of Swords represents action, aggression, and determination.",
                        "This card signifies taking swift action and pursuing your goals with determination.",
                        "Swords");
        Card queenOfSwords = new Card("Queen of Swords", "Queen of Swords", false,
                        "The Queen of Swords represents clarity, independence, and sharp intellect.",
                        "This card signifies a strong and independent woman with keen analytical skills.", "Swords");
        Card kingOfSwords = new Card("King of Swords", "King of Swords", false,
                        "The King of Swords represents authority, truth, and mental clarity.",
                        "This card signifies a strong and authoritative leader with sharp intellect and clear communication.",
                        "Swords");

        // Constructor or tarotDeck.pushinitialization method to populate the tarotDeck
        // list
        public void seedDatabase() {
                // tarotDeck.add(theFool);
                tarotDeck.addAll(Arrays.asList(theFool, theMagician, theHighPriestess,
                                theEmpress, theEmperor,
                                theHierophant, theLovers, theChariot, Strength, theHermit, wheelOfFortune,
                                Justice,
                                theHangedMan, Death, Temperance, theDevil, theTower, theStar, theMoon,
                                theSun,
                                Judgement, theWorld));
                tarotDeck.addAll(Arrays.asList(aceOfWands, twoOfWands, threeOfWands, fourOfWands,
                                fiveOfWands, sixOfWands, sevenOfWands, eightOfWands, nineOfWands,
                                tenOfWands, pageOfWands, knightOfWands, queenOfWands, kingOfWands));
                tarotDeck.addAll(Arrays.asList(aceOfCups, twoOfCups, threeOfCups, fourOfCups,
                                fiveOfCups, sixOfCups,
                                sevenOfCups, eightOfCups, nineOfCups, tenOfCups, pageOfCups, knightOfCups,
                                queenOfCups,
                                kingOfCups));
                tarotDeck.addAll(Arrays.asList(aceOfPentacles, twoOfPentacles,
                                threeOfPentacles, fourOfPentacles, fiveOfPentacles, sixOfPentacles,
                                sevenOfPentacles, eightOfPentacles, nineOfPentacles, tenOfPentacles,
                                pageOfPentacles, knightOfPentacles, queenOfPentacles, kingOfPentacles));
                tarotDeck.addAll(Arrays.asList(aceOfSwords, twoOfSwords, threeOfSwords,
                                fourOfSwords, fiveOfSwords, sixOfSwords, sevenOfSwords, eightOfSwords,
                                nineOfSwords, tenOfSwords, pageOfSwords, knightOfSwords, queenOfSwords,
                                kingOfSwords));

                if (cardRepository.count() == 0) {
                        for (Card card : tarotDeck) {
                                cardRepository.save(card); // Save each card to the database
                        }
                }
        }
}

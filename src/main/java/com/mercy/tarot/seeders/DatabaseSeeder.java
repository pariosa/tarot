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
        Card theFool = new Card("This card encourages you to embrace the unknown and trust in the journey ahead.",
                        "The Fool",
                        true,
                        "The Fool represents new beginnings, innocence, and spontaneity.",
                        "Major Arcana",
                        "0 - The Fool",
                        "Naivety, foolishness, recklessness, lack of responsibility.");
        Card theMagician = new Card(
                        "This card reminds you of your ability to create your reality and harness your skills.",
                        "The Magician",
                        true,
                        "The Magician represents manifestation, power, and resourcefulness.",
                        "Major Arcana",
                        "I -The Magician",
                        "Manipulation, poor planning, untapped talents.");
        Card theHighPriestess = new Card(
                        "This card encourages you to trust your inner wisdom and explore the depths of your subconscious.",
                        "The High Priestess",
                        true,
                        "The High Priestess represents intuition, mystery, and understanding the subconscious mind.",
                        "Major Arcana",
                        "II - The High Priestess",
                        "Secrets, disconnected from intuition, withdrawn.");
        Card theEmpress = new Card("This card symbolizes creativity, fertility, and the power of nurturing and growth.",
                        "The Empress",
                        true,
                        "The Empress represents nurturing, abundance, and fertility.",
                        "Major Arcana",
                        "III - The Empress",
                        "Dependence, smothering, emptiness, lack of growth.");
        Card theEmperor = new Card(
                        "This card reminds you to take charge of your life and establish a solid foundation for success.",
                        "The Emperor",
                        true,
                        "The Emperor represents authority, structure, and stability.",
                        "Major Arcana",
                        "IV - The Emperor",
                        "Domination, excessive control, rigidity, inflexibility.");
        Card theHierophant = new Card(
                        "This card encourages you to seek wisdom from spiritual teachings and traditional valuest",
                        "The Hierophant",
                        true,
                        "The Hierophant represents tradition, spirituality, and guidance.",
                        "Major Arcana",
                        "V - The Hierophant",
                        "Rebellion, breaking conventions, misalignment.");
        Card theLovers = new Card(
                        "This card signifies important decisions regarding love, relationships, and personal values.",
                        "The Lovers",
                        true,
                        "The Lovers represents love, relationships, and choices.",
                        "Major Arcana",
                        "VI - The Lovers",
                        "Disharmony, imbalance, misaligned values, broken relationships.");
        Card theChariot = new Card(
                        "This card reminds you to stay focused on your goals and overcome obstacles through determination.",
                        "The Chariot",
                        true,
                        "The Chariot represents determination, willpower, and control.",
                        "Major Arcana",
                        "VII - The Chariot",
                        "Aggression, lack of direction, loss of control, obstacles.");
        Card Strength = new Card(
                        "This card encourages you to tap into your inner strength and overcome challenges with grace.",
                        "Strength",
                        true,
                        "Strength represents courage, inner strength, and resilience.",
                        "Major Arcana",
                        "VIII - Strength",
                        "Weakness, self-doubt, lack of discipline.");
        Card theHermit = new Card(
                        "This card signifies a period of self-reflection and inner wisdom.",
                        "The Hermit",
                        true,
                        "The Hermit represents introspection, soul-searching, and inner guidance.",
                        "Major Arcana",
                        "IX - The Hermit",
                        "Loneliness, isolation, lost your way, withdrawal.");
        Card wheelOfFortune = new Card(
                        "This card signifies the ups and downs of life and the cycles of change.",
                        "Wheel of Fortune",
                        true,
                        "Wheel of Fortune represents destiny, cycles, and change.",
                        "Major Arcana",
                        "X - Wheel of Fortune",
                        "Bad luck, resistance to change, breaking cycles.");
        Card Justice = new Card(
                        "This card symbolizes fairness, truth, and karmic balance.",
                        "Justice",
                        true,
                        "Justice represents fairness, truth, and balance.",
                        "Major Arcana",
                        "XI - Justice",
                        "Injustice, lack of accountability, dishonesty.");
        Card theHangedMan = new Card(
                        "This card encourages you to let go of control and gain new insights through surrender.",
                        "The Hanged Man",
                        true,
                        "The Hanged Man represents surrender, release, and new perspectives.",
                        "Major Arcana",
                        "XII - The Hanged Man",
                        "Stalling, needless sacrifice, fear of sacrifice.");
        Card Death = new Card(
                        "This card symbolizes the end of one chapter and the beginning of another.",
                        "Death",
                        true,
                        "Death represents endings, transformation, and new beginnings.",
                        "Major Arcana",
                        "XIII - Death",
                        "Resistance to change, personal transformation, stagnation.");
        Card Temperance = new Card(
                        "This card encourages you to find balance and moderation in all aspects of life.",
                        "Temperance",
                        true,
                        "Temperance represents balance, harmony, and moderation.",
                        "Major Arcana",
                        "XIV - Temperance",
                        "Imbalance, excess, lack of long-term vision.");
        Card theDevil = new Card(
                        "This card warns against being trapped by material desires and illusions",
                        "The Devil",
                        true,
                        "The Devil represents materialism, bondage, and illusions.",
                        "Major Arcana",
                        "XV - The Devil",
                        "Breaking free, reclaiming power, overcoming restrictions.");
        Card theTower = new Card(
                        "This card signifies the breakdown of old structures and the emergence of new insights.",
                        "The Tower",
                        true,
                        "The Tower represents sudden change, upheaval, and revelation.",
                        "Major Arcana",
                        "XVI - The Tower",
                        "Fear of change, avoiding disaster, delay of destruction.");
        Card theStar = new Card(
                        "This card signifies hope, inspiration, and the promise of new beginnings.",
                        "The Star",
                        true,
                        "The Star represents hope, inspiration, and renewal.",
                        "Major Arcana",
                        "XVII - The Star",
                        "Disappointment, lack of faith, discouragement.");
        Card theMoon = new Card(
                        "This card symbolizes the exploration of the unconscious and hidden aspects of yourself.",
                        "The Moon",
                        true,
                        "The Moon represents intuition, illusion, and the subconscious mind.",
                        "Major Arcana",
                        "XVIII - The Moon",
                        "Confusion, fear, misinterpretation, deception.");
        Card theSun = new Card(
                        "This card signifies success, happiness, and fulfillment in all areas of life.",
                        "The Sun",
                        true,
                        "The Sun represents success, joy, and vitality.",
                        "Major Arcana",
                        "XIV - The Sun",
                        "Negativity, depression, sadness, lack of enthusiasm.");
        Card Judgement = new Card(
                        "This card symbolizes a spiritual awakening and the need to answer your inner calling.",
                        "Judgement",
                        true,
                        "Judgement represents awakening, rebirth, and inner calling.",
                        "Major Arcana",
                        "XX - Judgement",
                        "Self-doubt, refusal of self-examination, ignoring the call.");
        Card theWorld = new Card(
                        "This card signifies the completion of a cycle and the attainment of goals.",
                        "The World",
                        true,
                        "The World represents completion, fulfillment, and achievement.",
                        "Major Arcana",
                        "XXI - The World",
                        "Incompletion, no closure, stagnation, needing to achieve more.");

        // Suit of Cups cards data
        // Ace of Cups
        Card aceOfCups = new Card(
                        "This card often signifies a new phase of emotional growth or a deepening of existing emotional connections",
                        "Ace of Cups",
                        false,
                        "The Ace of Cups represents new beginnings, intuition, and emotional fulfillment.",
                        "Cups",
                        "Ace Of Cups",
                        "Blocked emotions, emotional loss, troubles in love.");

        // Two of Cups
        Card twoOfCups = new Card(
                        "The Two of Cups represents harmony, unity, and partnership.",
                        "Two of Cups",
                        false,
                        "The Two of Cups signifies a strong bond and mutual support between two people.",
                        "Cups",
                        "Two Of Cups",
                        "Break-up, imbalance in relationship, miscommunication.");

        // Three of Cups
        Card threeOfCups = new Card(
                        "The Three of Cups signifies celebration, friendship, and community.",
                        "Three of Cups",
                        false,
                        "The Three of Cups represents joyous occasions and connections with others.",
                        "Cups",
                        "Three Of Cups",
                        "Overindulgence, gossip, isolation.");

        // Four of Cups
        Card fourOfCups = new Card(
                        "The Four of Cups indicates contemplation, withdrawal, and introspection.",
                        "Four of Cups",
                        false,
                        "The Four of Cups suggests taking time to reflect on one's emotions and priorities.",
                        "Cups",
                        "Four Of Cups",
                        "Missed opportunities, discontent, reevaluation.");

        // Five of Cups
        Card fiveOfCups = new Card(
                        "The Five of Cups represents loss, regret, and disappointment.",
                        "Five of Cups",
                        false,
                        "The Five of Cups suggests focusing on what remains rather than dwelling on what is lost.",
                        "Cups",
                        "Five Of Cups",
                        "Acceptance, moving on, finding peace.");

        // Six of Cups
        Card sixOfCups = new Card(
                        "The Six of Cups symbolizes nostalgia, childhood memories, and innocence.",
                        "Six of Cups",
                        false,
                        "The Six of Cups encourages reconnecting with the past and finding joy in simpler times.",
                        "Cups",
                        "Six Of Cups",
                        "Stuck in past, naive, unrealistic.");

        // Seven of Cups
        Card sevenOfCups = new Card(
                        "The Seven of Cups represents choices, illusions, and fantasy.",
                        "Seven of Cups",
                        false,
                        "The Seven of Cups warns against being seduced by unrealistic dreams and desires.",
                        "Cups",
                        "Seven Of Cups",
                        "Clarity, decision made, focused.");

        // Eight of Cups
        Card eightOfCups = new Card(
                        "The Eight of Cups signifies walking away, abandonment, and moving on.",
                        "Eight of Cups",
                        false,
                        "The Eight of Cups suggests leaving behind what no longer serves one's emotional well-being.",
                        "Cups",
                        "Eight Of Cups",
                        "Avoidance, fear of moving on, stagnation.");

        // Nine of Cups
        Card nineOfCups = new Card(
                        "The Nine of Cups represents contentment, satisfaction, and emotional fulfillment.",
                        "Nine of Cups",
                        false,
                        "The Nine of Cups indicates a sense of emotional abundance and happiness.",
                        "Cups",
                        "Nine Of Cups",
                        "Discontent, unhappiness, imperfection.");

        // Ten of Cups
        Card tenOfCups = new Card(
                        "The Ten of Cups signifies harmony, happiness, and family bliss.",
                        "Ten of Cups",
                        false,
                        "The Ten of Cups represents the ultimate emotional fulfillment and domestic joy.",
                        "Cups",
                        "Ten Of Cups",
                        "Broken family, disconnection, unhappiness.");

        // Page of Cups
        Card pageOfCups = new Card(
                        "The Page of Cups represents creativity, intuition, and emotional exploration.",
                        "Page of Cups",
                        false,
                        "The Page of Cups encourages embracing one's artistic or intuitive side.",
                        "Cups",
                        "Page Of Cups",
                        "Immaturity, blocked intuition, moodiness.");

        // Knight of Cups
        Card knightOfCups = new Card(
                        "The Knight of Cups symbolizes romance, charm, and imagination.",
                        "Knight of Cups",
                        false,
                        "The Knight of Cups suggests following one's heart and pursuing romantic or artistic endeavors.",
                        "Cups",
                        "Knight Of Cups",
                        "Disappointment, unrealistic, disillusioned");

        // Queen of Cups
        Card queenOfCups = new Card(
                        "The Queen of Cups represents compassion, intuition, and emotional stability.",
                        "Queen of Cups",
                        false,
                        "The Queen of Cups embodies nurturing qualities and deep emotional understanding.",
                        "Cups",
                        "Queen Of Cups",
                        "Insecurity, dependence, manipulative.");

        // King of Cups
        Card kingOfCups = new Card(
                        "The King of Cups signifies emotional maturity, wisdom, and calmness.",
                        "King of Cups",
                        false,
                        "The King of Cups embodies control over emotions and compassionate leadership.",
                        "Cups",
                        "King Of Cups",
                        "Emotional manipulation, moodiness, untrustworthy.");

        // Wands
        // Ace of Wands
        Card aceOfWands = new Card(
                        "The Ace of Wands represents inspiration, new beginnings, and potential.",
                        "Ace of Wands",
                        false,
                        "The Ace of Wands signifies the spark of creative energy and the start of a passionate journey.",
                        "Wands",
                        "Ace Of Wands",
                        "Lack of energy, lack of passion, boredom.");

        // Two of Wands
        Card twoOfWands = new Card(
                        "The Two of Wands symbolizes planning, exploration, and potential.",
                        "Two of Wands",
                        false,
                        "The Two of Wands suggests considering different paths and expanding horizons.",
                        "Wands",
                        "Two Of Wands",
                        "Fear of unknown, lack of planning, poor decisions.");

        // Three of Wands
        Card threeOfWands = new Card(
                        "The Three of Wands represents foresight, leadership, and expansion.",
                        "Three of Wands",
                        false,
                        "The Three of Wands signifies taking initiative and preparing for future opportunities.",
                        "Wands",
                        "Three Of Wands",
                        "Delays, obstacles to success, frustration.");

        // Four of Wands
        Card fourOfWands = new Card(
                        "The Four of Wands symbolizes celebration, harmony, and achievement.",
                        "Four of Wands",
                        false,
                        "The Four of Wands represents joyous occasions and a sense of accomplishment.",
                        "Wands",
                        "Four Of Wands",
                        "Lack of support, transience, home conflicts.");

        // Five of Wands
        Card fiveOfWands = new Card(
                        "The Five of Wands signifies competition, conflict, and struggle.",
                        "Five of Wands",
                        false,
                        "The Five of Wands suggests facing challenges and overcoming obstacles.",
                        "Wands",
                        "Five Of Wands",
                        "Avoiding conflict, respecting differences, peace.");

        // Six of Wands
        Card sixOfWands = new Card(
                        "The Six of Wands represents victory, recognition, and success.",
                        "Six of Wands",
                        false,
                        "The Six of Wands signifies public acclaim and being recognized for achievements.",
                        "Wands",
                        "Six Of Wands",
                        "Lack of recognition, punishment, ego issues.");

        // Seven of Wands
        Card sevenOfWands = new Card(
                        "The Seven of Wands symbolizes perseverance, determination, and standing one's ground.",
                        "Seven of Wands",
                        false,
                        "The Seven of Wands suggests defending one's position and asserting oneself.",
                        "Wands",
                        "Seven Of Wands",
                        "Overwhelm, giving up, lack of confidence.");

        // Eight of Wands
        Card eightOfWands = new Card(
                        "The Eight of Wands represents swiftness, progress, and rapid change.",
                        "Eight of Wands",
                        false,
                        "The Eight of Wands signifies swift developments and forward momentum.",
                        "Wands",
                        "Eight Of Wands",
                        "Delays, frustration, resisting change.");

        // Nine of Wands
        Card nineOfWands = new Card(
                        "The Nine of Wands symbolizes resilience, perseverance, and inner strength.",
                        "Nine of Wands",
                        false,
                        "The Nine of Wands suggests overcoming challenges and staying resilient.",
                        "Wands",
                        "Nine Of Wands",
                        "Stubbornness, defensiveness, paranoia.");

        // Ten of Wands
        Card tenOfWands = new Card(
                        "The Ten of Wands represents burden, responsibility, and overwhelm.",
                        "Ten of Wands",
                        false,
                        "The Ten of Wands signifies carrying a heavy load and feeling overwhelmed.",
                        "Wands",
                        "Ten Of Wands",
                        "Dropping burdens, feeling overwhelmed, burnout.");

        // Page of Wands
        Card pageOfWands = new Card(
                        "The Page of Wands signifies enthusiasm, exploration, and new opportunities.",
                        "Page of Wands",
                        false,
                        "The Page of Wands encourages embracing new adventures and pursuing passions.",
                        "Wands",
                        "Page Of Wands",
                        "Lack of direction, pessimism, immaturity.");

        // Knight of Wands
        Card knightOfWands = new Card(
                        "The Knight of Wands represents action, adventure, and impulsiveness.",
                        "Knight of Wands",
                        false,
                        "The Knight of Wands suggests pursuing one's goals with passion and energy.",
                        "Wands",
                        "Knight Of Wands",
                        "Haste, scattered energy, delays, frustration.");

        // Queen of Wands
        Card queenOfWands = new Card(
                        "The Queen of Wands symbolizes confidence, independence, and determination.",
                        "Queen of Wands",
                        false,
                        "The Queen of Wands embodies strength and leadership in pursuing one's vision.",
                        "Wands",
                        "Queen Of Wands",
                        "Jealousy, insecurity, selfishness.");

        // King of Wands
        Card kingOfWands = new Card(
                        "The King of Wands represents charisma, leadership, and vision.",
                        "King of Wands",
                        false,
                        "The King of Wands embodies strong leadership qualities and the ability to inspire others.",
                        "Wands",
                        "King Of Wands",
                        "Tyranny, impulsive, overbearing.");

        // Pentacles
        // Ace of Pentacles
        Card aceOfPentacles = new Card(
                        "The Ace of Pentacles symbolizes opportunity, prosperity, and new beginnings.",
                        "Ace of Pentacles",
                        false,
                        "The Ace of Pentacles represents the potential for material and financial success.",
                        "Pentacles",
                        "Ace Of Pentacles",
                        "Missed opportunity, financial loss, lack of planning.");

        // Two of Pentacles
        Card twoOfPentacles = new Card(
                        "The Two of Pentacles signifies balance, adaptability, and juggling priorities.",
                        "Two of Pentacles",
                        false,
                        "The Two of Pentacles suggests finding harmony amidst life's challenges.",
                        "Pentacles",
                        "Two Of Pentacles",
                        "Imbalance, disorganization, overwhelmed.");

        // Three of Pentacles
        Card threeOfPentacles = new Card(
                        "The Three of Pentacles represents teamwork, collaboration, and craftsmanship.",
                        "Three of Pentacles",
                        false,
                        "The Three of Pentacles signifies recognition for hard work and skillful efforts.",
                        "Pentacles",
                        "Three Of Pentacles",
                        "Lack of teamwork, poor planning, mediocrity.");

        // Four of Pentacles
        Card fourOfPentacles = new Card(
                        "The Four of Pentacles symbolizes stability, security, and holding onto resources.",
                        "Four of Pentacles",
                        false,
                        "The Four of Pentacles suggests being cautious with finances and clinging to possessions.",
                        "Pentacles",
                        "Four Of Pentacles",
                        "Greed, materialism, self-protection.");

        // Five of Pentacles
        Card fiveOfPentacles = new Card(
                        "The Five of Pentacles represents financial hardship, poverty, and isolation.",
                        "Five of Pentacles",
                        false,
                        "The Five of Pentacles suggests facing challenges and seeking support during tough times.",
                        "Pentacles",
                        "Five Of Pentacles",
                        "Recovery, finding help, new opportunities.");

        // Six of Pentacles
        Card sixOfPentacles = new Card(
                        "The Six of Pentacles symbolizes generosity, charity, and sharing wealth.",
                        "Six of Pentacles",
                        false,
                        "The Six of Pentacles signifies giving back and finding balance in giving and receiving.",
                        "Pentacles",
                        "Six Of Pentacles",
                        " Stinginess, debt, selfishness.");

        // Seven of Pentacles
        Card sevenOfPentacles = new Card(
                        "The Seven of Pentacles represents patience, investment, and long-term growth.",
                        "Seven of Pentacles",
                        false,
                        "The Seven of Pentacles suggests assessing progress and waiting for results.",
                        "Pentacles",
                        "Seven Of Pentacles",
                        "Frustration, impatience, lack of reward.");

        // Eight of Pentacles
        Card eightOfPentacles = new Card(
                        "The Eight of Pentacles symbolizes craftsmanship, skill development, and diligence.",
                        "Eight of Pentacles",
                        false,
                        "The Eight of Pentacles signifies honing one's craft and dedication to improvement.",
                        "Pentacles",
                        "Eight Of Pentacles",
                        "Lack of focus, perfectionism, no motivation.");

        // Nine of Pentacles
        Card nineOfPentacles = new Card(
                        "The Nine of Pentacles represents abundance, self-sufficiency, and luxury.",
                        "Nine of Pentacles",
                        false,
                        "The Nine of Pentacles signifies enjoying the fruits of labor and independence.",
                        "Pentacles",
                        "Nine Of Pentacles",
                        "Financial dependency, loneliness, lack of stability.");

        // Ten of Pentacles
        Card tenOfPentacles = new Card(
                        "The Ten of Pentacles symbolizes wealth, inheritance, and long-term security.",
                        "Ten of Pentacles",
                        false,
                        "The Ten of Pentacles suggests family prosperity and financial legacy.",
                        "Pentacles",
                        "Ten Of Pentacles",
                        "Family issues, loss, financial failure.");

        // Page of Pentacles
        Card pageOfPentacles = new Card(
                        "The Page of Pentacles represents ambition, studiousness, and practicality.",
                        "Page of Pentacles",
                        false,
                        "The Page of Pentacles suggests learning new skills and focusing on practical goals.",
                        "Pentacles",
                        "Page Of Pentacles",
                        "Procrastination, immaturity, lack of progress.");

        // Knight of Pentacles
        Card knightOfPentacles = new Card(
                        "The Knight of Pentacles symbolizes responsibility, diligence, and reliability.",
                        "Knight of Pentacles",
                        false,
                        "The Knight of Pentacles suggests taking a methodical approach and making steady progress.",
                        "Pentacles",
                        "Knight Of Pentacles",
                        "Boredom, stagnation, lack of ambition.");

        // Queen of Pentacles
        Card queenOfPentacles = new Card(
                        "The Queen of Pentacles represents nurturing, abundance, and practicality.",
                        "Queen of Pentacles",
                        false,
                        "The Queen of Pentacles embodies generosity and taking care of others.",
                        "Pentacles",
                        "Queen Of Pentacles",
                        "Smothering, neglect, overly materialistic.");

        // King of Pentacles
        Card kingOfPentacles = new Card(
                        "The King of Pentacles symbolizes wealth, success, and financial stability.",
                        "King of Pentacles",
                        false,
                        "The King of Pentacles represents mastery in the material world and prosperity.",
                        "Pentacles",
                        "King Of Pentacles",
                        "Corruption, materialistic, exploitative.");

        // Swords
        // Ace of Swords
        Card aceOfSwords = new Card(
                        "The Ace of Swords symbolizes clarity, truth, and mental breakthroughs.",
                        "Ace of Swords",
                        false,
                        "The Ace of Swords represents new ideas, mental clarity, and cutting through confusion.",
                        "Swords",
                        "Ace Of Swords",
                        "Confusion, misuse of power, lack of clarity.");

        // Two of Swords
        Card twoOfSwords = new Card(
                        "The Two of Swords signifies difficult decisions, stalemate, and avoidance.",
                        "Two of Swords",
                        false,
                        "The Two of Swords suggests the need to confront challenges and make choices.",
                        "Swords",
                        "Two Of Swords",
                        "Indecision, lesser of two evils, overwhelmed.");

        // Three of Swords
        Card threeOfSwords = new Card(
                        "The Three of Swords represents heartbreak, sorrow, and emotional pain.",
                        "Three of Swords",
                        false,
                        "The Three of Swords signifies grief, loss, and facing painful truths.",
                        "Swords",
                        "Three Of Swords",
                        "Healing, reconciliation, moving past hurt.");

        // Four of Swords
        Card fourOfSwords = new Card(
                        "The Four of Swords symbolizes rest, recovery, and contemplation.",
                        "Four of Swords",
                        false,
                        "The Four of Swords suggests taking a break and finding peace of mind.",
                        "Swords",
                        "Four Of Swords",
                        "Restlessness, burnout, stagnation.");

        // Five of Swords
        Card fiveOfSwords = new Card(
                        "The Five of Swords represents conflict, betrayal, and defeat.",
                        "Five of Swords",
                        false,
                        "The Five of Swords signifies winning at all costs and the aftermath of conflict.",
                        "Swords",
                        "Five Of Swords",
                        "Reconciliation, making amends, past resentment.");

        // Six of Swords
        Card sixOfSwords = new Card(
                        "The Six of Swords symbolizes transition, moving on, and leaving the past behind.",
                        "Six of Swords",
                        false,
                        "The Six of Swords suggests a journey toward calmer waters and better times.",
                        "Swords",
                        "Six Of Swords",
                        "Stagnation, refusal to move on, baggage.");

        // Seven of Swords
        Card sevenOfSwords = new Card(
                        "The Seven of Swords represents deception, betrayal, and avoidance.",
                        "Seven of Swords",
                        false,
                        "The Seven of Swords suggests being wary of deception and dishonesty.",
                        "Swords",
                        "Seven Of Swords",
                        "Honest, coming clean, advice against deceit.");

        // Eight of Swords
        Card eightOfSwords = new Card(
                        "The Eight of Swords symbolizes restriction, isolation, and feeling trapped.",
                        "Eight of Swords",
                        false,
                        "The Eight of Swords suggests overcoming obstacles and finding freedom.",
                        "Swords",
                        "Eight Of Swords",
                        "Self-release, freedom, finding a way out.");

        // Nine of Swords
        Card nineOfSwords = new Card(
                        "The Nine of Swords represents anxiety, fear, and nightmares.",
                        "Nine of Swords",
                        false,
                        "The Nine of Swords signifies worries and mental anguish.",
                        "Swords",
                        "Nine Of Swords",
                        "Hope, overcoming anxiety, confronting fears.");

        // Ten of Swords
        Card tenOfSwords = new Card(
                        "The Ten of Swords symbolizes betrayal, loss, and rock bottom.",
                        "Ten of Swords",
                        false,
                        "The Ten of Swords suggests the end of a difficult situation and the dawn of a new beginning.",
                        "Swords",
                        "Ten Of Swords",
                        "Recovery, resurrection, inevitable end.");

        // Page of Swords
        Card pageOfSwords = new Card(
                        "The Page of Swords represents curiosity, vigilance, and intellectual pursuits.",
                        "Page of Swords",
                        false,
                        "The Page of Swords suggests seeking truth and embracing new ideas.",
                        "Swords",
                        "Page Of Swords",
                        "Deception, gossip, all talk.");

        // Knight of Swords
        Card knightOfSwords = new Card(
                        "The Knight of Swords symbolizes action, ambition, and decisiveness.",
                        "Knight of Swords",
                        false,
                        "The Knight of Swords suggests charging forward with determination and courage.",
                        "Swords",
                        "Knight Of Swords",
                        "Rashness, scattered thoughts, haste.");

        // Queen of Swords
        Card queenOfSwords = new Card(
                        "The Queen of Swords represents independence, intelligence, and clear communication.",
                        "Queen of Swords",
                        false,
                        "The Queen of Swords embodies wisdom and the power of discernment.",
                        "Swords",
                        "Queen Of Swords",
                        "Bitterness, cold-hearted, sharp-tongued.");

        // King of Swords
        Card kingOfSwords = new Card(
                        "The King of Swords symbolizes authority, intellect, and logical thinking.",
                        "King of Swords",
                        false,
                        "The King of Swords represents wise decision-making and mental clarity.",
                        "Swords",
                        "King Of Swords",
                        "Bitterness, cold-hearted, sharp-tongued.");

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

package com.mercy.tarot.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercy.tarot.dto.CardNamesRequest;
import com.mercy.tarot.models.TarotStoryElements;
import com.mercy.tarot.repositories.TarotStoryElementRespository;

@RestController
@RequestMapping("/api")
public class StoryController {

        // Logger
        private static final Logger logger = LoggerFactory.getLogger(StoryController.class);

        private final TarotStoryElementRespository storyElementRpository;
        private Object storyElementsRepository;

        @Autowired
        public StoryController(TarotStoryElementRespository storyElementRespository) {
                this.storyElementRpository = storyElementRespository;
        }

        @PostMapping("/getRandomKeyword")
        public ResponseEntity<String> getRandomKeyword(@RequestBody CardNamesRequest request) {
                // Query the database to find TarotStoryElement objects based on card names
                List<TarotStoryElements> storyElements = this.storyElementRpository.findAll();
                System.out.println(storyElements.toString());
                // Extract keywords from TarotStoryElement objects and combine into a list
                List<String> allKeywords = storyElements.stream()
                                .flatMap((TarotStoryElements element) -> Arrays.stream(element.getKeywords()))
                                .collect(Collectors.toList());
                // Randomly select one keyword
                Random random = new Random();
                int randomIndex = random.nextInt(allKeywords.size());

                String randomKeyword = allKeywords.get(randomIndex);

                return ResponseEntity.ok(randomKeyword);
        }

        @PostMapping("/getStoryDTO")
        public ResponseEntity<Map<String, Object>> getStoryPrompt(@RequestBody CardNamesRequest request) {
                try {
                        // Declare cardNames
                        String cardNames = request.getCardNames();

                        // Split the card names string into a list of card names
                        List<String> cardNameList = Arrays.stream(cardNames.split(","))
                                        .map(String::trim)
                                        .filter(name -> !name.isEmpty())
                                        .collect(Collectors.toList());
                        logger.info("Parsed card names: {}", cardNameList);

                        // Query the database to find TarotStoryElement objects based on card names
                        List<TarotStoryElements> storyElements = this.storyElementRpository.findAll();
                        logger.info("Retrieved {} story elements from database", storyElements.size());

                        // Create collections to hold story elements with their sources
                        List<StoryElementWithSource> allKeywords = new ArrayList<>();
                        List<StoryElementWithSource> allMainCharacterTraits = new ArrayList<>();
                        List<StoryElementWithSource> allMainCharacterDeficits = new ArrayList<>();
                        List<StoryElementWithSource> allMainCharacterGoals = new ArrayList<>();
                        List<StoryElementWithSource> allCallToActions = new ArrayList<>();
                        List<StoryElementWithSource> allAllyTraits = new ArrayList<>();
                        List<StoryElementWithSource> allAllyDeficits = new ArrayList<>();
                        List<StoryElementWithSource> allAllyGoals = new ArrayList<>();
                        List<StoryElementWithSource> allEnemyTraits = new ArrayList<>();
                        List<StoryElementWithSource> allEnemyDeficits = new ArrayList<>();
                        List<StoryElementWithSource> allEnemyGoals = new ArrayList<>();
                        List<StoryElementWithSource> allLocations = new ArrayList<>();
                        List<StoryElementWithSource> allPointsOfView = new ArrayList<>();
                        List<StoryElementWithSource> allMoralValues = new ArrayList<>();
                        List<StoryElementWithSource> allClimaxEvents = new ArrayList<>();
                        List<StoryElementWithSource> allClimaxLocations = new ArrayList<>();
                        List<StoryElementWithSource> allClimaxDescriptions = new ArrayList<>();
                        List<StoryElementWithSource> allThemes = new ArrayList<>();
                        List<StoryElementWithSource> allStyles = new ArrayList<>();

                        // Log any null cardName entries
                        long nullCardNameCount = storyElements.stream()
                                        .filter(element -> element.getCardName() == null)
                                        .count();

                        if (nullCardNameCount > 0) {
                                logger.warn("Found {} story elements with null card names in database",
                                                nullCardNameCount);
                        }

                        // Process each story element from database
                        for (TarotStoryElements element : storyElements) {
                                // CRITICAL: Check for null cardName before using it
                                if (element.getCardName() == null) {
                                        logger.warn("Skipping element with null card name, title: {}",
                                                        element.getTitle() != null ? element.getTitle() : "unknown");
                                        continue; // Skip this element and go to the next one
                                }

                                // Cast to String since getCardName() returns Object
                                String elementCardName = (String) element.getCardName();

                                // Check if this element's card is in our request
                                for (String requestedCard : cardNameList) {
                                        if (elementCardName.equalsIgnoreCase(requestedCard.trim())) {
                                                logger.info("Processing story elements for card: {}", elementCardName);

                                                // Add all elements from this card to our collections with source
                                                // tracking
                                                if (element.keywords != null) {
                                                        addElementsWithSource(allKeywords, element.keywords,
                                                                        elementCardName);
                                                }
                                                if (element.main_character_descriptors != null) {
                                                        addElementsWithSource(allMainCharacterTraits,
                                                                        element.main_character_descriptors,
                                                                        elementCardName);
                                                }
                                                if (element.main_character_defecits != null) {
                                                        addElementsWithSource(allMainCharacterDeficits,
                                                                        element.main_character_defecits,
                                                                        elementCardName);
                                                }
                                                if (element.main_character_goals != null) {
                                                        addElementsWithSource(allMainCharacterGoals,
                                                                        element.main_character_goals, elementCardName);
                                                }
                                                if (element.call_to_action != null) {
                                                        addElementsWithSource(allCallToActions, element.call_to_action,
                                                                        elementCardName);
                                                }
                                                if (element.ally_descriptors != null) {
                                                        addElementsWithSource(allAllyTraits, element.ally_descriptors,
                                                                        elementCardName);
                                                }
                                                if (element.ally_defecits != null) {
                                                        addElementsWithSource(allAllyDeficits, element.ally_defecits,
                                                                        elementCardName);
                                                }
                                                if (element.ally_goals != null) {
                                                        addElementsWithSource(allAllyGoals, element.ally_goals,
                                                                        elementCardName);
                                                }
                                                if (element.enemy_descriptors != null) {
                                                        addElementsWithSource(allEnemyTraits, element.enemy_descriptors,
                                                                        elementCardName);
                                                }
                                                if (element.enemy_defecits != null) {
                                                        addElementsWithSource(allEnemyDeficits, element.enemy_defecits,
                                                                        elementCardName);
                                                }
                                                if (element.enemy_goals != null) {
                                                        addElementsWithSource(allEnemyGoals, element.enemy_goals,
                                                                        elementCardName);
                                                }
                                                if (element.locations != null) {
                                                        addElementsWithSource(allLocations, element.locations,
                                                                        elementCardName);
                                                }
                                                if (element.point_of_view != null) {
                                                        addElementsWithSource(allPointsOfView, element.point_of_view,
                                                                        elementCardName);
                                                }
                                                if (element.moral_value != null) {
                                                        addElementsWithSource(allMoralValues, element.moral_value,
                                                                        elementCardName);
                                                }
                                                if (element.climax_event != null) {
                                                        addElementsWithSource(allClimaxEvents, element.climax_event,
                                                                        elementCardName);
                                                }
                                                if (element.climax_location != null) {
                                                        addElementsWithSource(allClimaxLocations,
                                                                        element.climax_location, elementCardName);
                                                }
                                                if (element.climax_description != null) {
                                                        addElementsWithSource(allClimaxDescriptions,
                                                                        element.climax_description, elementCardName);
                                                }
                                                if (element.theme != null) {
                                                        addElementsWithSource(allThemes, element.theme,
                                                                        elementCardName);
                                                }
                                                if (element.style != null) {
                                                        addElementsWithSource(allStyles, element.style,
                                                                        elementCardName);
                                                }

                                                break; // Found match, move to next element
                                        }
                                }
                        }

                        // Now randomly select one item from each collection
                        Random random = new Random();
                        Map<String, Object> storyPrompt = new HashMap<>();

                        storyPrompt.put("keyword", getRandomElementWithSource(allKeywords, random));
                        storyPrompt.put("mainCharacterTrait",
                                        getRandomElementWithSource(allMainCharacterTraits, random));
                        storyPrompt.put("mainCharacterDeficit",
                                        getRandomElementWithSource(allMainCharacterDeficits, random));
                        storyPrompt.put("mainCharacterGoal", getRandomElementWithSource(allMainCharacterGoals, random));
                        storyPrompt.put("callToAction", getRandomElementWithSource(allCallToActions, random));
                        storyPrompt.put("allyTrait", getRandomElementWithSource(allAllyTraits, random));
                        storyPrompt.put("allyDeficit", getRandomElementWithSource(allAllyDeficits, random));
                        storyPrompt.put("allyGoal", getRandomElementWithSource(allAllyGoals, random));
                        storyPrompt.put("enemyTrait", getRandomElementWithSource(allEnemyTraits, random));
                        storyPrompt.put("enemyDeficit", getRandomElementWithSource(allEnemyDeficits, random));
                        storyPrompt.put("enemyGoal", getRandomElementWithSource(allEnemyGoals, random));
                        storyPrompt.put("location", getRandomElementWithSource(allLocations, random));
                        storyPrompt.put("pointOfView", getRandomElementWithSource(allPointsOfView, random));
                        storyPrompt.put("moralValue", getRandomElementWithSource(allMoralValues, random));
                        storyPrompt.put("climaxEvent", getRandomElementWithSource(allClimaxEvents, random));
                        storyPrompt.put("climaxLocation", getRandomElementWithSource(allClimaxLocations, random));
                        storyPrompt.put("climaxDescription", getRandomElementWithSource(allClimaxDescriptions, random));
                        storyPrompt.put("theme", getRandomElementWithSource(allThemes, random));
                        storyPrompt.put("style", getRandomElementWithSource(allStyles, random));

                        // Add metadata
                        storyPrompt.put("cardsUsed", cardNameList);
                        storyPrompt.put("totalElementsFound", allKeywords.size() + allMainCharacterTraits.size() +
                                        allMainCharacterDeficits.size() + allMainCharacterGoals.size());

                        logger.info("Generated story prompt with {} elements", storyPrompt.size());
                        return ResponseEntity.ok(storyPrompt);

                } catch (Exception e) {
                        logger.error("Error generating story prompt: {}", e.getMessage(), e);
                        return ResponseEntity.status(500).body(Map.of("error", "Failed to generate story prompt"));
                }
        }

        // Helper method to add elements with source tracking
        private void addElementsWithSource(List<StoryElementWithSource> targetList, String elementsString,
                        String source) {
                Arrays.stream(elementsString.split(", "))
                                .map(String::trim)
                                .filter(element -> !element.isEmpty())
                                .forEach(element -> targetList.add(new StoryElementWithSource(element, source)));
        }

        // Helper method to get random element with source information
        private Map<String, String> getRandomElementWithSource(List<StoryElementWithSource> list, Random random) {
                if (list.isEmpty()) {
                        return Map.of(
                                        "storyElement", "No elements available",
                                        "source", "Unknown");
                }

                StoryElementWithSource selected = list.get(random.nextInt(list.size()));
                return Map.of(
                                "storyElement", selected.getElement(),
                                "source", selected.getSource());
        }

        // Inner class to hold story element with its source
        private static class StoryElementWithSource {
                private final String element;
                private final String source;

                public StoryElementWithSource(String element, String source) {
                        this.element = element;
                        this.source = source;
                }

                public String getElement() {
                        return element;
                }

                public String getSource() {
                        return source;
                }
        }
        // @PostMapping("/create-story-element")
        // public ResponseEntity<TarotStoryElements> createStoryElement(@RequestBody
        // String cardNames) {
        // // Split the comma-separated string into an array of card names
        // String[] cardNameArray = cardNames.split(",");

        // // Create a new TarotStoryElements object
        // TarotStoryElements element = new TarotStoryElements();
        // System.out.println(cardNameArray.toString());
        // // Iterate over the card names and set the name field of the element
        // for (String cardName : cardNameArray) {
        // // element.setName(cardName);
        // // Perform any other operations you need to set other fields of the element
        // }

        // // Save the element to the database (assuming you have a repository)
        // TarotStoryElements savedElement = storyElementRepository.save(element);

        // return ResponseEntity.ok(savedElement);
        // }

        @PostMapping("/retrieve")
        public ResponseEntity<TarotStoryElements> retrieveByNamne(@RequestBody TarotStoryElements element) {
                TarotStoryElements foundElement = storyElementRpository.findByTitle(element.getName());
                if (foundElement == null) {
                        return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(foundElement);
        }

        @PostMapping("/find-by-name")
        public ResponseEntity<TarotStoryElements> findByName(@RequestBody TarotStoryElements element) {
                TarotStoryElements foundElement = storyElementRpository.findByTitle(element.getName());
                if (foundElement == null) {
                        return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(foundElement);
        }

        // @Query("SELECT e FROM TarotStoryElements e WHERE e.cardName IN :cardNames")
        // List<TarotStoryElements> findByCardNameInArray(@Param("cardNames")
        // List<String> cardNames) {
        // return (List<TarotStoryElements>)
        // storyElementRpository.findCardByNameIn(cardNames);
        // }

        // @Query("SELECT e FROM TarotStoryElements e WHERE e.cardName IN :cardNames")
        // List<TarotStoryElements> findByCardNameIn(@Param("cardNames") String
        // cardNames) {
        // return (List<TarotStoryElements>)
        // storyElementRpository.findCardByNameIn(cardNames);
        // }

        // @Query("SELECT e FROM TarotStoryElements e WHERE e.cardName IN :cardNames")
        // List<TarotStoryElements> findByCardNameInStringQuery(@Param("cardNames")
        // String cardNames) {
        // return (List<TarotStoryElements>)
        // storyElementRpository.findCardByNameInString(cardNames);
        // }

        @Query("SELECT * FROM TarotStoryElements WHERE LOWER(:title) = LOWER(:title)")
        public TarotStoryElements retrieveByName(@Param("title") String title) {
                // Assuming you have a method to fetch TarotStoryElements by title from the
                // database
                TarotStoryElements foundElement = ((TarotStoryElementRespository) this.storyElementsRepository)
                                .findByTitleIgnoreCase(title);
                return foundElement;
        }

        // @PostMapping("/findByCardNameInString2")
        // public Iterable<TarotStoryElements> findByCardNameInString(String cardNames)
        // {
        // System.out.println(cardNames);

        // List<String> cardNameList = Arrays.asList(cardNames.split(", ")); // Split
        // the cardNames string into a
        // // list of card names
        // List<TarotStoryElements> matchingElements = new ArrayList<>();

        // Iterable<TarotStoryElements> allElements = storyElementRpository.findAll();

        // System.out.println(allElements.toString());

        // for (TarotStoryElements entity : allElements) {
        // // Perform operations on each entity
        // System.out.println(entity.toString());
        // if (cardNameList.contains(entity.getCardName())) {
        // matchingElements.add(entity);
        // }
        // }

        // return matchingElements;
        // }

        @PostMapping("/findByCardNameIn")
        public Iterable<TarotStoryElements> findByCardNameIn(String card_names) {
                System.out.println(card_names);

                List<String> cardNameList = Arrays.asList(card_names.split(", ")); // Split the cardNames string into a
                                                                                   // list of card names
                List<TarotStoryElements> matchingElements = new ArrayList<>();

                Iterable<TarotStoryElements> allElements = storyElementRpository.findAll();

                System.out.println(allElements.toString());

                for (TarotStoryElements entity : allElements) {
                        // Perform operations on each entity
                        System.out.println(entity.toString());
                        if (cardNameList.contains(entity.getCardName())) {
                                matchingElements.add(entity);
                        }
                }

                return matchingElements;
        }

        public TarotStoryElements findByName(String card_name) {
                System.out.println(card_name);
                TarotStoryElements card = storyElementRpository.findByTitle(card_name);
                System.out.println(card);
                return card;
        }
}

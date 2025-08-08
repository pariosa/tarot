package com.mercy.tarot.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercy.tarot.dto.CardNamesRequest;
import com.mercy.tarot.models.TarotStoryElements;
import com.mercy.tarot.repositories.TarotStoryElementRespository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class StoryController {

        private final TarotStoryElementRespository storyElementRpository;
        private Object storyElementsRepository;

        @Autowired
        public StoryController(TarotStoryElementRespository storyElementRespository) {
                this.storyElementRpository = storyElementRespository;
                log.info("StoryController initialized with repository: {}",
                                storyElementRespository.getClass().getSimpleName());
        }

        // ========== FREE USER "TASTE" ENDPOINTS ==========

        @GetMapping("/story/location")
        public ResponseEntity<Map<String, String>> getRandomLocation() {
                log.info("Random location requested (free user endpoint)");
                return getRandomStoryElement("location", this::extractLocations);
        }

        @GetMapping("/story/character-trait")
        public ResponseEntity<Map<String, String>> getRandomCharacterTrait() {
                log.info("Random character trait requested (free user endpoint)");
                return getRandomStoryElement("characterTrait", this::extractMainCharacterTraits);
        }

        @GetMapping("/story/theme")
        public ResponseEntity<Map<String, String>> getRandomTheme() {
                log.info("Random theme requested (free user endpoint)");
                return getRandomStoryElement("theme", this::extractThemes);
        }

        @GetMapping("/story/keyword")
        public ResponseEntity<Map<String, String>> getRandomKeywordEndpoint() {
                log.info("Random keyword requested (free user endpoint)");
                return getRandomStoryElement("keyword", this::extractKeywords);
        }

        @GetMapping("/story/moral-value")
        public ResponseEntity<Map<String, String>> getRandomMoralValue() {
                log.info("Random moral value requested (free user endpoint)");
                return getRandomStoryElement("moralValue", this::extractMoralValues);
        }

        @GetMapping("/story/point-of-view")
        public ResponseEntity<Map<String, String>> getRandomPointOfView() {
                log.info("Random point of view requested (free user endpoint)");
                return getRandomStoryElement("pointOfView", this::extractPointsOfView);
        }

        @GetMapping("/story/style")
        public ResponseEntity<Map<String, String>> getRandomStyle() {
                log.info("Random style requested (free user endpoint)");
                return getRandomStoryElement("style", this::extractStyles);
        }

        @GetMapping("/story/climax-event")
        public ResponseEntity<Map<String, String>> getRandomClimaxEvent() {
                log.info("Random climax event requested (free user endpoint)");
                return getRandomStoryElement("climaxEvent", this::extractClimaxEvents);
        }

        // ========== HELPER METHOD FOR INDIVIDUAL ELEMENTS ==========

        private ResponseEntity<Map<String, String>> getRandomStoryElement(String elementType,
                        ElementExtractor extractor) {
                try {
                        List<TarotStoryElements> allStoryElements = storyElementRpository.findAll();
                        log.debug("Retrieved {} story elements for {} extraction",
                                        allStoryElements.size(), elementType);

                        List<StoryElementWithSource> elements = new ArrayList<>();

                        for (TarotStoryElements element : allStoryElements) {
                                if (element.getCardName() == null) {
                                        continue;
                                }
                                String cardName = (String) element.getCardName();
                                extractor.extract(element, elements, cardName);
                        }

                        if (elements.isEmpty()) {
                                log.warn("No {} elements found in database", elementType);
                                return ResponseEntity.ok(Map.of(
                                                "element", "No " + elementType + " available",
                                                "source", "Unknown",
                                                "type", elementType));
                        }

                        // Use ThreadLocalRandom for better performance
                        StoryElementWithSource selected = elements.get(
                                        ThreadLocalRandom.current().nextInt(elements.size()));

                        Map<String, String> response = Map.of(
                                        "element", selected.getElement(),
                                        "source", selected.getSource(),
                                        "type", elementType);

                        log.debug("Selected {} '{}' from card '{}'", elementType,
                                        selected.getElement(), selected.getSource());
                        return ResponseEntity.ok(response);

                } catch (Exception e) {
                        log.error("Error getting random {}: {}", elementType, e.getMessage(), e);
                        return ResponseEntity.status(500).body(Map.of(
                                        "error", "Failed to get random " + elementType,
                                        "type", elementType));
                }
        }

        // ========== ELEMENT EXTRACTORS ==========

        @FunctionalInterface
        private interface ElementExtractor {
                void extract(TarotStoryElements element, List<StoryElementWithSource> targetList, String cardName);
        }

        private void extractLocations(TarotStoryElements element, List<StoryElementWithSource> targetList,
                        String cardName) {
                if (element.locations != null) {
                        addElementsWithSource(targetList, element.locations, cardName);
                }
        }

        private void extractMainCharacterTraits(TarotStoryElements element, List<StoryElementWithSource> targetList,
                        String cardName) {
                if (element.main_character_descriptors != null) {
                        addElementsWithSource(targetList, element.main_character_descriptors, cardName);
                }
        }

        private void extractThemes(TarotStoryElements element, List<StoryElementWithSource> targetList,
                        String cardName) {
                if (element.theme != null) {
                        addElementsWithSource(targetList, element.theme, cardName);
                }
        }

        private void extractKeywords(TarotStoryElements element, List<StoryElementWithSource> targetList,
                        String cardName) {
                if (element.keywords != null) {
                        addElementsWithSource(targetList, element.keywords, cardName);
                }
        }

        private void extractMoralValues(TarotStoryElements element, List<StoryElementWithSource> targetList,
                        String cardName) {
                if (element.moral_value != null) {
                        addElementsWithSource(targetList, element.moral_value, cardName);
                }
        }

        private void extractPointsOfView(TarotStoryElements element, List<StoryElementWithSource> targetList,
                        String cardName) {
                if (element.point_of_view != null) {
                        addElementsWithSource(targetList, element.point_of_view, cardName);
                }
        }

        private void extractStyles(TarotStoryElements element, List<StoryElementWithSource> targetList,
                        String cardName) {
                if (element.style != null) {
                        addElementsWithSource(targetList, element.style, cardName);
                }
        }

        private void extractClimaxEvents(TarotStoryElements element, List<StoryElementWithSource> targetList,
                        String cardName) {
                if (element.climax_event != null) {
                        addElementsWithSource(targetList, element.climax_event, cardName);
                }
        }

        // ========== EXISTING ENDPOINTS (IMPROVED) ==========

        @PostMapping("/getRandomKeyword")
        public ResponseEntity<String> getRandomKeyword(@RequestBody CardNamesRequest request) {
                log.info("Legacy random keyword endpoint called");
                // Query the database to find TarotStoryElement objects based on card names
                List<TarotStoryElements> storyElements = this.storyElementRpository.findAll();
                log.debug("Retrieved {} story elements for keyword extraction", storyElements.size());

                // Extract keywords from TarotStoryElement objects and combine into a list
                List<String> allKeywords = storyElements.stream()
                                .flatMap((TarotStoryElements element) -> Arrays.stream(element.getKeywords()))
                                .collect(Collectors.toList());

                if (allKeywords.isEmpty()) {
                        log.warn("No keywords found in database");
                        return ResponseEntity.ok("No keywords available");
                }

                // Use ThreadLocalRandom for better performance
                String randomKeyword = allKeywords.get(ThreadLocalRandom.current().nextInt(allKeywords.size()));
                log.debug("Selected random keyword: {}", randomKeyword);

                return ResponseEntity.ok(randomKeyword);
        }

        @PostMapping("/getStoryDTO")
        public ResponseEntity<Map<String, Object>> getStoryPrompt(@RequestBody CardNamesRequest request) {
                try {
                        // Declare cardNames
                        String cardNames = request.getCardNames();
                        log.info("Story prompt requested for cards: {}", cardNames);

                        // Split the card names string into a list of card names
                        List<String> cardNameList = Arrays.stream(cardNames.split(","))
                                        .map(String::trim)
                                        .filter(name -> !name.isEmpty())
                                        .collect(Collectors.toList());
                        log.debug("Parsed card names: {}", cardNameList);

                        // Query the database to find TarotStoryElement objects based on card names
                        List<TarotStoryElements> storyElements = this.storyElementRpository.findAll();
                        log.debug("Retrieved {} story elements from database", storyElements.size());

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
                                log.warn("Found {} story elements with null card names in database",
                                                nullCardNameCount);
                        }

                        int matchedCards = 0;

                        // Process each story element from database
                        for (TarotStoryElements element : storyElements) {
                                // CRITICAL: Check for null cardName before using it
                                if (element.getCardName() == null) {
                                        log.trace("Skipping element with null card name, title: {}",
                                                        element.getTitle() != null ? element.getTitle() : "unknown");
                                        continue; // Skip this element and go to the next one
                                }

                                // Cast to String since getCardName() returns Object
                                String elementCardName = (String) element.getCardName();

                                // Check if this element's card is in our request
                                for (String requestedCard : cardNameList) {
                                        if (elementCardName.equalsIgnoreCase(requestedCard.trim())) {
                                                log.debug("Processing story elements for card: {}", elementCardName);
                                                matchedCards++;

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

                        log.info("Matched {} cards from request, generating story elements", matchedCards);

                        // Now randomly select one item from each collection using ThreadLocalRandom
                        Random random = ThreadLocalRandom.current();
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
                        storyPrompt.put("cardsMatched", matchedCards);
                        storyPrompt.put("totalElementsFound", allKeywords.size() + allMainCharacterTraits.size() +
                                        allMainCharacterDeficits.size() + allMainCharacterGoals.size());

                        log.info("Generated complete story prompt with {} elements for {} matched cards",
                                        storyPrompt.size() - 3, matchedCards); // -3 for metadata
                        return ResponseEntity.ok(storyPrompt);

                } catch (Exception e) {
                        log.error("Error generating story prompt: {}", e.getMessage(), e);
                        return ResponseEntity.status(500).body(Map.of("error", "Failed to generate story prompt"));
                }
        }

        // ========== HELPER METHODS ==========

        // Helper method to add elements with source tracking
        private void addElementsWithSource(List<StoryElementWithSource> targetList, String elementsString,
                        String source) {
                if (elementsString == null || elementsString.trim().isEmpty()) {
                        return;
                }

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

        // ========== EXISTING METHODS (KEPT FOR COMPATIBILITY) ==========

        @PostMapping("/retrieve")
        public ResponseEntity<TarotStoryElements> retrieveByNamne(@RequestBody TarotStoryElements element) {
                log.debug("Retrieve by name requested for: {}", element.getName());
                TarotStoryElements foundElement = storyElementRpository.findByTitle(element.getName());
                if (foundElement == null) {
                        log.warn("Story element not found with name: {}", element.getName());
                        return ResponseEntity.notFound().build();
                }
                log.debug("Found story element: {}", foundElement.getTitle());
                return ResponseEntity.ok(foundElement);
        }

        @PostMapping("/find-by-name")
        public ResponseEntity<TarotStoryElements> findByName(@RequestBody TarotStoryElements element) {
                log.debug("Find by name requested for: {}", element.getName());
                TarotStoryElements foundElement = storyElementRpository.findByTitle(element.getName());
                if (foundElement == null) {
                        log.warn("Story element not found with name: {}", element.getName());
                        return ResponseEntity.notFound().build();
                }
                log.debug("Found story element: {}", foundElement.getTitle());
                return ResponseEntity.ok(foundElement);
        }

        @Query("SELECT * FROM TarotStoryElements WHERE LOWER(:title) = LOWER(:title)")
        public TarotStoryElements retrieveByName(@Param("title") String title) {
                log.debug("Retrieve by name query for title: {}", title);
                // Assuming you have a method to fetch TarotStoryElements by title from the
                // database
                TarotStoryElements foundElement = ((TarotStoryElementRespository) this.storyElementsRepository)
                                .findByTitleIgnoreCase(title);
                return foundElement;
        }

        @PostMapping("/findByCardNameIn")
        public Iterable<TarotStoryElements> findByCardNameIn(String card_names) {
                log.debug("Find by card names requested: {}", card_names);

                List<String> cardNameList = Arrays.asList(card_names.split(", ")); // Split the cardNames string into a
                                                                                   // list of card names
                List<TarotStoryElements> matchingElements = new ArrayList<>();

                Iterable<TarotStoryElements> allElements = storyElementRpository.findAll();

                for (TarotStoryElements entity : allElements) {
                        if (cardNameList.contains(entity.getCardName())) {
                                matchingElements.add(entity);
                        }
                }

                log.debug("Found {} matching elements for card names: {}", matchingElements.size(), card_names);
                return matchingElements;
        }

        public TarotStoryElements findByName(String card_name) {
                log.debug("Find by name method called for: {}", card_name);
                TarotStoryElements card = storyElementRpository.findByTitle(card_name);
                return card;
        }
}
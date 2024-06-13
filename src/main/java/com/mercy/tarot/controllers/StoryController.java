package com.mercy.tarot.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercy.tarot.models.TarotStoryElements;
import com.mercy.tarot.repositories.TarotStoryElementRespository;

@RestController
@RequestMapping("/api")
public class StoryController {

        //
        private final TarotStoryElementRespository storyElementRpository;
        private Object storyElementsRepository;

        @Autowired
        public StoryController(TarotStoryElementRespository storyElementRespository) {
                this.storyElementRpository = storyElementRespository;
        }

        @PostMapping("/getRandomKeyword")
        public ResponseEntity<String> getRandomKeyword(@RequestBody String cardNames) {
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
        public ResponseEntity<String> getStoryPrompt(@RequestBody String cardNames) {
                List<TiedStoryElements> keywordsTied = new ArrayList<>();
                List<TiedStoryElements> mainCharacterTraitsTied = new ArrayList<>();
                List<TiedStoryElements> mainCharacterDefecitsTied = new ArrayList<>();
                List<TiedStoryElements> mainCharacterGoalsTied = new ArrayList<>();
                List<TiedStoryElements> callToActionTied = new ArrayList<>();
                List<TiedStoryElements> allyTraitsTied = new ArrayList<>();
                List<TiedStoryElements> allyDefecitsTied = new ArrayList<>();
                List<TiedStoryElements> allyGoalsTied = new ArrayList<>();
                List<TiedStoryElements> enemyTraitsTied = new ArrayList<>();
                List<TiedStoryElements> enemyDefecitsTied = new ArrayList<>();
                List<TiedStoryElements> enemyGoalsTied = new ArrayList<>();
                List<TiedStoryElements> locationsTied = new ArrayList<>();
                List<TiedStoryElements> pointOfViewTied = new ArrayList<>();
                List<TiedStoryElements> moralValueTied = new ArrayList<>();
                List<TiedStoryElements> climaxEventTied = new ArrayList<>();
                List<TiedStoryElements> climaxLocationTied = new ArrayList<>();
                List<TiedStoryElements> climaxDescriptionTied = new ArrayList<>();
                List<TiedStoryElements> themeTied = new ArrayList<>();
                List<TiedStoryElements> styleTied = new ArrayList<>();

                // Split the card names string into a list of card names
                List<String> cardNameList = Arrays.asList(cardNames.split(", "));

                // Query the database to find TarotStoryElement objects based on card names
                // List<TarotStoryElements> storyElements =
                // this.storyElementRpository.findByCardNameIn(cardNames);

                List<TarotStoryElements> storyElements = this.storyElementRpository.findAll();

                List<TiedStoryElements> newObjectList = new ArrayList<>();
                for (TarotStoryElements element : storyElements) {
                        for (String cardName : cardNameList) {
                                if (element.getCardName().equals(cardName)) {
                                        Arrays.stream(element.main_character_goals.split(", "))
                                                        .map(goal -> new TiedStoryElements(goal, cardName))
                                                        .forEach(mainCharacterGoalsTied::add);
                                        Arrays.stream(element.main_character_defecits.split(", "))
                                                        .map(defecit -> new TiedStoryElements(defecit, cardName))
                                                        .forEach(mainCharacterDefecitsTied::add);
                                        Arrays.stream(element.main_character_descriptors.split(", "))
                                                        .map(descriptor -> new TiedStoryElements(descriptor, cardName))
                                                        .forEach(mainCharacterTraitsTied::add);
                                        Arrays.stream(element.call_to_action.split(", "))
                                                        .map(call -> new TiedStoryElements(call, cardName))
                                                        .forEach(callToActionTied::add);
                                        Arrays.stream(element.ally_goals.split(", "))
                                                        .map(goal -> new TiedStoryElements(goal, cardName))
                                                        .forEach(allyGoalsTied::add);
                                        Arrays.stream(element.ally_defecits.split(", "))
                                                        .map(defecit -> new TiedStoryElements(defecit, cardName))
                                                        .forEach(allyDefecitsTied::add);
                                        Arrays.stream(element.ally_descriptors.split(", "))
                                                        .map(descriptor -> new TiedStoryElements(descriptor, cardName))
                                                        .forEach(allyTraitsTied::add);
                                        Arrays.stream(element.enemy_goals.split(", "))
                                                        .map(goal -> new TiedStoryElements(goal, cardName))
                                                        .forEach(enemyGoalsTied::add);
                                        Arrays.stream(element.enemy_defecits.split(", "))
                                                        .map(defecit -> new TiedStoryElements(defecit, cardName))
                                                        .forEach(enemyDefecitsTied::add);
                                        Arrays.stream(element.enemy_descriptors.split(", "))
                                                        .map(descriptor -> new TiedStoryElements(descriptor, cardName))
                                                        .forEach(enemyTraitsTied::add);
                                        Arrays.stream(element.locations.split(", "))
                                                        .map(location -> new TiedStoryElements(location, cardName))
                                                        .forEach(locationsTied::add);
                                        Arrays.stream(element.point_of_view.split(", "))
                                                        .map(point -> new TiedStoryElements(point, cardName))
                                                        .forEach(pointOfViewTied::add);
                                        Arrays.stream(element.moral_value.split(", "))
                                                        .map(moral -> new TiedStoryElements(moral, cardName))
                                                        .forEach(moralValueTied::add);
                                        Arrays.stream(element.climax_event.split(", "))
                                                        .map(climax -> new TiedStoryElements(climax, cardName))
                                                        .forEach(climaxEventTied::add);
                                        Arrays.stream(element.climax_location.split(", "))
                                                        .map(climax -> new TiedStoryElements(climax, cardName))
                                                        .forEach(climaxLocationTied::add);
                                        Arrays.stream(element.climax_description.split(", "))
                                                        .map(climax -> new TiedStoryElements(climax, cardName))
                                                        .forEach(climaxDescriptionTied::add);
                                        Arrays.stream(element.theme.split(", "))
                                                        .map(theme -> new TiedStoryElements(theme, cardName))
                                                        .forEach(themeTied::add);
                                        // TiedStoryElements newObject = new TiedStoryElements();
                                        // newObject.setStoryElement(element);
                                        // newObject.setCardName(cardName);
                                        // newObjectList.add(newObject);
                                }
                        }
                }

                System.out.println(storyElements.toString());
                // Extract keywords from TarotStoryElement objects and combine into a list
                List<String> allKeywords = storyElements.stream()
                                .flatMap((TarotStoryElements element) -> Arrays.stream(element.getKeywords()))
                                .collect(Collectors.toList());
                return ResponseEntity.ok(mainCharacterDefecitsTied.toString());

                // Randomly select one keyword

                // Random random = new Random();
                // int randomIndex = random.nextInt(allKeywords.size());

                // String randomKeyword = allKeywords.get(randomIndex);

                // return ResponseEntity.ok(randomKeyword);
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

        @Query("SELECT * FROM tarotstoryelements WHERE LOWER(:title) = LOWER(:title)")
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
        public Iterable<TarotStoryElements> findByCardNameIn(String cardNames) {
                System.out.println(cardNames);

                List<String> cardNameList = Arrays.asList(cardNames.split(", ")); // Split the cardNames string into a
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

        public TarotStoryElements findByName(String cardName) {
                System.out.println(cardName);
                TarotStoryElements card = storyElementRpository.findByTitle(cardName);
                System.out.println(card);
                return card;
        }
}

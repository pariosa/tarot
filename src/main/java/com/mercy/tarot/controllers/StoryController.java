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

    @Query("SELECT * FROM tarotstoryelements WHERE LOWER(:title) = LOWER(:title)")
    public TarotStoryElements retrieveByName(@Param("title") String title) {
        // Assuming you have a method to fetch TarotStoryElements by title from the
        // database
        TarotStoryElements foundElement = ((TarotStoryElementRespository) this.storyElementsRepository)
                .findByTitleIgnoreCase(title);
        return foundElement;
    }

    @PostMapping("/findByCardNameIn")
    public List<TarotStoryElements> findByCardNameIn(String cardNames) {
        System.out.println(cardNames);
        List<String> cardNameList = Arrays.asList(cardNames.split(", "));
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

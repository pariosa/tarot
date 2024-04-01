package com.mercy.tarot.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mercy.tarot.dto.CardDTO;
import com.mercy.tarot.models.Card;
import com.mercy.tarot.repositories.CardRepository;

@RestController
@RequestMapping("/api")
public class CardController {

    private final CardRepository cardRepository;

    @Autowired
    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping("/card/{id}")
    @ResponseBody
    public ResponseEntity<Card> getCardById(@PathVariable Long id) {
        Card card = cardRepository.findById(id).orElse(null);
        if (card != null) {
            return ResponseEntity.ok(card);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public void shuffleALot(List<Card> allCards) {
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
        shuffle(allCards);
    }

    public List<CardDTO> getRandomCards(@PathVariable int count) {
        List<Card> allCards = cardRepository.findAll();
        List<CardDTO> selectedCards = new ArrayList<>();

        // Shuffle the list of cards a few times :)
        shuffleALot(allCards);
        // Select random cards
        for (int i = 0; i < count; i++) {
            Card card = allCards.get(i);
            // Simulate random orientation
            boolean reversed = new Random().nextBoolean();
            CardDTO cardDTO = new CardDTO(card.getTitle(), card.getDescription(), reversed, card.getStory());
            selectedCards.add(cardDTO);
        }

        return selectedCards;
    }

    public List<CardDTO> getRandomWeightedReversalCards(@PathVariable int count) {
        List<Card> allCards = cardRepository.findAll();
        List<CardDTO> selectedCards = new ArrayList<>();

        // Shuffle the list of cards a few times :)
        shuffleALot(allCards);

        // Select random cards
        for (int i = 0; i < count; i++) {
            Card card = allCards.get(i);
            // Simulate random orientation
            boolean reversed = CardDrawHelpers.drawCardWithWeightedProbability();
            CardDTO cardDTO = new CardDTO(card.getTitle(), card.getDescription(), reversed, card.getStory());
            selectedCards.add(cardDTO);
        }

        return selectedCards;
    }

    // Custom method to shuffle a list
    private void shuffle(List<Card> cards) {
        Random rnd = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Swap elements
            Card temp = cards.get(index);
            cards.set(index, cards.get(i));
            cards.set(i, temp);
        }
    }

    @GetMapping("/draw")
    @ResponseBody
    public List<CardDTO> draw() {
        List<CardDTO> cards = getRandomCards(1); // Fetch 3 random cards
        return cards;
    }

    @GetMapping("/draw/weighted")
    @ResponseBody
    public List<CardDTO> drawWeighted() {
        List<CardDTO> cards = getRandomWeightedReversalCards(1); // Fetch 3 random cards
        return cards;
    }

    @GetMapping("/sporead/weighted/{numCards}")
    @ResponseBody
    public List<CardDTO> drawCardWithWeightedProbability(@PathVariable int numCards) {
        List<CardDTO> cards = getRandomWeightedReversalCards(numCards); // Fetch 5 random cards
        return cards;
    }

    @GetMapping("/spread/{numCards}")
    @ResponseBody
    public List<CardDTO> getSpreadOfFive(@PathVariable int numCards) {
        List<CardDTO> cards = getRandomCards(numCards); // Fetch 5 random cards
        return cards;
    }

}

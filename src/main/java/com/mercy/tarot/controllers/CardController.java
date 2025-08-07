package com.mercy.tarot.controllers;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class CardController {

    private final CardRepository cardRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
        log.info("CardController initialized with cardRepository: {}", cardRepository.getClass().getSimpleName());
    }

    @GetMapping("/card/{id}")
    @ResponseBody
    public ResponseEntity<Card> getCardById(@PathVariable Long id) {
        log.debug("Fetching card with ID: {}", id);

        Card card = cardRepository.findById(id).orElse(null);
        if (card != null) {
            log.debug("Successfully found card: {} (ID: {})", card.getTitle(), id);
            return ResponseEntity.ok(card);
        } else {
            log.warn("Card not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Enhanced shuffle method using multithreading and cryptographically secure
     * randomness
     * Performs multiple shuffles concurrently to maximize randomness
     */
    public void shuffleALot(List<Card> allCards) {
        if (allCards.size() < 2) {
            log.debug("Skipping shuffle - insufficient cards: {}", allCards.size());
            return; // Nothing to shuffle
        }

        log.debug("Starting enhanced shuffle for {} cards", allCards.size());
        long startTime = System.currentTimeMillis();

        // Create multiple concurrent shuffle tasks
        List<CompletableFuture<Void>> shuffleTasks = new ArrayList<>();

        // Perform parallel shuffles with different algorithms
        for (int i = 0; i < 6; i++) {
            shuffleTasks.add(CompletableFuture.runAsync(() -> fisherYatesShuffle(allCards)));
        }

        for (int i = 0; i < 4; i++) {
            shuffleTasks.add(CompletableFuture.runAsync(() -> Collections.shuffle(allCards, secureRandom)));
        }

        for (int i = 0; i < 4; i++) {
            shuffleTasks.add(CompletableFuture.runAsync(() -> threadLocalRandomShuffle(allCards)));
        }

        for (int i = 0; i < 4; i++) {
            shuffleTasks.add(CompletableFuture.runAsync(() -> modernFisherYatesShuffle(allCards)));
        }

        // Wait for all shuffle operations to complete
        CompletableFuture.allOf(shuffleTasks.toArray(new CompletableFuture[0])).join();

        // Final entropy-based shuffle using system time and thread info
        entropyBasedShuffle(allCards);

        long duration = System.currentTimeMillis() - startTime;
        log.debug("Enhanced shuffle completed in {}ms for {} cards", duration, allCards.size());
    }

    /**
     * Classic Fisher-Yates shuffle algorithm
     */
    private void fisherYatesShuffle(List<Card> cards) {
        synchronized (cards) {
            log.trace("Executing Fisher-Yates shuffle for {} cards", cards.size());
            for (int i = cards.size() - 1; i > 0; i--) {
                int j = secureRandom.nextInt(i + 1);
                Collections.swap(cards, i, j);
            }
        }
    }

    /**
     * Modern Fisher-Yates shuffle using parallel stream processing
     */
    private void modernFisherYatesShuffle(List<Card> cards) {
        synchronized (cards) {
            log.trace("Executing modern Fisher-Yates shuffle for {} cards", cards.size());
            IntStream.range(0, cards.size())
                    .parallel()
                    .forEach(i -> {
                        int j = ThreadLocalRandom.current().nextInt(cards.size());
                        // Use a more complex swapping pattern
                        if (i != j && Math.abs(i - j) > 1) {
                            Collections.swap(cards, i, j);
                        }
                    });
        }
    }

    /**
     * Shuffle using ThreadLocalRandom for better performance in multithreaded
     * scenarios
     */
    private void threadLocalRandomShuffle(List<Card> cards) {
        synchronized (cards) {
            log.trace("Executing ThreadLocalRandom shuffle for {} cards", cards.size());
            for (int i = cards.size() - 1; i > 0; i--) {
                int j = ThreadLocalRandom.current().nextInt(i + 1);
                Collections.swap(cards, i, j);
            }
        }
    }

    /**
     * Entropy-based shuffle that incorporates system state for additional
     * randomness
     */
    private void entropyBasedShuffle(List<Card> cards) {
        log.trace("Executing entropy-based shuffle for {} cards", cards.size());

        // Use system entropy sources
        long systemTime = System.nanoTime();
        long threadId = Thread.currentThread().getId();
        int hashCode = cards.hashCode();

        // Create seed from multiple entropy sources
        long seed = systemTime ^ threadId ^ hashCode ^ System.currentTimeMillis();
        log.trace("Generated entropy seed: {} from systemTime: {}, threadId: {}, hashCode: {}",
                seed, systemTime, threadId, hashCode);

        SecureRandom entropyRandom = new SecureRandom();
        entropyRandom.setSeed(seed);

        // Perform multiple passes with different patterns
        for (int pass = 0; pass < 3; pass++) {
            for (int i = 0; i < cards.size(); i++) {
                // Use different selection strategies per pass
                int j = switch (pass) {
                    case 0 -> entropyRandom.nextInt(cards.size());
                    case 1 -> (i + entropyRandom.nextInt(cards.size() - 1) + 1) % cards.size();
                    default -> cards.size() - 1 - entropyRandom.nextInt(cards.size());
                };

                if (i != j) {
                    Collections.swap(cards, i, j);
                }
            }
        }
    }

    /**
     * Alternative high-performance shuffle method for when you need maximum speed
     */
    public void shuffleParallel(List<Card> allCards) {
        if (allCards.size() < 2) {
            log.debug("Skipping parallel shuffle - insufficient cards: {}", allCards.size());
            return;
        }

        log.debug("Starting parallel shuffle for {} cards", allCards.size());
        long startTime = System.currentTimeMillis();

        // Create a copy to work with multiple threads safely
        List<Card> workingCopy = new ArrayList<>(allCards);

        // Perform concurrent operations on segments
        int numThreads = Runtime.getRuntime().availableProcessors();
        int segmentSize = Math.max(1, allCards.size() / numThreads);

        log.debug("Using {} threads with segment size: {}", numThreads, segmentSize);

        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            int start = i * segmentSize;
            int end = (i == numThreads - 1) ? allCards.size() : Math.min((i + 1) * segmentSize, allCards.size());

            if (start < end) {
                final int threadIndex = i;
                tasks.add(CompletableFuture.runAsync(() -> {
                    log.trace("Thread {} shuffling segment [{}, {})", threadIndex, start, end);
                    // Shuffle segment
                    List<Card> segment = workingCopy.subList(start, end);
                    Collections.shuffle(segment, ThreadLocalRandom.current());
                }));
            }
        }

        // Wait for all segments to complete
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();

        // Final global shuffle
        Collections.shuffle(workingCopy, secureRandom);

        // Replace original list contents
        allCards.clear();
        allCards.addAll(workingCopy);

        long duration = System.currentTimeMillis() - startTime;
        log.debug("Parallel shuffle completed in {}ms for {} cards", duration, allCards.size());
    }

    public List<CardDTO> getRandomCards(@PathVariable int count) {
        log.debug("Getting {} random cards", count);

        List<Card> allCards = cardRepository.findAll();
        log.debug("Retrieved {} total cards from repository", allCards.size());

        List<CardDTO> selectedCards = new ArrayList<>();

        // Use enhanced shuffle
        shuffleALot(allCards);

        // Select random cards
        int actualCount = Math.min(count, allCards.size());
        log.debug("Selecting {} cards (requested: {}, available: {})", actualCount, count, allCards.size());

        for (int i = 0; i < actualCount; i++) {
            Card card = allCards.get(i);
            // Use ThreadLocalRandom for better performance
            boolean reversed = ThreadLocalRandom.current().nextBoolean();
            CardDTO cardDTO = new CardDTO(card.getTitle(), card.getDescription(), reversed, card.getStory(),
                    card.getValue(), card.getReversedDescription());
            selectedCards.add(cardDTO);
            log.trace("Selected card: {} (reversed: {})", card.getTitle(), reversed);
        }

        log.info("Successfully generated {} random cards", selectedCards.size());
        return selectedCards;
    }

    public List<CardDTO> getRandomWeightedReversalCards(@PathVariable int count) {
        log.debug("Getting {} random cards with weighted reversal", count);

        List<Card> allCards = cardRepository.findAll();
        log.debug("Retrieved {} total cards from repository", allCards.size());

        List<CardDTO> selectedCards = new ArrayList<>();

        // Use enhanced shuffle
        shuffleALot(allCards);

        // Select random cards
        int actualCount = Math.min(count, allCards.size());
        log.debug("Selecting {} cards with weighted reversal (requested: {}, available: {})",
                actualCount, count, allCards.size());

        for (int i = 0; i < actualCount; i++) {
            Card card = allCards.get(i);
            boolean reversed = CardDrawHelpers.drawCardWithWeightedProbability();
            CardDTO cardDTO = new CardDTO(card.getTitle(), card.getDescription(), reversed, card.getStory(),
                    card.getValue(), card.getReversedDescription());
            selectedCards.add(cardDTO);
            log.trace("Selected weighted card: {} (reversed: {})", card.getTitle(), reversed);
        }

        log.info("Successfully generated {} weighted reversal cards", selectedCards.size());
        return selectedCards;
    }

    @GetMapping("/draw")
    @ResponseBody
    public List<CardDTO> draw() {
        log.info("Single card draw requested");
        List<CardDTO> cards = getRandomCards(1);
        log.info("Single card draw completed - card: {}",
                cards.isEmpty() ? "none" : cards.get(0).getName());
        return cards;
    }

    @GetMapping("/draw/weighted")
    @ResponseBody
    public List<CardDTO> drawWeighted() {
        log.info("Single weighted card draw requested");
        List<CardDTO> cards = getRandomWeightedReversalCards(1);
        log.info("Single weighted card draw completed - card: {}",
                cards.isEmpty() ? "none" : cards.get(0).getName());
        return cards;
    }

    @GetMapping("/spread/weighted/{numCards}")
    @ResponseBody
    public List<CardDTO> drawCardWithWeightedProbability(@PathVariable int numCards) {
        log.info("Weighted card spread requested for {} cards", numCards);
        List<CardDTO> cards = getRandomWeightedReversalCards(numCards);
        log.info("Weighted card spread completed - {} cards drawn", cards.size());
        return cards;
    }

    @GetMapping("/spread/{numCards}")
    @ResponseBody
    public List<CardDTO> getSpreadOfFive(@PathVariable int numCards) {
        log.info("Card spread requested for {} cards", numCards);
        List<CardDTO> cards = getRandomCards(numCards);
        log.info("Card spread completed - {} cards drawn", cards.size());
        return cards;
    }

    /**
     * Get random cards using parallel shuffle algorithm for better performance
     */
    public List<CardDTO> getRandomCardsParallel(int count) {
        log.debug("Getting {} random cards using parallel shuffle", count);

        List<Card> allCards = cardRepository.findAll();
        log.debug("Retrieved {} total cards from repository for parallel shuffle", allCards.size());

        List<CardDTO> selectedCards = new ArrayList<>();

        // Use parallel shuffle instead of enhanced shuffle
        shuffleParallel(allCards);

        // Select random cards
        int actualCount = Math.min(count, allCards.size());
        log.debug("Selecting {} cards with parallel shuffle (requested: {}, available: {})",
                actualCount, count, allCards.size());

        for (int i = 0; i < actualCount; i++) {
            Card card = allCards.get(i);
            // Use ThreadLocalRandom for better performance
            boolean reversed = ThreadLocalRandom.current().nextBoolean();
            CardDTO cardDTO = new CardDTO(card.getTitle(), card.getDescription(), reversed, card.getStory(),
                    card.getValue(), card.getReversedDescription());
            selectedCards.add(cardDTO);
            log.trace("Selected card (parallel): {} (reversed: {})", card.getTitle(), reversed);
        }

        log.info("Successfully generated {} random cards using parallel shuffle", selectedCards.size());
        return selectedCards;
    }

    /**
     * Get random cards with weighted reversal using parallel shuffle algorithm
     */
    public List<CardDTO> getRandomWeightedReversalCardsParallel(int count) {
        log.debug("Getting {} random cards with weighted reversal using parallel shuffle", count);

        List<Card> allCards = cardRepository.findAll();
        log.debug("Retrieved {} total cards from repository for parallel weighted shuffle", allCards.size());

        List<CardDTO> selectedCards = new ArrayList<>();

        // Use parallel shuffle instead of enhanced shuffle
        shuffleParallel(allCards);

        // Select random cards
        int actualCount = Math.min(count, allCards.size());
        log.debug("Selecting {} cards with parallel weighted reversal (requested: {}, available: {})",
                actualCount, count, allCards.size());

        for (int i = 0; i < actualCount; i++) {
            Card card = allCards.get(i);
            boolean reversed = CardDrawHelpers.drawCardWithWeightedProbability();
            CardDTO cardDTO = new CardDTO(card.getTitle(), card.getDescription(), reversed, card.getStory(),
                    card.getValue(), card.getReversedDescription());
            selectedCards.add(cardDTO);
            log.trace("Selected weighted card (parallel): {} (reversed: {})", card.getTitle(), reversed);
        }

        log.info("Successfully generated {} weighted reversal cards using parallel shuffle", selectedCards.size());
        return selectedCards;
    }

    @GetMapping("/draw/parallel")
    @ResponseBody
    public List<CardDTO> drawParallel() {
        log.info("Single card draw with parallel shuffle requested");
        List<CardDTO> cards = getRandomCardsParallel(1);
        log.info("Single card draw with parallel shuffle completed - card: {}",
                cards.isEmpty() ? "none" : cards.get(0).getName());
        return cards;
    }

    @GetMapping("/draw/parallel/weighted")
    @ResponseBody
    public List<CardDTO> drawParallelWeighted() {
        log.info("Single weighted card draw with parallel shuffle requested");
        List<CardDTO> cards = getRandomWeightedReversalCardsParallel(1);
        log.info("Single weighted card draw with parallel shuffle completed - card: {}",
                cards.isEmpty() ? "none" : cards.get(0).getName());
        return cards;
    }

    @GetMapping("/spread/parallel/{numCards}")
    @ResponseBody
    public List<CardDTO> getSpreadParallel(@PathVariable int numCards) {
        log.info("Card spread with parallel shuffle requested for {} cards", numCards);
        List<CardDTO> cards = getRandomCardsParallel(numCards);
        log.info("Card spread with parallel shuffle completed - {} cards drawn", cards.size());
        return cards;
    }

    @GetMapping("/spread/parallel/weighted/{numCards}")
    @ResponseBody
    public List<CardDTO> getSpreadParallelWeighted(@PathVariable int numCards) {
        log.info("Weighted card spread with parallel shuffle requested for {} cards", numCards);
        List<CardDTO> cards = getRandomWeightedReversalCardsParallel(numCards);
        log.info("Weighted card spread with parallel shuffle completed - {} cards drawn", cards.size());
        return cards;
    }
}
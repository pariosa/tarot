package com.mercy.tarot.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardDrawHelpers {

    // Weighted probability: 70% upright, 30% reversed
    public static boolean drawCardWithWeightedProbability() {
        Random random = new Random();
        double probability = random.nextDouble(); // Generate a random number between 0 and 1
        return probability > 0.7; // Return true (upright) if probability is less than 0.7
    }

    // Sequential drawing: alternate between upright and reversed
    public static List<Boolean> drawSequentialCards(int numCards) {
        List<Boolean> results = new ArrayList<>();
        boolean isUpright = true;
        for (int i = 0; i < numCards; i++) {
            results.add(isUpright);
            isUpright = !isUpright; // Alternate between upright and reversed
        }
        return results;
    }

    public static void main(String[] args) {
        // Example usage
        int numCards = 3;

        // Weighted probability
        System.out.println("Weighted Probability:");
        for (int i = 0; i < numCards; i++) {
            System.out.println("Card " + (i + 1) + ": " + drawCardWithWeightedProbability());
        }

        // Sequential drawing
        System.out.println("\nSequential Drawing:");
        List<Boolean> sequentialResults = drawSequentialCards(numCards);
        for (int i = 0; i < numCards; i++) {
            System.out.println("Card " + (i + 1) + ": " + sequentialResults.get(i));
        }
    }
}
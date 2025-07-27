package com.mercy.tarot.dto;

public class CardNamesRequest {
    private String cardNames;

    // Default constructor (required for JSON deserialization)
    public CardNamesRequest() {
    }

    // Constructor with parameter
    public CardNamesRequest(String cardNames) {
        this.cardNames = cardNames;
    }

    // Getter (required for JSON deserialization)
    public String getCardNames() {
        return cardNames;
    }

    // Setter (required for JSON deserialization)
    public void setCardNames(String cardNames) {
        this.cardNames = cardNames;
    }

    @Override
    public String toString() {
        return "CardNamesRequest{" +
                "cardNames='" + cardNames + '\'' +
                '}';
    }
}
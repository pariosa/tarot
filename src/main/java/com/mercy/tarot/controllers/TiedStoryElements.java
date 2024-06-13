package com.mercy.tarot.controllers;

public class TiedStoryElements {

    public String cardName;
    public String storyElement;

    public TiedStoryElements(String cardName, String storyElement) {
        this.cardName = cardName;
        this.storyElement = storyElement;

    }

    public String name = cardName;

    public void setStoryElement(String element) {
        this.storyElement = element;
    };

    public void setCardName(String cardName) {
        this.cardName = cardName;
    };
}

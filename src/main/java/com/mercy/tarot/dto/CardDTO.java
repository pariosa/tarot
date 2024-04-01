package com.mercy.tarot.dto;

public class CardDTO {

    private String name;
    private String description;
    private boolean reversed;
    private String story;

    // Constructors, getters, and setters

    // Constructor
    public CardDTO(String name, String description, boolean reversed, String story) {
        this.name = name;
        this.description = description;
        this.reversed = reversed;
        this.story = story;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getStory() {
        return story;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

}
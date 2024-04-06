package com.mercy.tarot.models;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@DynamicUpdate

public class Card {
    @Id
    private long id;
    public String card_description;
    public String card_value;
    public Boolean major;
    public String suit;
    public String title;
    public String story;

    public Card(
            String card_description,
            String card_value,
            Boolean major,
            String story,
            String suit,
            String title) {
        super();
        this.card_value = card_value;
        this.title = title;
        this.major = major;
        this.suit = suit;
        this.card_description = card_description;
        this.story = story;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return card_description;
    }

    public String getValue() {
        return card_value;
    }

    public Boolean getMajor() {
        return major;
    }

    public String getSuit() {
        return suit;
    }

    public String getStory() {
        return story;
    }

    public String getTitle() {
        return title;
    }
}
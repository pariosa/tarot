package com.mercy.tarot.models;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@DynamicUpdate
public class MajorArcana {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;
    public String card_value;
    public String title;
    public String roman_numeral;
    public String card_description;
    public String story;
    public Boolean major;

    public MajorArcana() {
        // Default constructor
    }

    public MajorArcana(String card_value, String title, String roman_numeral, String description, String story,
            Boolean major) {
        super();
        this.card_value = card_value;
        this.title = title;
        this.roman_numeral = roman_numeral;
        this.card_description = description;
        this.story = story;
        this.major = major;
    }

}
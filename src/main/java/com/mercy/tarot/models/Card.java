package com.mercy.tarot.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@Entity
@DynamicUpdate
public class Card{
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    
	private long id;
    public String card_value;
    public String title;
    public String suit;
    public String card_description;
    public String story;
    public String major;
    public Card (String card_value, String title, String suit, String card_description, String story,Boolean major){
        super ();
        this.card_value = card_value;
        this.title = title;
        this.suit = suit;
        this.card_description = card_description;
        this.story = story;
    }
}
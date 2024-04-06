package com.mercy.tarot.models;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@DynamicUpdate

public class Story {

    @Id
    private long id;
    public String keywords;
    public String main_character;
    public String main_character_defecit;
    public String main_character_goal;
    public String main_character_description;
    public String main_character_gender;
    public String call_to_action;
    public String ally;
    public String ally_description;
    public String ally_goal;
    public String ally_defecit;
    public String enemy;
    public String enemy_description;
    public String enemy_goal;
    public String enemy_defecit;
    public String location;
    public String point_of_view;
    public String moral_value;
    public String climax_event;
    public String climax_location;
    public String climax_description;
    public String theme;
    public String style;
    public String title;

    public Story() {
        // Default constructor
    }

    public Story(String keywords, String gender, String main_character, String main_character_defecit,
            String main_character_goal, String main_character_gender,
            String main_character_description, String call_to_action, String ally,
            String ally_description, String ally_goal, String ally_defecit, String enemy, String enemy_description,
            String enemy_goal, String enemy_defecit, String climax_event, String climax_location,
            String climax_description,
            String location, String point_of_view, String moral_value, String theme, String style, String title) {
        super();
        this.main_character = main_character;
        this.main_character_gender = main_character_gender;
        this.main_character_defecit = main_character_defecit;
        this.main_character_goal = main_character_goal;
        this.main_character_description = main_character_description;
        this.call_to_action = call_to_action;
        this.ally = ally;
        this.ally_description = ally_description;
        this.ally_goal = ally_goal;
        this.ally_defecit = ally_defecit;
        this.enemy = enemy;
        this.enemy_description = enemy_description;
        this.enemy_goal = enemy_goal;
        this.enemy_defecit = enemy_defecit;
        this.climax_event = climax_event;
        this.climax_location = climax_location;
        this.climax_description = climax_description;
        this.location = location;
        this.point_of_view = point_of_view;
        this.moral_value = moral_value;
        this.theme = theme;
        this.style = style;
        this.title = title;
    }
}
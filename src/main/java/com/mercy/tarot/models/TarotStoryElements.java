package com.mercy.tarot.models;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@DynamicUpdate

public class TarotStoryElements {
    @Id
    public String title;
    public String keywords;
    public String main_character_descriptors;
    public String main_character_defecits;
    public String main_character_goals;
    public String call_to_action;
    public String ally_descriptors;
    public String ally_goals;
    public String ally_defecits;
    public String enemy_descriptors;
    public String enemy_goals;
    public String enemy_defecits;
    public String locations;
    public String point_of_view;
    public String moral_value;
    public String climax_event;
    public String climax_location;
    public String climax_description;
    public String theme;
    public String style; 
    public String card_name;
    public TarotStoryElements() {
        // Default constructor
    }

    public TarotStoryElements(
            String title,
            String keywords,
            String main_character_descriptors,
            String main_character_defecits,
            String main_character_goals,
            String call_to_action,
            String ally_descriptors,
            String ally_goals,
            String ally_defecits,
            String enemy_descriptors,
            String enemy_goals,
            String enemy_defecits,
            String climax_event,
            String climax_location,
            String climax_description,
            String locations,
            String point_of_view,
            String moral_value,
            String theme,
            String style,
            String card_name) { 
        super();
        this.title = title;
        this.keywords = keywords;
        this.main_character_defecits = main_character_defecits;
        this.main_character_goals = main_character_goals;
        this.main_character_descriptors = main_character_descriptors;
        this.call_to_action = call_to_action;
        this.ally_descriptors = ally_descriptors;
        this.ally_goals = ally_goals;
        this.ally_defecits = ally_defecits;
        this.enemy_descriptors = enemy_descriptors;
        this.enemy_goals = enemy_goals;
        this.enemy_defecits = enemy_defecits;
        this.climax_event = climax_event;
        this.climax_location = climax_location;
        this.climax_description = climax_description;
        this.locations = locations;
        this.point_of_view = point_of_view;
        this.moral_value = moral_value;
        this.theme = theme;
        this.card_name = card_name;
        this.style = style;
    }

    public String[] getStyles() {
        return style.split(",");
    }

    public String[] getThemes() {
        return theme.split(",");
    }

    public String[] getMoralValues() {
        return style.split(",");
    }

    public String[] getKeywords() {
        return keywords.split(",");
    }

    public String[] getLocations() {
        return locations.split(",");
    }

    public String[] getMainCharacterDefecits() {
        return main_character_defecits.split(",");
    }

    public String[] getMainCharacterGoals() {
        return main_character_goals.split(",");
    }

    public String[] getMainCharacterDescriptors() {
        return main_character_descriptors.split(",");
    }

    public String[] getAllyDefecits() {
        return ally_defecits.split(",");
    }

    public String[] getAllyGoals() {
        return ally_goals.split(",");
    }

    public String[] getAllyDescriptors() {
        return ally_descriptors.split(",");
    }

    public String[] getEnemyDefecits() {
        return enemy_defecits.split(",");
    }

    public String[] getEnemyGoals() {
        return enemy_goals.split(",");
    }

    public String[] getEnemyDescriptors() {
        return enemy_descriptors.split(",");
    }

    public String[] getPointsOfView() {
        return point_of_view.split(",");
    }

    public String[] getClimaxEvents() {
        return climax_event.split(",");
    }

    public String[] getClimaxLocations() {
        return climax_location.split(",");
    }

    public String[] getClimaxDescriptions() {
        return climax_description.split(",");
    }

    public String getName() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public Object getCardName() {
        return card_name;
    }

}

CREATE TABLE IF NOT EXISTS `tarotstoryelements`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(100) NOT NULL,
    `keywords` TEXT,
    `main_character_descriptors` TEXT,
    `main_character_defecits` TEXT,
    `main_character_goals` TEXT,
    `call_to_action` TEXT,
    `ally_descriptors` TEXT,
    `ally_goals` TEXT,
    `ally_defecits` TEXT,
    `enemy_descriptors` TEXT,
    `enemy_goals` TEXT,
    `enemy_defecits` TEXT,
    `locations` TEXT,
    `point_of_view` TEXT,
    `moral_value` TEXT,
    `climax_event` TEXT,
    `climax_location` TEXT,
    `climax_description` TEXT,
    `theme` TEXT,
    `style` TEXT 
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
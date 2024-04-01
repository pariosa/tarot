# Tarot

Java package for reading tarot cards, complete with database seeders, model, controller, migrations, seecders and several endpoints.

## JDBC:

mariadb connection:
jdbc:mariadb://host:port/database
eg.
jdbc:mariadb://localhost:3306/tarot

## flyway maven script:

mvn clean flyway:migrate -Dflyway.configFiles=myFlywayConfig.conf

## maven build script:

mvn clean install

## Java execution script:

java -jar target/tarot-0.0.1-SNAPSHOT.jar

## tarot endpoints

### card by id

localhost:8080/api/card/{id}

### spread of cards

localhost:8080/api/spread/{number between 0 and 78}

### draw a single card

localhost:8080/api/draw

## Weighted endpoints

weighted to have 70% probability to not be reversed

### draw a card - weighted

localhost:8080/draw/weighted

### draw a spread - weighted

localhost:8080/spread/weighted/{number of cards}

# Tarot

Java package for reading tarot cards, complete with database seeders, model, controller, migrations, DataTransferObject and several endpoints.

## JDBC:

mariadb connection:
jdbc:mariadb://host:port/database
eg.
jdbc:mariadb://localhost:3306/tarot

## Flyway maven script:

mvn clean flyway:migrate -Dflyway.configFiles=flyway.conf

## Maven build script:

mvn clean install

## Java execution script:

java -jar target/tarot-0.0.2-SNAPSHOT.jar

## Tarot endpoints

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

## Front-end:

the front end uses a Vite bundler with React-Typescript

### npm install script

cd tarot-frontend && npm install

### run dev server

npm run dev

### Story Prompt Functions

api route /api/getRandomKeyword
POST
body:
{
cardNames:"Ace of Cups, The Moon, Ace OfWands, Ace Of Pentacles, Six Of Pentacles, Seven of Swords",
}

this will return a random "keyword" from a list of all the story elements from the cards present in the cardNames string.

### Authentication configuration

generate a firebase service account json here:
https://console.firebase.google.com/project/**tarot-project**/settings/serviceaccounts/adminsdk
and place it here:
src/main/resources/firebase-service-account.json

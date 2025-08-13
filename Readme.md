# Tarot

A modern Java tarot reading service api.

## Introduction

A high-performance, full-stack application designed for authentic tarot readings with modern scalability. Built on **Java Spring Boot** with a **React TypeScript (Vite)** frontend, this API delivers **secure, multi-threaded card shuffling algorithms** and **weighted probability reversals** for personalized readings.

### Key Features

✔ **Advanced Multi-Threaded Shuffling** – Combines **Fisher-Yates, ThreadLocalRandom, and entropy-based shuffling** for maximum randomness.  
✔ **Weighted Card Reversals** – Customizable probability settings for reversed card interpretations.  
✔ **Full Authentication System** – Secure **JWT-based** user roles (free & premium).  
✔ **Daily Readings & Spreads** – Supports single-card draws, multi-card spreads, and weighted reversals.  
✔ **Optimized Performance** – **Parallel processing** for high-throughput shuffling.  
✔ **Open-Source Stack** – **Spring Boot (Java 21), MySQL, React (TypeScript + Vite), Flyway, Hibernate**.

### Why Choose This API?

- **Cryptographically Secure Randomness** – Uses `SecureRandom` and system entropy for true unpredictability.
- **Scalable Architecture** – Multi-threaded shuffling ensures fast responses under load.
- **Modern Full-Stack Approach** – Combines **Spring Boot's robustness** with **React's dynamic frontend**.
- **Customizable Readings** – Adjust reversal weights, spread sizes, and user preferences.

### Technology Stack

| Component      | Technologies Used                         |
| -------------- | ----------------------------------------- |
| **Backend**    | Java 21, Spring Boot 3.2.4, Hibernate 6.5 |
| **Database**   | MySQL (H2 for testing)                    |
| **Migrations** | Flyway                                    |
| **Security**   | JWT, Spring Security                      |
| **Frontend**   | React, TypeScript, Vite                   |
| **Build Tool** | Maven                                     |

---

whether for personal insight or integration into spiritual apps, this API delivers **authentic, high-performance tarot readings with modern tech.**

[Explore Endpoints](#tarot-endpoints) | [Front End Configuration](#front-end-configuration) | [Story Prompting](#story-prompt-functions)

---

## Database Configuration

### MySQL/MariaDB Compatibility

The application is configured for **MySQL** by default but can easily switch to **MariaDB** by modifying the connection URL:

```yaml
# MySQL Configuration (default)
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/tarot?useSSL=false&serverTimezone=UTC
    driver-class-name: com.mysql.cj.jdbc.Driver

# MariaDB Configuration (alternative)
spring:
  datasource:
    url: jdbc:mariadb://localhost:3306/tarot
    driver-class-name: org.mariadb.jdbc.Driver
```

#The application uses Flyway for database version control. To execute migrations:

## Flyway maven script:

mvn clean flyway:migrate -Dflyway.configFiles=flyway.conf

## Maven build script:

mvn clean install

## Java execution script:

java -jar target/tarot-0.0.7-SNAPSHOT.jar

## Tarot Endpoints

### Authentication & User Management

| Endpoint                 | Method         | Description                               | Access |
| ------------------------ | -------------- | ----------------------------------------- | ------ |
| `/api/auth/**`           | POST           | Authentication endpoints (login/register) | Public |
| `/api/users`             | POST           | User registration                         | Public |
| `/api/users/check-email` | GET            | Check if email is available               | Public |
| `/api/users/me`          | GET            | Get current user profile                  | USER   |
| `/api/users/**`          | GET/PUT/DELETE | User management                           | USER   |

### Card Operations

| Endpoint                                   | Method | Description                            | Access |
| ------------------------------------------ | ------ | -------------------------------------- | ------ |
| `/api/card/{id}`                           | GET    | Get card by ID                         | USER   |
| `/api/cards/daily`                         | GET    | Get daily card                         | Public |
| `/api/draw`                                | GET    | Draw single random card                | USER   |
| `/api/draw/weighted`                       | GET    | Draw card with 70% upright probability | USER   |
| `/api/draw/parallel`                       | GET    | Draw card using parallel shuffle       | USER   |
| `/api/draw/parallel/weighted`              | GET    | Parallel weighted draw                 | USER   |
| `/api/spread/{numCards}`                   | GET    | Draw spread (1-78 cards)               | USER   |
| `/api/spread/weighted/{numCards}`          | GET    | Weighted spread (70% upright)          | Public |
| `/api/spread/parallel/{numCards}`          | GET    | Parallel shuffled spread               | USER   |
| `/api/spread/parallel/weighted/{numCards}` | GET    | Parallel weighted spread               | Public |

### Story Generation Elements

| Endpoint                             | Method | Description                            | Access  |
| ------------------------------------ | ------ | -------------------------------------- | ------- |
| `/api/story/location`                | GET    | Random story location                  | Public  |
| `/api/story/character-trait`         | GET    | Random character trait                 | Public  |
| `/api/story/theme`                   | GET    | Random story theme                     | Public  |
| `/api/story/keyword`                 | GET    | Random story keyword                   | Public  |
| `/api/story/moral-value`             | GET    | Random moral value                     | Public  |
| `/api/story/point-of-view`           | GET    | Random POV style                       | Public  |
| `/api/story/style`                   | GET    | Random writing style                   | Public  |
| `/api/story/climax-event`            | GET    | Random climax event                    | Public  |
| `/api/story/complete-element`        | GET    | Complete story element set             | PREMIUM |
| `/api/story/full-reading/{numCards}` | GET    | Full story reading with interpretation | PREMIUM |
| `/api/getStoryDTO`                   | GET    | Get complete story DTO                 | Public  |
| `/api/getRandomKeyword`              | GET    | Get random keyword                     | Public  |

### System Endpoints

| Endpoint           | Method | Description              | Access |
| ------------------ | ------ | ------------------------ | ------ |
| `/actuator/health` | GET    | Application health check | Public |
| `/api/public/**`   | GET    | Public API endpoints     | Public |

## Usage Examples

### Basic Card Draw

```bash
curl -X GET "http://localhost:8080/api/draw"
  -H "Authorization: Bearer {token}"
```

## Front End Configuration

configure .env with the .env.example if you are using fierebase to authenticate

### npm install script

cd tarot-frontend && npm install

### run dev server

npm run dev

### Story Prompt Functions

#### endpoint

    /api/story/getStoryDTO

#### Request

POST
body:

```json
{
  "cardNames": "The High Priestess, Three Of Swords, Ten Of Pentacles, The Sun"
}
```

#### Response

```json
{
  "mainCharacterDeficit": {
    "source": "Three of Swords",
    "storyElement": "Impulsive"
  },
  "allyGoal": {
    "source": "Ten of Pentacles",
    "storyElement": "Protecting lineage"
  },
  "climaxLocation": {
    "source": "Ten of Pentacles",
    "storyElement": "Sealed Crypt: Where ancestors rest and the past demands justice"
  },
  "enemyTrait": {
    "source": "The High Priestess",
    "storyElement": "Rigid"
  },
  "cardsMatched": 4,
  "climaxDescription": {
    "source": "Three of Swords",
    "storyElement": "where Rue must face the version of her who never healed—truth and tears finally surface"
  },
  "allyTrait": {
    "source": "The Sun",
    "storyElement": "Honest"
  },
  "pointOfView": {
    "source": "The Sun",
    "storyElement": "First Person Joyful – Narrated with light and hopeful inner dialogue"
  },
  "mainCharacterGoal": {
    "source": "The Sun",
    "storyElement": "Spreading joy or optimism"
  },
  "callToAction": {
    "source": "Ten of Pentacles",
    "storyElement": "Confrontation with forgotten past"
  },
  "cardsUsed": [
    "The High Priestess",
    "Three Of Swords",
    "Ten Of Pentacles",
    "The Sun"
  ],
  "allyDeficit": {
    "source": "The Sun",
    "storyElement": "Jealous of protagonist's success"
  },
  "climaxEvent": {
    "source": "The Sun",
    "storyElement": "A truth revealed in public"
  },
  "mainCharacterTrait": {
    "source": "Three of Swords",
    "storyElement": "Honest"
  },
  "enemyDeficit": {
    "source": "Ten of Pentacles",
    "storyElement": "Need for control"
  },
  "totalElementsFound": 139,
  "enemyGoal": {
    "source": "The Sun",
    "storyElement": "Isolate protagonist"
  },
  "location": {
    "source": "The High Priestess",
    "storyElement": "Dreamscapes"
  },
  "theme": {
    "source": "The Sun",
    "storyElement": "Celebration of Life: Embracing joy"
  },
  "style": {
    "source": "Ten of Pentacles",
    "storyElement": "Symbolic Realism"
  },
  "keyword": {
    "source": "The High Priestess",
    "storyElement": "Secrets"
  },
  "moralValue": {
    "source": "Three of Swords",
    "storyElement": "but it can help"
  }
}
```

this will return a random "keyword" from a list of all the story elements from the cards present in the cardNames string.

### Authentication configuration

generate a firebase service account json here:
https://console.firebase.google.com/project/**tarot-project**/settings/serviceaccounts/adminsdk
and place it here:
src/main/resources/firebase-service-account.json

# Tarot

# Tarot API - Professional Documentation

## Introduction

Welcome to the **Tarot API**, a high-performance, full-stack application designed for authentic tarot readings with modern scalability. Built on **Java Spring Boot** with a **React TypeScript (Vite)** frontend, this API delivers **secure, multi-threaded card shuffling algorithms** and **weighted probability reversals** for personalized readings.

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

**Get started today**—whether for personal insight or integration into spiritual apps, this API delivers **authentic, high-performance tarot readings with modern tech.**

[Explore Endpoints](#endpoints) | [View GitHub Repo](#github) | [API Demo](#demo)

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

```

```

# JDBC:

mariadb connection:
jdbc:mariadb://host:port/database
eg.
jdbc:mariadb://localhost:3306/tarot

# flyway maven script:

mvn clean flyway:migrate -Dflyway.configFiles=myFlywayConfig.conf

# maven build script:

mvn clean install

# Java execution script:

java -jar target/tarot-0.0.1-SNAPSHOT.jar

# tarot emdpoints

localhost:8080/api/card/{id}

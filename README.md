# Kotlin SpringBoot Elasticsearch example


## Project prerequisite:
- java 17
- maven
- docker or kubernetes

## How to run:
TODO: wip
```bash
mvn clean compile
mvn clean install -DskipTests
mvn clean package -DskipTests
java -jar target/kotlin-elastic-0.0.1-SNAPSHOT.jar
```


- Swagger UI: http://localhost:8080/swagger-ui.html
- API: http://localhost:8080/api

**Generate dtos from oas:**
```bash
mvn clean generate-sources
```

**Docker commands:**
```bash
docker build -t kotlin-elastic:latest .
docker rm kotlin-elastic
docker run --name kotlin-elastic --network docker-elk_elk -p 8080:8080 kotlin-elastic:latest -d
```

## Elasticsearch and Kibana
run and setup


## Deploy, Helm charts
how to run in kubernetes
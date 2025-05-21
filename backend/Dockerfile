#поменять в .properties на Postgres!
FROM openjdk:17-slim

RUN apt-get update && apt-get install -y \
    postgresql \
    postgresql-contrib \
    && rm -rf /var/lib/apt/lists/*

COPY target/prod-1.0.0.jar /app.jar

ENV POSTGRES_DB=vape-shop-db
ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=password

CMD service postgresql start && java -jar /app.jar
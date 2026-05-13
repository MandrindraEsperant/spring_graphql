# ─────────────────────────────────────────────
# ÉTAPE 1 : Build Maven
# ─────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copier uniquement les fichiers Maven d'abord
COPY pom.xml .

# Télécharger dépendances (cache Docker)
RUN mvn dependency:go-offline -B

# Copier le code source
COPY src ./src

# Build application
RUN mvn clean package -DskipTests -B

# ─────────────────────────────────────────────
# ÉTAPE 2 : Runtime léger
# ─────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copier uniquement le JAR
COPY --from=builder /app/target/*.jar app.jar

# Optimisation JVM pour container
ENV JAVA_OPTS="-XX:+UseContainerSupport \
-XX:MaxRAMPercentage=75.0 \
-XX:+UseG1GC"
# Port exposé
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
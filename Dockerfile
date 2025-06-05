

# Etapa 1: Construcción con Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia pom.xml y descarga dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el resto del código
COPY . .

# Compila el frontend y construye el jar en modo producción
RUN mvn vaadin:prepare-frontend vaadin:build-frontend
RUN mvn clean package -Pproduction -DskipTests

# Etapa 2: Imagen final con JDK liviano
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copia el jar desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Configura límites de memoria para evitar crash
ENV JAVA_TOOL_OPTIONS="-Xmx512m -Xms256m"

# Railway usa una variable de entorno PORT
ENV PORT=8080
EXPOSE 8080

# Arranca la aplicación
#CMD ["java", "-jar", "app.jar"]
# Comando para ejecutar tu aplicación con opciones de memoria
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]


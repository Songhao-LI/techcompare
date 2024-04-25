# Stage 1: Build the React application
FROM node:20 as build-frontend
WORKDIR /app
COPY Web/package.json Web/package-lock.json ./
RUN npm install
COPY Web/ ./
RUN npm run build

# Stage 2: Build the Spring Boot application using Gradle
FROM gradle:8.7.0-jdk17 as build-backend
WORKDIR /backend
COPY --chown=gradle:gradle Service/ ./
RUN rm -rf /backend/src/main/resources/static && rm -rf /backend/src/main/resources/templates
COPY --from=build-frontend /app/dist /backend/src/main/resources/static
RUN gradle build

# Stage 3: Run the application
FROM amazoncorretto:17-al2-generic
EXPOSE 3000
COPY --from=build-backend /backend/build/libs/*SNAPSHOT.jar /app.jar
COPY entrypoint.sh /entrypoint.sh
CMD ["bash", "/entrypoint.sh"]
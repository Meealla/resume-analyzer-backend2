# Используем официальный образ OpenJDK для Java 21
FROM openjdk:21-jdk-slim as build

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем файл gradle wrapper и build.gradle в рабочую директорию
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

# Копируем исходный код приложения
COPY src ./src

# Строим приложение
RUN ./gradlew build --no-daemon

# Используем минимальный образ OpenJDK для продакшн
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR файл из предыдущего шага
COPY --from=build /app/build/libs/resume-generator-0.0.1-SNAPSHOT.jar /app/resume-generator.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "resume-generator.jar"]

# Открываем порт 8080
EXPOSE 8080
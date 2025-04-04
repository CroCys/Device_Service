FROM openjdk:21-jdk

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранный .jar файл в контейнер
COPY target/Device_Service-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт 8080 для приложения
EXPOSE 8080

# Устанавливаем активный профиль Spring для продакшн среды
ENV SPRING_PROFILES_ACTIVE=prod

# Определяем переменную для дополнительных JVM параметров
ENV JAVA_OPTS=""

# Основная команда для запуска приложения
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE $JAVA_OPTS -jar app.jar"]

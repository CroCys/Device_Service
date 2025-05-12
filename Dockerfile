# Используем многоступенчатую сборку для оптимизации
FROM openjdk:21-slim AS builder

# Копируем JAR-файл в промежуточный контейнер
COPY target/Device_Service-0.0.1-SNAPSHOT.jar app.jar

# Распаковываем JAR для улучшения слоев Docker и более быстрого запуска
RUN mkdir -p extracted && \
    java -Djarmode=layertools -jar app.jar extract --destination extracted

# Финальный образ
FROM openjdk:21-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Создаем директорию для логов
RUN mkdir -p /var/logs && \
    chmod 777 /var/logs

# Копируем распакованное приложение из предыдущего этапа
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./

# Открываем порт 8080
EXPOSE 8080

# Настройки профиля и JVM
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# Exec форма ENTRYPOINT для корректной передачи сигналов
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} org.springframework.boot.loader.launch.JarLauncher"]

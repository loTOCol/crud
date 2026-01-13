# 멀티 스테이지 빌드: 빌드 스테이지
FROM gradle:8-jdk25 AS build
WORKDIR /app

# Gradle 캐시를 활용하기 위해 의존성 먼저 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon || true

# 소스 코드 복사 및 빌드
COPY src ./src
RUN gradle build -x test --no-daemon

# 실행 스테이지
FROM eclipse-temurin:25-jre
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

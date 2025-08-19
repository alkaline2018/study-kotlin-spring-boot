# 1단계: JAR 빌드
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# 2단계: 실행 이미지
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# 빌드 결과 JAR 복사 (스냅샷/버전 상관없이 첫 번째 JAR를 app.jar로)
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
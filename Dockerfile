# Dockerfile
# 1. 'builder' 스테이지: JAR 파일의 레이어를 분리하는 역할만 함
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY app.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# 2. 최종 이미지를 만드는 스테이지
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 3. builder 스테이지에서 분리된 레이어들을 복사
#    - 의존성처럼 잘 바뀌지 않는 것을 먼저 복사하여 캐시 효율을 높임
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

EXPOSE 8080
# 4. ENTRYPOINT 변경: 분리된 레이어를 실행하기 위한 시작점
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
#FROM eclipse-temurin:21-jre-alpine
#WORKDIR /app
#COPY app.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/app/app.jar"]
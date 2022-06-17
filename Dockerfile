FROM openjdk:18
ADD target/projekt_chmury.jar projekt_chmury.jar
ENTRYPOINT ["java", "-jar", "/projekt_chmury.jar"]
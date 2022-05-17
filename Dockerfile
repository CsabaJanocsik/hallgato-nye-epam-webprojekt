FROM openjdk:17-jdk-alpine3.14

COPY ".target/student-management-system.jar" "/application/student-management-system.jar"

CMD ["java", "-jar", "/application/student-management-system.jar"]

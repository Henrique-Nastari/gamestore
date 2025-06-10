# Usa uma imagem com o Java JDK 17 e o Maven para compilar o projeto
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Copia o arquivo de configuração do Maven e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código fonte
COPY src ./src

# Compila o projeto e gera o arquivo .jar, pulando os testes
RUN mvn package -DskipTests

# Usa uma imagem muito menor, contendo apenas o Java JRE 17 para rodar
FROM eclipse-temurin:17-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia apenas o arquivo .jar compilado do estágio de build
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
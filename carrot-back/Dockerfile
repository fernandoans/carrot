# ESTÁGIO 1: Build (Compilação com Gradle e JDK 17)
# Imagem que já contém o Gradle e o JDK 17.
FROM gradle:8.9-jdk21 AS build

# Definir o diretório de trabalho para o processo de build
WORKDIR /app

# Copiar os arquivos de configuração do Maven para o container
COPY mvnw .
COPY .mvn .mvn

# Copiar os arquivos de definição de dependências (pom.xml)
COPY pom.xml .

# Opcional: Baixa as dependências em uma camada separada para acelerar builds futuros
RUN ./mvnw dependency:go-offline -B

# Copiar o código-fonte
COPY src ./src

# Executar a construção completo do projeto, gerando o JAR executável
RUN chmod +x ./mvnw && ./mvnw clean package -DskipTests

# ---

# ESTÁGIO 2: Runtime (Execução - Imagem Final mais leve)
# JRE 21 (Java Runtime Environment) de uma imagem Alpine
FROM eclipse-temurin:21-jre-alpine

# Definir o diretório de trabalho (onde o app.jar será executado)
WORKDIR /app

# JAR gerado na pasta 'target/'
ARG JAR_FILE=/app/target/*.jar

# Copiar o JAR do estágio de 'build' para o estágio de 'runtime'
COPY --from=build ${JAR_FILE} app.jar

# Informa ao Docker que a aplicação escuta a porta 8080
EXPOSE 8080

# Comando para rodar a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]

# 🥕 Carrot
Cenoura? Não isso é um primo pobre, *Open Source*, *Font-Open* do **Kahoot**, com o básico que todo professor precisa para instruir divertidamente seus alunos.

Uma aplicação muito próxima do "Kahoot", com QRCode, ranking em tempo real, cronômetro e múltiplos jogadores, sem depender de serviços externos. Essa arquitetura é simples e suficiente para dezenas ou até centenas de participantes.

## Tecnologias Usadas

- Java 21 com Spring Boot 4.1.0
 	- Controllers MCV
    - Spring Web
    - Spring WebSocket (tempo real)
    - Spring Data JPA
    - H2 Database
    - Lombok
- Banco H2
- JPA/Hibernate
- Thymeleaf (renderização)
- HTMX (requisições AJAX)
	- STOMP (Para comunicação em tempo real com Spring WebSocket)
    - Bootstrap (Visual)


### Estrutura do Projeto

A estrutura de pastas única seguem o padrão Domain-Drive Design / Clean Architecture que propõe, para o Backend a divisão em 4 camadas definidas:
- application (componentes e services)
- domain (model e repository)
- infrastructure (classes de apoio ao projeto)
- presentation (controles e DTOs)

Pasta /resources
- static (css e JS)
- templates (páginas HTMX)

Projeto ainda em Construção

## Tarefas a cumprir

### Fase 1
- [x] Criar a estrutura do Projeto Back
- [x] Criar jogadores
    - [x] Criar um jogador (entrar no Jogo)
    - [x] Listar jogadores
- [x] Iniciar jogo
- [x] Iniciar pergunta
- [x] Enviar resposta do Jogador
- [x] Finalizar pergunta obtendo o ranking
- [x] Verificar se terminou o jogo

### Fase 2
- [x] Criar a estrutura do Projeto Front
- [x] Criar conexão com o Websocket
- [x] Criar Dockerfile para Back/Front
- [x] Testar envios de mensagens pelo Websocket e Stomp
- [x] Enviar mensagem para gerar o QRCode inicial
- [x] Importar perguntas

### Fase 3
- [x] Ativar o Cronômetro
- [x] Complexidade exagerada com Rest.js
- [x] Entrar no jogo
- [x] Receber e mostrar a pergunta
- [x] Enviar a resposta do Aluno/Front
- [x] Mostrar resultado atual (Ranking)

### Fase 4
- [x] Tela de Pontuação
- [x] Tela do Ranking Final
- [x] Base de Perguntas reais para refinamentos e testes 
- [ ] Tela da Posição do Jogador
- [ ] Exportar os resultados para CSV

## Passos ao baixar o projeto

Se desejar testar o projeto basta baixá-lo, no front criar na pasta raiz um arquivo chamado .env com a seguinte variável:

VITE_BACKEND_PORT=[porta do Back]

### Testes a executar

Verificar envio correto da comunicação WS -> Stomp

```
Spring Boot
  ↓
WebSocket - STOMP
  ↓
React
  ↓
SimpleBroker
  ↓
/topic/game
```

1. Iniciar o backend.
2. Iniciar o frontend.
3. No navegador, abrir o frontend no endereço: http://localhost:5173 veremos a tela inicial.
4. No console deve aparecer: WebSocket conectado!
5. No navegador, em uma nova aba, enviar a mensagem de teste: http://localhost:8080/test/send
6. Deve mostrar na página: Mensagem Enviada!
7. No console deve aparecer: TEST - Testar a conexão e a tela do front mudar para a tela com QRCode.

E validamos: <br/>
✅ Conexão WebSocket <br/>
✅ Handshake STOMP <br/>
✅ Subscribe <br/>
✅ Envio de mensagem do backend <br/> 
✅ Recebimento da mensagem no frontend

### Importar o Arquivo

Exemplo de um Arquivo CSV para importar:

```csv
ordem;pergunta;a;b;c;d;correta;tempo
1;Capital do Brasil?;Rio;São Paulo;Brasília;Salvador;3;20
2;2 + 2?;3;4;5;6;2;15
3;Java é?;Banco;Linguagem;Sistema;Framework;2;20
```

A. Fluxo da Ação no Front

Professor -> Acessar o Link: http://localhost:5173/admin/upload
Professor -> Enviar o arquivo

Na tela mostrar o Link: http://localhost:5173

Tela dos Alunos -> Automaticamente modifica a tela de Entrada para aguardando Jogadores com o QRCode. 

B. Fluxo da Ação no Back

```html
POST /api/game/upload-file
Recebe: MultipartFile csv
```
Responsabilidades:
1. Limpar banco
2. Ler CSV
3. Persistir perguntas
4. Persistir jogo
5. Enviar mensagem de aguardando início
6. Iniciar o cronômetro

### Cronômetro

O jogo funciona na base de mensagens (WebSock-Stomp) e o "coração" está no cronômetro, consideremos que as mensagens seguem a seguinte ordem por tempo:
```
GAME_WAITING (Assim que for realizado o Download do Arquivo)
  ↓ 5 mins para entrar
QUESTION_STARTED
  ↓ Tempo definido na questão
SHOW_RANKING
  ↓ 1 min para visualizar o Ranking
QUESTION_STARTED
  ↓ Tempo definido na questão
SHOW_RANKING
  ↓ 1 min para visualizar o Ranking
 ...
  ↓
GAME_FINISHED
```

# Entrar no Jogo
Para resolver o fluxo sem bagunça e mantendo HTMX + STOMP (WebSocket) 
no controle total, foi dividido os endpoints e visões em dois papéis claros:
```
┌────────────────────────────────────────────────────┐
│                    ESTADO DO JOGO                  │
│ (Enum/Service no Spring: LOBBY, QUESTION, RANKING) │
└───────────────────────────┬────────────────────────┘
                            │
             ┌──────────────┴────────────────┐
             ▼                               ▼
   Visão 1: SCREEN/PROJETOR    Visão 2: CELULAR DO ALUNO
     (http://.../screen)        (http://.../player/join)
   - Exibir QR Code             - Digitar Nickname
   - Listar quem entrou         - Ficar na tela de "Aguarde"
   - Mostrar Pergunta/Opções    - Ver botões para responder
   - Mostrar Ranking Geral      - Ver resultado da sua resposta
             │                               │
             ▼                               ▼
   Inscrito em: /topic/game       Inscrito em: /topic/game
     (fragmentos/screen)             (fragmentos/player)
             │                               │
             ▼                               ▼
     🖥️ Projetor (index.html)   📱 Celulares (player.html)
```

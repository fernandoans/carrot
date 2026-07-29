# 🥕 Carrot
Cenoura? Não isso é um primo pobre, *Open Source*, *Font-Open* do **Kahoot**, com o básico que todo professor precisa para instruir divertidamente seus alunos.

Uma aplicação muito próxima do "Kahoot", com QRCode, ranking em tempo real, cronômetro e múltiplos jogadores, sem depender de serviços externos. Essa arquitetura é simples e suficiente para dezenas ou até centenas de participantes.

## Tecnologias Usadas

### Backend
- Java 21 com Spring Boot
	- Spring Web
	- Spring WebSocket (tempo real)
	- Spring Data JPA
	- H2 Database
	- Validation
	- Lombok
- Banco H2
- JPA/Hibernate

### Frontend
- React.js com Vite
	- react-router-dom
	- STOMP (Para comunicação em tempo real com Spring WebSocket)
	- axios (Cliente HTTP)
	- qrcode.react (exibir o QR Code de entrada dos jogadores)
- HTML
- CSS (Bootstrap 5)

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
- [ ] Ativar o Cronômetros
- [ ] Entrar na sessão
- [ ] Receber e mostrar a pergunta
- [ ] Enviar a resposta do Aluno/Front
- [ ] Mostrar resultado atual (Ranking)

### Fase 4
- [ ] Tela de Pontuação
- [ ] Tela de Ranking
- [ ] Tela de Encerramento
- [ ] Exportar os resultados para CSV

## Baixar o projeto

Se desejar testar o projeto basta baixá-lo, no front criar na pasta raiz um arquivo chamado .env com a seguinte variável:

VITE_BACKEND_PORT=[porta do Back]

## Testes a executar

### Verificar envio correto da comunicação WS -> Stomp

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

### Importação do Arquivo

Exemplo do Arquivo CSV para importar:

```csv
ordem;pergunta;a;b;c;d;correta;tempo
1;Capital do Brasil?;Rio;São Paulo;Brasília;Salvador;3;20
2;2 + 2?;3;4;5;6;2;15
3;Java é?;Banco;Linguagem;Sistema;Framework;2;20
```

A. Fluxo da Ação no Front

Professor -> Enviar o arquivo -> Limpa o banco e grava questões e jogo -> Envia mensagem
Alunos    -> Muda a tela de Entrada para aguardando Jogadores 

B. Fluxo da Ação no Front

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

### Entrar no Jogo
```html
POST /api/player
```

### Listar jogadores
```html
GET /api/player/all
```

### Abrir pergunta
```html
GET /api/game/open-question
```

### Responder pergunta
```html
POST /api/player/answer
```

### Fechar pergunta e obter ranking
```html
GET /api/game/finish-question
```

### Verificar se terminou
```html
GET /api/game/finish
```
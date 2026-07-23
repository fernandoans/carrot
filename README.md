# Carrot
Cenoura? Não isso é um primo pobre, Open Source, FontOpen do Kahoot, com o básico que todo professor precisa para instruir divertidamente seus alunos.

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
- [ ] Importar perguntas
- [ ] Criar a estrutura do Projeto Front
- [ ] Entrar na sessão
- [ ] Sala de espera
- [ ] QRCode

### Fase 3
- [ ] Websocket
- [ ] Iniciar o jogo pelo Front
- [ ] Enviar a pergunta pelo Front
- [ ] Receber a resposta pelo Front

### Fase 4
- [ ] Cronômetro
- [ ] Tela de Pontuação
- [ ] Tela de Ranking
- [ ] Tela de Encerramento
- [ ] Exportar os resultados para CSV

### Importação do Arquivo

Exemplo do Arquivo CSV para importar:

```csv
ordem;pergunta;a;b;c;d;correta;tempo
1;Capital do Brasil?;Rio;São Paulo;Brasília;Salvador;3;20
2;2 + 2?;3;4;5;6;2;15
3;Java é?;Banco;Linguagem;Sistema;Framework;2;20
```
Fluxo

```java
import org.springframework.web.multipart.MultipartFile;

public void loadQuestions(MultipartFile file) {
  gameRepository.deleteAll();
  playerAnswerRepository.deleteAll();
  questionRepository.deleteAll();
  playerRepository.deleteAll();
  loadCSV(file);
}
```
## Fluxo do Front

### Subir arquivo CSV
```html
POST /api/game/load
Recebe: MultipartFile csv
```
Responsabilidades:
1. Limpar banco
2. Ler CSV
3. Persistir perguntasa

### Entrar no Jogo
```html
POST /api/player
```

### Listar jogadores
```html
GET /api/player/all
```

### Iniciar jogo
```html
POST /api/game/start
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
# Estágio 1: Build (Apenas para garantir que o Node.js e o NPM estejam disponíveis)
# Imagem oficial do Node.js (preferencialmente LTS)
FROM node:20-alpine AS development

# Definir o diretório de trabalho no container
WORKDIR /src

# Copiar os arquivos de definição de dependências para aproveitar o cache do Docker
# O package.json indica suas dependências.
COPY package*.json ./

# Instalar todas as dependências do projeto
# (Node:20-alpine é muito leve e eficiente)
RUN npm install

# Copia o restante do código fonte do projeto
COPY . .

# Definir a porta que o Vite Dev Server
EXPOSE 5173

# Comando de Execução:
# O comando "npm run dev" inicia o servidor de desenvolvimento do Vite.
# O parâmetro --host garante que o servidor escute em todas as interfaces de rede (0.0.0.0),
# permitindo que o Docker o exponha corretamente.
CMD ["npm", "run", "dev", "--", "--host"]

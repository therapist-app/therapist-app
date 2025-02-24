FROM node:22.14.0 AS build

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

ARG ENV_NAME=production

RUN cp .env.production.$ENV_NAME .env

ENV NODE_ENV=production

RUN npm run build

FROM nginx:latest

WORKDIR /usr/share/nginx/html

COPY --from=build /app/dist ./

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

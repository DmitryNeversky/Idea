#stage 1
FROM node:12.22.10-alpine as node
WORKDIR /app
COPY . .
RUN npm install | npm build --prod
#stage 2
FROM nginx
COPY --from=node /app/dist/frontend /usr/share/nginx/html
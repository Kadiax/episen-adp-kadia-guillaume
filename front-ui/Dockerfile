FROM node:alpine AS my-app-build
WORKDIR /opt/app
COPY . .
RUN npm ci && npm run build

# étape 2 : la conteneuriser
FROM nginx:alpine
COPY --from=my-app-build opt/app/dist/front-ui /usr/share/nginx/html
EXPOSE 80

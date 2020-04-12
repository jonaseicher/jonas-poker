FROM nginx:alpine
RUN mkdir /app
COPY dist /app
COPY .htpasswd /etc/nginx/.htpasswd
COPY ./nginx.conf /etc/nginx/nginx.conf
COPY ./fetch_env.js /etc/nginx/fetch_env.js
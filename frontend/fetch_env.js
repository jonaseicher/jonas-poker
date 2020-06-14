/* eslint-disable */
function fetchNginxServerName() {
  return process.env.NGINX_SERVER_NAME;
}

function fetchApiServiceHost() {
  return process.env.JONAS_POKER_BACKEND_HOST;
}

function fetchApiService() {
  return `${process.env.JONAS_POKER_BACKEND_HOST}:${process.env.JONAS_POKER_BACKEND_PORT}`;
}

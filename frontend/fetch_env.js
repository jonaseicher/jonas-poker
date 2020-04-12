
function fetchNginxServerName(r) {
  return process.env.NGINX_SERVER_NAME;
}

function fetchApiServiceHost(r) {
  return process.env.JONAS_POKER_BACKEND_HOST;
}

function fetchApiService(r) {
  return `${process.env.JONAS_POKER_BACKEND_HOST}:${process.env.JONAS_POKER_BACKEND_PORT}`;
}

const HOST = window.location.hostname;
const BACKEND_PORT = import.meta.env.VITE_BACKEND_PORT || 8080;

console.log('HOST:', HOST);
console.log('BACKEND_PORT:', BACKEND_PORT);
console.log('Montado1:', `http://${HOST}:${BACKEND_PORT}`);
console.log('Montado2:', `ws://${HOST}:${BACKEND_PORT}/game`);

export const API_URL = `http://${HOST}:${BACKEND_PORT}`;
export const WS_URL = `ws://${HOST}:${BACKEND_PORT}/game`;
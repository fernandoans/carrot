const HOST = window.location.hostname;
const BACKEND_PORT = import.meta.env.VITE_BACKEND_PORT || 8080;

export const API_URL = `http://${HOST}:${BACKEND_PORT}/api`;
export const WS_URL = `ws://${HOST}:${BACKEND_PORT}/game`;
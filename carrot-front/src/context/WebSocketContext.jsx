import { createContext, useContext, useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import { WS_URL } from '../config/config.js';

const WebSocketContext = createContext(null);

export const WebSocketProvider = ({ children }) => {

  const [client, setClient] = useState(null);
  const [connected, setConnected] = useState(false);

  useEffect(() => {
    const stompClient = new Client({
      brokerURL: WS_URL,
      reconnectDelay: 5000
    });

    stompClient.onConnect = () => {
      console.log('WebSocket conectado!');
      setConnected(true);
    };

    stompClient.onDisconnect = () => {
      console.log('WebSocket desconectado!');
      setConnected(false);
    };

    stompClient.onStompError = (frame) => {
      console.error('Erro no WebSocket:', frame);
    };

    stompClient.activate();
    setClient(stompClient);
    
    return () => {
      stompClient.deactivate();
    };
  }, []);

  return (
    <WebSocketContext.Provider 
      value={{ 
        client, 
        connected 
      }}
    >
      {children}
    </WebSocketContext.Provider>
  );
};

export const useWebSocket = () => {
  return useContext(WebSocketContext);
};
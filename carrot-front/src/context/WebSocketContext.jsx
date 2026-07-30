import { createContext, useContext, useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import { WS_URL } from '../config/config.js';
import { useGame } from './GameContext';

const WebSocketContext = createContext(null);

export const WebSocketProvider = ({ children }) => {

  const game = useGame();
  const [client, setClient] = useState(null);
  const [connected, setConnected] = useState(false);

  useEffect(() => {
    const stompClient = new Client({
      brokerURL: WS_URL,
      reconnectDelay: 5000
    });

    stompClient.onConnect = (message) => {
      console.log('WebSocket conectado!');
      stompClient.subscribe("/topic/game", (message) => {
        const event = JSON.parse(message.body);
        switch (event.type) {
          case 'TEST':
            game?.setGameStatus("WAITING");
            console.log(event.type, "-", event.content);
            break;
          case 'GAME_WAITING':
            game?.setGameStatus("WAITING");
            //setJoinSeconds(event.payload.joinSeconds);
            break;
          case 'PLAYER_JOINED':
            console.log('Jogador entrou:', event.content);
            break;
          case 'QUESTION_STARTED':
            game?.setGameStatus("QUESTION");
            console.log('Pergunta iniciada:', event.content);
            //setCurrentQuestion(event.payload);
            break;
          case 'SHOW_RANKING':
            game?.setGameStatus("RANKING");
            console.log('Mostrar Ranking:', event.content);
            //setRanking(event.payload);
            break;
          case 'GAME_FINISHED':
            game?.setGameStatus("FINISHED");
            console.log('Jogo finalizado:', event.content);
            break;
          default:
            console.log('Evento desconhecido:', event);
        }     
      });
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
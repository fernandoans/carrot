import MainScreen from './pages/MainScreen';

import { WebSocketProvider } from './context/WebSocketContext';
import { GameProvider } from './context/GameContext';

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

function App() {
  return (
    <GameProvider>
      <WebSocketProvider>
        <MainScreen />
      </WebSocketProvider>
    </GameProvider>
  );
}

export default App;
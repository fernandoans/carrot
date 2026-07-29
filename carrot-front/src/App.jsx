import { BrowserRouter as Router, Route, Routes, BrowserRouter } from 'react-router-dom';

import { GameProvider } from './context/GameContext';
import { WebSocketProvider } from './context/WebSocketContext';

import MainScreen from './pages/MainScreen';
import UploadQuestions from './pages/UploadQuestions';

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

function App() {
  return (
    <GameProvider>
      <WebSocketProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<MainScreen />} />
            <Route path="/admin/upload" element={<UploadQuestions />} />
          </Routes>
        </BrowserRouter>
      </WebSocketProvider>
    </GameProvider>
  );
}

export default App;
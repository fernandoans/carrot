import { createContext, useContext, useState } from "react";

const GameContext = createContext();

export const GameProvider = ({ children }) => {

  const [gameStatus, setGameStatus] = useState("TEST");
  const [joinSeconds, setJoinSeconds] = useState(0);
  const [players, setPlayers] = useState([]);
  const [currentQuestion, setCurrentQuestion] = useState(null);
  const [ranking, setRanking] = useState([]);

  return (
    <GameContext.Provider
      value={{
        gameStatus,
        setGameStatus,
        joinSeconds,
        setJoinSeconds,
        players,
        setPlayers,
        currentQuestion,
        setCurrentQuestion,
        ranking,
        setRanking
      }}
    >
      {children}
    </GameContext.Provider>
  );
};

export const useGame = () => useContext(GameContext);
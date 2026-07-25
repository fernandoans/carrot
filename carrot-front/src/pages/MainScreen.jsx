import { useGame } from "../context/GameContext";

import LobbyView from "../components/LobbyView";
import QuestionView from "../components/QuestionView";
import RankingView from "../components/RankingView";
import FinalView from "../components/FinalView";
import DefaultView from "../components/DefaultView";

function MainScreen() {
  const { gameStatus } = useGame();

  switch(gameStatus) {
    case "WAITING":
      return <LobbyView />;
    case "QUESTION":
      return <QuestionView />;
    case "RANKING":
      return <RankingView />;
    case "FINISHED":
      return <FinalView />;
    default:
      return <DefaultView />;  
  }
}

export default MainScreen;
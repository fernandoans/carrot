import { QRCodeSVG } from 'qrcode.react';

function LobbyView() {

  const joinUrl =
    `${window.location.protocol}//${window.location.host}/join`;

  const players = [
    "Fernando",
    "Maria",
    "João",
    "Pedro",
    "Paulo",
    "Buarque",
    "Helena",
    "Lucas",
    "Ana",
    "Carla",
    "Rafael",
    "Beatriz",
    "Gustavo",
    "Camila",
    "Mariana"
  ];

  return (
    <div className="app-container">
      <div className="container">
        <div className="row justify-content-center mt-2">
          <div className="row">
            <div className="col-md-5">
              <h3 className="display-3 fw-bold text-white">
                🥕 Carrot
              </h3>
            </div>
            <div className="col-md-6 text-center title_text">
              <span className="text-white">
                Plataforma interativa de perguntas e respostas em tempo real.
              </span>
            </div>
          </div>
          <div className="hero-card">
            <div className="row g-4 align-items-stretch">
              {/* QRCode */}
              <div className="col-md-5">
                <div className="panel-card text-center">
                  <h5 className="text-white mb-20">
                    Escaneie para participar
                  </h5>
                  <div className="qr-container">
                    <QRCodeSVG
                      value={joinUrl}
                      size={250}
                    />
                  </div>
                  <div className="mt-2 text-white small">
                    {joinUrl}
                  </div>
                </div>
              </div>

              {/* Jogadores */}
              <div className="col-md-6">
                <div className="panel-card">
                  <div className="d-flex justify-content-between align-items-center mb-3">
                    <h5 className="text-white mb-0">
                      Jogadores conectados
                    </h5>
                    <span className="badge bg-warning text-dark fs-6">
                      {players.length}
                    </span>
                  </div>
                  <div className="player-list">
                    {players.map((player, index) => (
                      <div
                        key={index}
                        className="player-item"
                      >
                        👤 {player}
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
            <div className="text-center mt-2">
              <h4 className="text-white">
                Aguardando o início do jogo...
              </h4>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LobbyView;
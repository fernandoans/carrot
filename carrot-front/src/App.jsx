import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

function App() {
  return (
    <div className="app-container">
      <div className="container text-center">
        <div className="row justify-content-center">
          <div className="col-lg-8">
            <div className="hero-card">
              <div className="mb-4">
                <span className="display-1">🥕</span>
              </div>
              <h1 className="display-3 fw-bold text-white">
                Carrot
              </h1>
              <p className="text-white"> 
                Plataforma interativa de perguntas e respostas em tempo real.
              </p>
              <div className="d-grid gap-3 mt-5"> 
                <button className="btn btn-warning btn-lg fw-bold py-3"> 
                  Entrar em uma partida
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
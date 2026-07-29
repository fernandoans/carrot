import { useState } from "react";
import { uploadQuestions } from "../services/questionService";

export default function UploadQuestions() {
  const [file, setFile] = useState(null);
  const [uploading, setUploading] = useState(false);
  const [error, setError] = useState(null);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleUpload = async () => {
    if (!file) {
      setError("Selecione um arquivo CSV.");
      return;
    }

    setUploading(true);
    setError(null);

    try {
      await uploadQuestions(file);
      alert("Arquivo enviado com sucesso!");
    } catch (err) {
      setError(err.message || "Falhou para carregar o arquivo.");
    } finally {
      setUploading(false);
    }
  };

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
              <div className="panel-card text-center">
                <h5 className="text-white mb-20">
                  Enviar Questões
                </h5>
                <input 
                  type="file" 
                  accept=".csv"
                  className="form-control mt-3" 
                  onChange={ (e) => setFile(e.target.files[0]) }
                />
                <button 
                  onClick={handleUpload} 
                  disabled={uploading}
                  className="btn btn-primary mt-3"
                >
                  {uploading ? "Enviando..." : "Enviar"}
                </button>
                {error && <p className="error">{error}</p>}
              </div>  
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
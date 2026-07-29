import { API_URL } from '../config/config.js';

export async function uploadQuestions(file) {

  const formData = new FormData();
  formData.append('file', file);

  try {
    const response = await fetch(`${API_URL}/game/upload-file`, {
      method: 'POST',
      body: formData,
    });

    if (!response.ok) {
      throw new Error('Failed to upload question');
    }
    const data = await response.text();
    return data;
  } catch (error) {
    throw new Error('Erro ao enviar o arquivo');
  }
}
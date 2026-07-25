docker stop carrot-front
docker rm carrot-front
docker build -t carrot-front:latest .
docker run -d -p 5173:5173 --name carrot-front carrot-front

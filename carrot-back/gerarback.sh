docker stop carrot-back
docker rm carrot-back
docker rmi carrot-back:latest
docker build -t carrot-back:latest .
docker run -d -p 8080:8080 --name carrot-back carrot-back:latest

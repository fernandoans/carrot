docker stop carrot
docker rm carrot
docker rmi carrot:latest
docker build -t carrot:latest .
docker run -d -p 8080:8080 --name carrot carrot:latest

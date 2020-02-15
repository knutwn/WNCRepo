mvn clean install -U
./docker_image_remove_all.sh
docker build -t foodservice .
docker run -p 8080:8080 foodservice

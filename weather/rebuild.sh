docker stop `cat weather.cid`
rm weather.cid
for x in `docker container ps -a | grep weather | awk '{print $1}'`; do docker rm  $x; done
docker rmi weather
docker build -t weather -f Dockerfile.local .
docker run --name "weather" --cidfile weather.cid -d -p 3000:3000 weather

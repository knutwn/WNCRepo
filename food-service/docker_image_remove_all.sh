docker images | awk '{print $3}' | grep -v IMAGE | xargs docker rmi -f

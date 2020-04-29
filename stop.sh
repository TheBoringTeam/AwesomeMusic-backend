if [ ! "$(docker ps -q -f name=backend)" ]; then
    if [ "$(docker ps -aq -f status=exited -f name=backend)" ]; then
        # cleanup
        docker rm backend
    fi
fi
if [ -z `docker ps -q --no-trunc | grep $(docker-compose ps -q backend)` ]; then
  docker kill backend
  echo "No, it's not running."
else
  echo "Yes, it's running."
fi
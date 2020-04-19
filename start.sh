docker-compose up -d
docker logs backend >& logs/backend.log
docker logs postgres >& logs/postgres.log
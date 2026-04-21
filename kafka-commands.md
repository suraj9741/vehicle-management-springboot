Create Topic 
```shell
docker exec -it kafka-1 kafka-topics \
--create \
--topic vehicle-events \
--bootstrap-server kafka-1:29092 \
--replication-factor 2 \
--partitions 3
```

Verify Topic
```shell
docker exec -it kafka-1 kafka-topics \
--list \
--bootstrap-server kafka-1:29092
```
Describe Topic
```shell
docker exec -it kafka-1 kafka-topics \
--describe \
--topic vehicle-events \
--bootstrap-server kafka-1:29092
```
# Microservice using Kafka Streams

Microservice that uses Kafka Streams to get the geolocation of an IP address from api IPStack. 

##  Configuration

Edit default.properties for production and test.properties for tests.

INPUT_TOPIC=topic_name
OUTPUT_TOPIC=topic_name
KAFKA_APP_ID=iplocation
KAFKA_SERVER_HOST=localhost
KAFKA_SERVER_PORT=9092
REDIS_SERVER_HOST=localhost
REDIS_SERVER_PORT=6379
REDIS_SERVER_DB=1
IPSTACK_ACCESS_KEY=put_your_key_here
IPSTACK_SERVICE_URL=http://api.ipstack.com/

### Cache

Use de a Redis Server for cache.
# Microservice using Kafka Streams

Microservice that uses Kafka Streams to get the geolocation of an IP address from api IPStack. 

## Input format

#### Serialized Jason String

##### clientId: String
##### timestamp: Integer
##### ip: String

{"clientId":"client_id_value", "timestamp":12244111, "ip":"0.0.0.0"}

## Output format

#### Serialized Jason String

##### clientId: String
##### timestamp: Integer
##### ip: String
##### latitude: BigDecimal
##### longitude: BigDecimal
##### country: String
##### region: String
##### city: String

{"clientId":"client_id_value", "timestamp":12244111, "ip":"0.0.0.0", "latitude": -22.2255555, "longitude": -5.5554444, "country":"United States", "region":"Virginia", "city":"Richmond"}

##  Configuration

Edit default.properties for production and test.properties for tests.

#### INPUT_TOPIC=topic_name
#### OUTPUT_TOPIC=topic_name
#### KAFKA_APP_ID=iplocation
#### KAFKA_SERVER_HOST=localhost
#### KAFKA_SERVER_PORT=9092
#### REDIS_SERVER_HOST=localhost
#### REDIS_SERVER_PORT=6379
#### REDIS_SERVER_DB=1
#### IPSTACK_ACCESS_KEY=put_your_key_here
#### IPSTACK_SERVICE_URL=http://api.ipstack.com/

### Cache

Use de a Redis Server for cache.
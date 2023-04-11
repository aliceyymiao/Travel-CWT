# Travel-CWT
CWT is working with 3rd party agent CONCUR to retrieve customer data.
The system will take input from kafka stream, the stream data is customer email,
firstname and last name. Based on this stream data, you will visit CWT database to fetch
the token that matches the input. Token usually has expiration timestamp on it. Once you fetch the
token, you will use it to call the CONCUR api to fetch detailed user information,
which includes the user detail and payment data. This detailed user data will then be sent
out via kafka publisher so another service called CustomerSystem to subscribe.

### Follow the three steps to set up kafka
1. download binary package: https://www.conduktor.io/kafka/how-to-install-apache-kafka-on-mac
2. start the zookeeper by cmd: bin/zookeeper-server-start.sh config/zookeeper.properties
3. start kafka service by cmd: bin/kafka-server-start.sh config/server.properties
Producer -> Queue -> Consumer

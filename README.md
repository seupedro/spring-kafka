<p align="center">
  <a href="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/320px-Spring_Framework_Logo_2018.svg.png"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/320px-Spring_Framework_Logo_2018.svg.png" style="height: 100px" alt="SpringLogo"></a>
</p>
<p align="center">
    <em>Spring-Kafka project</em>
</p>

---

### Requirements ✅

- Kafka 2.8.0
- Java 11
- Gradle 7.1

### Kafka setup ⚙️
 
 - Download latest kafka from here.
 - Extract and navigate to the extracted folder using terminal
 - Edit the server.properties file uncommenting the line 31, should be like this `listeners=PLAINTEXT://localhost:9092`.
 - Now go ahead and start zookeeper $ `./bin/zookeeper-server-start.sh config/zookeeper.properties`
 - And start kafka as well $ `./bin/kafka-server-start.sh config/server.properties`
 - Now you're ready to go! 

##### Quick commands 

Create new topic:
 `./bin/kafka-topics.sh --create --topic novice-players --bootstrap-server localhost:9092`

Write new events:
`./bin/kafka-console-producer.sh --topic novice-players --bootstrap-server localhost:9092`

Read events:
`bin/kafka-console-consumer.sh --topic novice-players --from-beginning --bootstrap-server localhost:9092`

### Running the App 🚀

##### Using gradle
- Go to the project folder
- Execute on terminal:
`gradle run`

##### Using jar
- Go to the project folder
- Execute the command to generate a JAR using gradle: `gradle task bootJar`
- Run the JAR with following command: `java -jar build/libs/spring*.jar`


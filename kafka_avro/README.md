# Description
`ShakespeareProducer` reads Shakespeare's work from `resources/datasets/shakespeare`. It then produces records of type {work} : {line} to the **shakespeare_topic**

`ShakespeareConverterConsumerProducer` consumes messages with format **{work} : {line}** from **shakespeare_topic** and produces records of format **{work, year} : {line_numer, line}** to the topic **shakespeare_avro_topic**

`ShakespeareAvroConsumer` reads messages from **shakespeare_avro_topic** and display them on screen.

##### Example:
{Julius Caesar :   4925  To part the glories of this happy day.}

{"work": "Julius Caesar", "year": 1599} : {"line_number": 4925, "line": "To part the glories of this happy day."}

#### Tests
I created FunctionalTest.java which contains:
* A test to run ShakespeareProducer
* A test to run ShakespeareConverterConsumerProducer
* A test to run ShakespeareAvroConsumer


               +----------------------+                          
               | /dir containing      |                          
               | Shakespeare dataset  |                          
               +----------------------+                          
                          |                                      
                          |                                      
                          |                                      
               +----------------------+                          
               | ShakespeareProducer  |                          
               |                      |                          
               +----------------------+                          
                          |                                      
                          |  Kafka Topic:  shakespeare_topic     
                          |                                      
        +------------------------------------+                   
        |ShakespeareConverterConsumerProducer|                   
        |                                    |                   
        +------------------------------------+                   
                          |                                      
                          |   Kafka Topic: shakespeare_avro_topic
                          |                                      
               +------------------------+                        
               |ShakespeareAvroConsumer |                        
               |                        |                        
               +------------------------+                        


#### How generate Java classes from avro:
$ mvn generate-sources

#### Consume from shakespeare_topic:
$ kafka-console-consumer --bootstrap-server broker101:9092 --from-beginning --topic shakespeare_topic --property print.key=true

#### Delete topic
$ kafka-topics --zookeeper zookeeper1:2181 --delete --topic shakespeare_topic
{"work": "Julius Caesar", "year": 1599} : {"line_number": 4911, "line": "All the conspirators, save only he,"}
Using the console to consume from the producer:
$ kafka-console-consumer \
--bootstrap-server broker101:9092 \
--from-beginning \
--topic hello_world_topic \
--property print.key=true

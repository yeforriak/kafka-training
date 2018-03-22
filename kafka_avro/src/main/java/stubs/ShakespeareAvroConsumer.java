package stubs;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import stubs.model.ShakespeareKey;
import stubs.model.ShakespeareValue;

public class ShakespeareAvroConsumer {
	private final Logger log = Logger.getLogger(this.getClass());

	private KafkaConsumer<ShakespeareKey, ShakespeareValue> consumer;

	public ShakespeareAvroConsumer(String topic) {
		Properties props = new Properties();
		props.put(BOOTSTRAP_SERVERS_CONFIG, "broker101:9092");
		props.put(GROUP_ID_CONFIG, randomUUID().toString());
		props.put(KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
		props.put(VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
		props.put(AUTO_OFFSET_RESET_CONFIG, "earliest");

		props.put("schema.registry.url", "http://schemaregistry1:8081");
		props.put("specific.avro.reader", "true");

		this.consumer = new KafkaConsumer<ShakespeareKey, ShakespeareValue>(props);
		this.consumer.subscribe(asList(topic));
	}

	public void consume() {
		try {
			while (true) {
				this.consumer.poll(100)
						.forEach(record -> log.info(format("Read %s : %s", record.key(), record.value())));
			}
		} finally {
			this.consumer.close();
		}
	}
}

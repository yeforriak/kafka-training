package stubs;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Arrays.asList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import static java.util.UUID.randomUUID;
import static java.lang.String.format;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

public class HelloConsumer {
	private static final int POLL_INTERVAL_MS = 100;
	private final Logger log = Logger.getLogger(this.getClass());

	private List<String> topics;
	private KafkaConsumer<String, String> consumer;

	public HelloConsumer(String... topics) {
		this.topics = new ArrayList<String>(asList(topics));
		Properties properties = new Properties();
		properties.put(BOOTSTRAP_SERVERS_CONFIG, "broker101:9092");
		properties.put(GROUP_ID_CONFIG, randomUUID().toString());
		properties.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
		this.consumer = new KafkaConsumer<String, String>(properties);
	}

	public void consume(int numberOfPolls) {
		this.consumer.subscribe(this.topics);
		for (int i = 0; i < numberOfPolls; i++ ) {
			log.info(format("Poll number %d", numberOfPolls));
			ConsumerRecords<String, String> records = consumer.poll(POLL_INTERVAL_MS);
			for (ConsumerRecord<String, String> record : records) {
	             log.info(format("partition = %d, offset = %d, key = %s, value = %s", record.partition(), record.offset(), record.key(), record.value())); 
			}
		}
	}
	
	public Map<Integer,Long> consume(int numberOfPolls, boolean trackOffset) {
        Map<Integer, Long> offsets = new HashMap<>();
		this.consumer.subscribe(this.topics);
		for (int i = 0; i < numberOfPolls; i++ ) {
			log.info(format("Poll number %d", numberOfPolls));
			ConsumerRecords<String, String> records = consumer.poll(POLL_INTERVAL_MS);
			for (ConsumerRecord<String, String> record : records) {
				 offsets.put(record.partition(), record.offset());
	             log.info(format("partition = %d, offset = %d, key = %s, value = %s", record.partition(), record.offset(), record.key(), record.value())); 
			}
		}
		
		return offsets;
	}

	public void consume() {
		this.consume(1);
	}
	
}

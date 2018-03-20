package stubs;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;


public class HelloProducer {
	private final Logger log = Logger.getLogger(this.getClass());
	private KafkaProducer<String, String> kafkaProducer;
	private String topic;
	
	public HelloProducer(String topic) {
		this.topic = topic;
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "broker101:9092");
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		this.kafkaProducer = new KafkaProducer<String, String>(properties);		
	}
	
	
	public void produce(String key, String value) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(this.topic, key, value);
		this.kafkaProducer.send(record);
		log.info(String.format("Sent %s, %s", key, value));
	}
	
}
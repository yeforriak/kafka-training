package stubs;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import static stubs.transformer.ShakespeareKeyTransformer.shakespeareKeyFrom;
import static stubs.transformer.ShakespeareValueTransformer.shakespeareValueFrom;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import stubs.model.ShakespeareKey;
import stubs.model.ShakespeareValue;

public class ShakespeareConverterConsumerProducer {
	private final Logger log = Logger.getLogger(this.getClass());

	private final KafkaConsumer<String, String> consumer;
	private final KafkaProducer<ShakespeareKey, ShakespeareValue> producer;
	private final String outputTopic;

	public ShakespeareConverterConsumerProducer(String inputTopic, String outputTopic) {
		this.consumer = createConsumer(inputTopic);
		this.producer = createProducer();
		this.outputTopic = outputTopic;
	}

	private KafkaConsumer<String, String> createConsumer(String topic) {
		Properties props = new Properties();
		props.put(BOOTSTRAP_SERVERS_CONFIG, "broker101:9092");
		props.put(GROUP_ID_CONFIG, randomUUID().toString());
		props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(AUTO_OFFSET_RESET_CONFIG, "earliest");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(asList(topic));
		return consumer;
	}

	private KafkaProducer<ShakespeareKey, ShakespeareValue> createProducer() {
		Properties props = new Properties();
		props.put(BOOTSTRAP_SERVERS_CONFIG, "broker101:9092");
		props.put(KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
		props.put(VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);

		props.put("schema.registry.url", "http://schemaregistry1:8081");

		return new KafkaProducer<>(props);
	}

	public void run() {
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			records.forEach(record -> {
				ShakespeareKey key = shakespeareKeyFrom(record.key());
				ShakespeareValue value = shakespeareValueFrom(record.value());
				log.info(format("Producing %s : %s", key, value));

				producer.send(new ProducerRecord<ShakespeareKey, ShakespeareValue>(this.outputTopic, key, value));
			});
		}
	}
}

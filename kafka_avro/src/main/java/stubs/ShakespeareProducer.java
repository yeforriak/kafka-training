package stubs;

import static java.lang.String.format;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import stubs.file.parser.FilesParser;

public class ShakespeareProducer {
	private final Logger log = Logger.getLogger(this.getClass());
	private final static String INPUT_PATH = "datasets/shakespeare";

	private final FilesParser fileParser = new FilesParser(INPUT_PATH);
	private final String topic;
	private final KafkaProducer<String, String> producer;

	public ShakespeareProducer(String topic) {
		this.topic = topic;
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker101:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		this.producer = new KafkaProducer<String, String>(props);
	}

	public void produce() {
		fileParser.parse().forEach(
				(file, contents) -> contents.forEach(line -> {
					log.info(format("Add %s : %s", file, line));
					this.producer.send(new ProducerRecord<String, String>(this.topic, file, line));
		}));
	}

}

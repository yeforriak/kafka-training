package stubs;

import org.junit.Ignore;
import org.junit.Test;

public class FunctionalTest {
	private static final String shakespeareTopic = "shakespeare_topic";
	private static final String shakespeareAvroTopic = "shakespeare_avro_topic";

	/*
	 * Reads Shakespeare's work from resources/datasets/shakespeare and produce the records to shakespeare_topic 
	 */
	@Ignore
	@Test
	public void shakespeareProducer() {
		ShakespeareProducer shakespeareProducer = new ShakespeareProducer(shakespeareTopic);
		shakespeareProducer.produce();
	}

	/*
	 * Reads indefinitely from shakespeare_topic and transform the messages into { @ShakespeareKey : @ShakespeareValue }
	 * then puts those records into shakespeare_avro_topic
	 */
	@Ignore
	@Test
	public void shakespeareConsumerAvroProducer() {
		ShakespeareConverterConsumerProducer shakespeareConverterConsumerProducer = new ShakespeareConverterConsumerProducer(
				shakespeareTopic, shakespeareAvroTopic);
		shakespeareConverterConsumerProducer.run();
	}
	
	/*
	 * Reads from shakespeare_avro_topic and outputs the records on screen
	 */
	@Ignore
	@Test
	public void shakespeareAvroConsumer() {
		ShakespeareAvroConsumer shakespeareAvroConsumer = new ShakespeareAvroConsumer(shakespeareAvroTopic);
		shakespeareAvroConsumer.consume();
	}
}

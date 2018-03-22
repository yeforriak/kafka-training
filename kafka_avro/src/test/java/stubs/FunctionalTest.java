package stubs;

import org.junit.Test;

public class FunctionalTest {
	private static final String shakespeareTopic = "shakespeare_topic";
	private static final String shakespeareAvroTopic = "shakespeare_avro_topic";

	@Test
	public void shakespeareProducer() {
		ShakespeareProducer shakespeareProducer = new ShakespeareProducer(shakespeareTopic);
		shakespeareProducer.produce();
	}

	@Test
	public void shakespeareConsumerAvroProducer() {
		ShakespeareConverterConsumerProducer shakespeareConverterConsumerProducer = new ShakespeareConverterConsumerProducer(
				shakespeareTopic, shakespeareAvroTopic);
		shakespeareConverterConsumerProducer.run();
	}

	@Test
	public void shakespeareAvroConsumer() {
		ShakespeareAvroConsumer shakespeareAvroConsumer = new ShakespeareAvroConsumer(shakespeareAvroTopic);
		shakespeareAvroConsumer.consume();
	}
}

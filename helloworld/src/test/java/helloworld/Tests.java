package helloworld;

import static org.junit.Assert.*;
import static java.lang.String.format;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.RecordMetadata;

import static org.apache.kafka.clients.admin.AdminClientConfig.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import stubs.HelloConsumer;
import stubs.HelloProducer;

public class Tests {
	private final AdminClient adminClient = adminClient();
	private final List<String> createdTopics = new ArrayList<String>();

	@Before
	public void tearDown() {
		this.adminClient.deleteTopics(createdTopics);
	}

	/* Write a Consumer which reads all data from a Topic each time it starts */
	@Test
	public void consumerThatReadFromTheStart() {

		String topicName = "read-from-start-topic";

		createTopic(topicName, 1, (short) 1);

		HelloProducer producer = new HelloProducer(topicName);

		IntStream.rangeClosed(1, 100)
				.forEach(i -> producer.produce(format("firstKey", i), format("firstkey-value-%s", i)));

		HelloConsumer consumer = new HelloConsumer(topicName);
		consumer.consume();
	}

	private void createTopic(String name, int partition, short replication) {
		NewTopic topic = new NewTopic(name, partition, replication);
		System.out.printf("Topic created, name: %s, partitions:%s", topic.name(), topic.numPartitions());
		this.adminClient.createTopics(asList(topic));
		this.createdTopics.add(name);
	}

	private AdminClient adminClient() {
		Properties config = new Properties();
		config.put(BOOTSTRAP_SERVERS_CONFIG, "broker101:9092");
		return AdminClient.create(config);
	}
	
	private void waitFor(List<Future> futures) {
		
	}
}

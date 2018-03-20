package helloworld;

import static org.junit.Assert.*;
import static java.lang.String.format;


import java.util.stream.IntStream;

import org.junit.Test;

import stubs.HelloProducer;

public class Tests {

	private final String topic = "hello_world_topic";
	
	@Test
	public void test() {
		HelloProducer producer = new HelloProducer(topic);
	
		IntStream.rangeClosed(0, 5)
			.forEach( i -> producer.produce(format("Key-%s", i), format("Value-%s", i)));
	}

}

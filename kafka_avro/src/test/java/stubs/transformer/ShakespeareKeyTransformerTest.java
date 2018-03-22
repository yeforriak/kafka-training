package stubs.transformer;

import static org.junit.Assert.assertTrue;
import static stubs.transformer.ShakespeareKeyTransformer.shakespeareKeyFrom;

import org.junit.Test;

import stubs.model.ShakespeareKey;

public class ShakespeareKeyTransformerTest {

	@Test
	public void transformKey() {
		String key = "Hamlet";
		ShakespeareKey shakespeareKey = shakespeareKeyFrom(key);
		assertTrue(shakespeareKey.getWork().equals(key));
		assertTrue(shakespeareKey.getYear().equals(1600));
	}
}

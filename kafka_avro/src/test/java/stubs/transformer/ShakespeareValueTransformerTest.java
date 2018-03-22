package stubs.transformer;

import static org.junit.Assert.assertTrue;
import static stubs.transformer.ShakespeareValueTransformer.shakespeareValueFrom;

import org.junit.Test;

import stubs.model.ShakespeareValue;

public class ShakespeareValueTransformerTest {
	@Test
	public void testValueTransformer() {
		String line = "   936  I saw him once; he was a goodly king.";
		ShakespeareValue value = shakespeareValueFrom(line);
		assertTrue(value.getLineNumber().equals(936));
		assertTrue(value.getLine().equals("I saw him once; he was a goodly king."));
	}
}

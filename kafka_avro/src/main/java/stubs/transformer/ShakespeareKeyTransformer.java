package stubs.transformer;

import java.util.HashMap;
import java.util.Map;

import stubs.model.ShakespeareKey;

public class ShakespeareKeyTransformer {
	private static final Map<String, Integer> workYear = new HashMap<String, Integer>(){{
		put("Hamlet", 1600);
	    put("Julius Caesar", 1599);
        put("Macbeth", 1605);
        put("Merchant of Venice", 1596);
        put("Othello", 1604);
        put("Romeo and Juliet", 1594);
	}};

	public static ShakespeareKey shakespeareKeyFrom(String key) {
		return new ShakespeareKey(key, workYear.get(key));
	}

}

package me.loveqoo.mp3.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static me.loveqoo.mp3.Bytes.BIT_0;
import static me.loveqoo.mp3.Bytes.BIT_1;

public class Emphasis {

	public enum Type {
		EMPHASIS_NONE, EMPHASIS_50_15_MS, EMPHASIS_RESERVED, EMPHASIS_CCIT_J_17
	}

	static public final int MASK_EMPHASIS = BIT_1 | BIT_0;

	static private final Map<Integer, Type> EMPHASIS_MAP;

	static {
		Map<Integer, Type> emphasisMap = new HashMap<>(4);
		emphasisMap.put(0x00, Type.EMPHASIS_NONE);
		emphasisMap.put(0x01, Type.EMPHASIS_50_15_MS);
		emphasisMap.put(0x02, Type.EMPHASIS_RESERVED);
		emphasisMap.put(0x03, Type.EMPHASIS_CCIT_J_17);
		EMPHASIS_MAP = Collections.unmodifiableMap(emphasisMap);
	}

	public static Function<byte[], Optional<Type>> FIND_EMPHASIS = (header) -> {
		assert (header.length == 4);
		int index = (header[3] & MASK_EMPHASIS);
		return EMPHASIS_MAP.containsKey(index) ? Optional.of(EMPHASIS_MAP.get(index)) : Optional.empty();
	};
}

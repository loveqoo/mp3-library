package me.loveqoo.mp3.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static me.loveqoo.mp3.Bytes.BIT_3;
import static me.loveqoo.mp3.Bytes.BIT_4;

public class Version {

	/**
	 * MPEG Version
	 */
	public enum Type {
		VERSION_1, VERSION_2, VERSION_2_5, VERSION_RESERVED
	}

	private static Map<Integer, Version.Type> VERSION_MAP;

	static {
		Map<Integer, Version.Type> map = new HashMap<>(4);
		map.put(0, Version.Type.VERSION_2_5);
		map.put(1, Version.Type.VERSION_RESERVED);
		map.put(2, Version.Type.VERSION_2);
		map.put(3, Version.Type.VERSION_1);
		VERSION_MAP = Collections.unmodifiableMap(map);
	}

	public static Function<byte[], Optional<Version.Type>> FIND_VERSION = (header) -> {
		assert(header.length == 4);
		int index = ((header[1] & (BIT_4 | BIT_3)) >> 3);
		return VERSION_MAP.containsKey(index) ? Optional.of(VERSION_MAP.get(index)) : Optional.empty();
	};
}

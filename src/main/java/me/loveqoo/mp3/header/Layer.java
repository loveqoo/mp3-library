package me.loveqoo.mp3.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static me.loveqoo.mp3.Bytes.BIT_1;
import static me.loveqoo.mp3.Bytes.BIT_2;

public class Layer {

	public enum Type {
		LAYER_1, LAYER_2, LAYER_3, LAYER_RESERVED;
	}

	private static final Map<Integer, Layer.Type> LAYER_MAP;

	static {
		Map<Integer, Layer.Type> layerMap = new HashMap<>(4);
		layerMap.put(0, Layer.Type.LAYER_RESERVED);
		layerMap.put(1, Layer.Type.LAYER_3);
		layerMap.put(2, Layer.Type.LAYER_2);
		layerMap.put(3, Layer.Type.LAYER_1);
		LAYER_MAP = Collections.unmodifiableMap(layerMap);
	}

	public static Function<byte[], Optional<Type>> FIND_LAYER = (header) -> {
		assert (header.length == 4);
		int index = ((header[1] & (BIT_2 | BIT_1)) >> 1);
		return LAYER_MAP.containsKey(index) ? Optional.of(LAYER_MAP.get(index)) : Optional.empty();
	};
}

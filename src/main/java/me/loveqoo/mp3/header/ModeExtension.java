package me.loveqoo.mp3.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static me.loveqoo.mp3.Bytes.BIT_4;
import static me.loveqoo.mp3.Bytes.BIT_5;

public class ModeExtension {

	public enum Type {
		BANDS_4_TO_31, BANDS_8_TO_31, BANDS_12_TO_31, BANDS_16_TO_31, MS_ON_INTENSITY_ON, MS_OFF_INTENSITY_OFF, MS_ON_INTENSITY_OFF, MS_OFF_INTENSITY_ON
	}

	static public final int MASK_MODE_EXTENSION = BIT_5 | BIT_4;

	private static final Map<Integer, Type> MODE_EXTENSION_MAP;

	static {
		Map<Integer, Type> modeExtensionMap = new HashMap<>(12);
		// -- Layer 1
		modeExtensionMap.put(0x06, Type.BANDS_4_TO_31);
		modeExtensionMap.put(0x16, Type.BANDS_8_TO_31);
		modeExtensionMap.put(0x26, Type.BANDS_12_TO_31);
		modeExtensionMap.put(0x36, Type.BANDS_16_TO_31);
		// -- Layer 2
		modeExtensionMap.put(0x04, Type.BANDS_4_TO_31);
		modeExtensionMap.put(0x14, Type.BANDS_8_TO_31);
		modeExtensionMap.put(0x24, Type.BANDS_12_TO_31);
		modeExtensionMap.put(0x34, Type.BANDS_16_TO_31);
		// -- Layer 3
		modeExtensionMap.put(0x02, Type.MS_OFF_INTENSITY_OFF);
		modeExtensionMap.put(0x12, Type.MS_OFF_INTENSITY_ON);
		modeExtensionMap.put(0x22, Type.MS_ON_INTENSITY_OFF);
		modeExtensionMap.put(0x32, Type.MS_ON_INTENSITY_ON);
		MODE_EXTENSION_MAP = Collections.unmodifiableMap(modeExtensionMap);
	}

	public static Function<byte[], Optional<Type>> FIND_MODE_EXTENSION = (header) -> {
		assert (header.length == 4);
		int index = (header[3] & MASK_MODE_EXTENSION | header[1] & Layer.MASK_LAYER);
		return MODE_EXTENSION_MAP.containsKey(index) ? Optional.of(MODE_EXTENSION_MAP.get(index)) : Optional.empty();
	};
}

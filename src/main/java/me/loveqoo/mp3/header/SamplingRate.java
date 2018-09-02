package me.loveqoo.mp3.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static me.loveqoo.mp3.Bytes.*;

public class SamplingRate {

	public enum Type {
		SAMPLING_RATE_RESERVED, SAMPLING_RATE_8000, SAMPLING_RATE_11025, SAMPLING_RATE_12000, SAMPLING_RATE_16000,
		SAMPLING_RATE_22050, SAMPLING_RATE_24000, SAMPLING_RATE_32000, SAMPLING_RATE_44100, SAMPLING_RATE_48000;
	}

	static private final Map<Integer, Type> SAMPLING_RATE_MAP;

	static {
		Map<Integer, Type> samplingRateMap = new HashMap<>(12);
		// -- MPEG 1
		samplingRateMap.put(0x03, Type.SAMPLING_RATE_44100);
		samplingRateMap.put(0x13, Type.SAMPLING_RATE_48000);
		samplingRateMap.put(0x23, Type.SAMPLING_RATE_32000);
		samplingRateMap.put(0x33, Type.SAMPLING_RATE_RESERVED);
		// -- MPEG 2
		samplingRateMap.put(0x02, Type.SAMPLING_RATE_22050);
		samplingRateMap.put(0x12, Type.SAMPLING_RATE_24000);
		samplingRateMap.put(0x22, Type.SAMPLING_RATE_16000);
		samplingRateMap.put(0x32, Type.SAMPLING_RATE_RESERVED);
		// -- MPEG 2.5
		samplingRateMap.put(0x00, Type.SAMPLING_RATE_11025);
		samplingRateMap.put(0x10, Type.SAMPLING_RATE_12000);
		samplingRateMap.put(0x20, Type.SAMPLING_RATE_8000);
		samplingRateMap.put(0x30, Type.SAMPLING_RATE_RESERVED);
		SAMPLING_RATE_MAP = Collections.unmodifiableMap(samplingRateMap);
	}

	public static Function<byte[], Optional<Type>> FIND_SAMPLING_RATE = (header) -> {
		assert (header.length == 4);

		int MASK_SAMPLING_RATE = BIT_3 | BIT_2;
		int SAMPLING_RATE = (header[2] & MASK_SAMPLING_RATE) << 2;

		int MASK_VERSION = BIT_4 | BIT_3;
		int VERSION = (header[1] & MASK_VERSION) >> 3;

		int index = SAMPLING_RATE | VERSION;
		return SAMPLING_RATE_MAP.containsKey(index) ? Optional.of(SAMPLING_RATE_MAP.get(index)) : Optional.empty();
	};

}

package me.loveqoo.mp3.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static me.loveqoo.mp3.Bytes.BIT_6;
import static me.loveqoo.mp3.Bytes.BIT_7;

public class ChannelMode {

	public enum Type {
		STEREO, JOINT_STEREO, DUAL_CHANNEL, SINGLE_CHANNEL
	}

	static final int MASK_CHANNEL_MODE = BIT_7 | BIT_6;

	private static final Map<Integer, Type> CHANNEL_MODE_MAP;

	static {
		Map<Integer, Type> channelModeMap = new HashMap<>(4);
		channelModeMap.put(0x00, Type.STEREO);
		channelModeMap.put(0x01, Type.JOINT_STEREO);
		channelModeMap.put(0x02, Type.DUAL_CHANNEL);
		channelModeMap.put(0x03, Type.SINGLE_CHANNEL);
		CHANNEL_MODE_MAP = Collections.unmodifiableMap(channelModeMap);
	}

	public static Function<byte[], Optional<Type>> FIND_CHANNEL_MODE = (header) -> {
		assert (header.length == 4);
		int index = ((header[3] & MASK_CHANNEL_MODE) >>> 6);
		return CHANNEL_MODE_MAP.containsKey(index) ? Optional.of(CHANNEL_MODE_MAP.get(index)) : Optional.empty();
	};
}

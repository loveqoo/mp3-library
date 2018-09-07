package me.loveqoo.mp3.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static me.loveqoo.mp3.Bytes.*;

public class BitRate {

	public enum Type {
		BITRATE_RESERVED, BITRATE_FREE, BITRATE_8, BITRATE_16, BITRATE_24, BITRATE_32,
		BITRATE_40, BITRATE_48, BITRATE_56, BITRATE_64, BITRATE_80, BITRATE_96, BITRATE_112,
		BITRATE_128, BITRATE_144, BITRATE_160, BITRATE_176, BITRATE_192, BITRATE_224,
		BITRATE_256, BITRATE_288, BITRATE_320, BITRATE_352, BITRATE_384, BITRATE_416,
		BITRATE_448
	}

	public static int MASK_BITRATE = BIT_7 | BIT_6 | BIT_5 | BIT_4;

	private static final Map<Integer, BitRate.Type> BITRATE_MAP;

	static {
		// [bitRate:4][version:2][layer:2]
		Map<Integer, BitRate.Type> bitrateMap = new HashMap<>(16 * 9);
		// -- MPEG 1 Layer 1 (1111): F
		bitrateMap.put(0x0F, BitRate.Type.BITRATE_FREE);    // 00001111
		bitrateMap.put(0x1F, BitRate.Type.BITRATE_32);        // 00011111
		bitrateMap.put(0x2F, BitRate.Type.BITRATE_64);        // 00101111
		bitrateMap.put(0x3F, BitRate.Type.BITRATE_96);        // 00111111
		bitrateMap.put(0x4F, BitRate.Type.BITRATE_128);        // 01001111
		bitrateMap.put(0x5F, BitRate.Type.BITRATE_160);        // 01011111
		bitrateMap.put(0x6F, BitRate.Type.BITRATE_192);        // 01101111
		bitrateMap.put(0x7F, BitRate.Type.BITRATE_224);        // 01111111
		bitrateMap.put(0x8F, BitRate.Type.BITRATE_256);        // 10001111
		bitrateMap.put(0x9F, BitRate.Type.BITRATE_288);        // 10011111
		bitrateMap.put(0xAF, BitRate.Type.BITRATE_320);        // 10101111
		bitrateMap.put(0xBF, BitRate.Type.BITRATE_352);        // 10111111
		bitrateMap.put(0xCF, BitRate.Type.BITRATE_384);        // 11001111
		bitrateMap.put(0xDF, BitRate.Type.BITRATE_416);        // 11011111
		bitrateMap.put(0xEF, BitRate.Type.BITRATE_448);        // 11101111
		bitrateMap.put(0xFF, BitRate.Type.BITRATE_RESERVED);// 11111111
		// -- MPEG 1 Layer 2 (1110): E
		bitrateMap.put(0x0E, BitRate.Type.BITRATE_FREE);    // 00001110
		bitrateMap.put(0x1E, BitRate.Type.BITRATE_32);        // 00011110
		bitrateMap.put(0x2E, BitRate.Type.BITRATE_48);        // 00101110
		bitrateMap.put(0x3E, BitRate.Type.BITRATE_56);        // 00111110
		bitrateMap.put(0x4E, BitRate.Type.BITRATE_64);        // 01001110
		bitrateMap.put(0x5E, BitRate.Type.BITRATE_80);        // 01011110
		bitrateMap.put(0x6E, BitRate.Type.BITRATE_96);        // 01101110
		bitrateMap.put(0x7E, BitRate.Type.BITRATE_112);        // 01111110
		bitrateMap.put(0x8E, BitRate.Type.BITRATE_128);        // 10001110
		bitrateMap.put(0x9E, BitRate.Type.BITRATE_160);        // 10011110
		bitrateMap.put(0xAE, BitRate.Type.BITRATE_192);        // 10101110
		bitrateMap.put(0xBE, BitRate.Type.BITRATE_224);        // 10111110
		bitrateMap.put(0xCE, BitRate.Type.BITRATE_256);        // 11001110
		bitrateMap.put(0xDE, BitRate.Type.BITRATE_320);        // 11011110
		bitrateMap.put(0xEE, BitRate.Type.BITRATE_384);        // 11101110
		bitrateMap.put(0xFE, BitRate.Type.BITRATE_RESERVED);// 11111110
		// -- MPEG 1 Layer 3 (1101): D
		bitrateMap.put(0x0D, BitRate.Type.BITRATE_FREE);    // 00001101
		bitrateMap.put(0x1D, BitRate.Type.BITRATE_32);        // 00011101
		bitrateMap.put(0x2D, BitRate.Type.BITRATE_40);        // 00101101
		bitrateMap.put(0x3D, BitRate.Type.BITRATE_48);        // 00111101
		bitrateMap.put(0x4D, BitRate.Type.BITRATE_56);        // 01001101
		bitrateMap.put(0x5D, BitRate.Type.BITRATE_64);        // 01011101
		bitrateMap.put(0x6D, BitRate.Type.BITRATE_80);        // 01101101
		bitrateMap.put(0x7D, BitRate.Type.BITRATE_96);        // 01111101
		bitrateMap.put(0x8D, BitRate.Type.BITRATE_112);        // 10001101
		bitrateMap.put(0x9D, BitRate.Type.BITRATE_128);        // 10011101
		bitrateMap.put(0xAD, BitRate.Type.BITRATE_160);        // 10101101
		bitrateMap.put(0xBD, BitRate.Type.BITRATE_192);        // 10111101
		bitrateMap.put(0xCD, BitRate.Type.BITRATE_224);        // 11001101
		bitrateMap.put(0xDD, BitRate.Type.BITRATE_256);        // 11011101
		bitrateMap.put(0xED, BitRate.Type.BITRATE_320);        // 11101101
		bitrateMap.put(0xFD, BitRate.Type.BITRATE_RESERVED);// 11111101
		// -- MPEG 2 Layer 1 (1011): B
		bitrateMap.put(0x0B, BitRate.Type.BITRATE_FREE);    // 00001011
		bitrateMap.put(0x1B, BitRate.Type.BITRATE_32);        // 00011011
		bitrateMap.put(0x2B, BitRate.Type.BITRATE_48);        // 00101011
		bitrateMap.put(0x3B, BitRate.Type.BITRATE_56);        // 00111011
		bitrateMap.put(0x4B, BitRate.Type.BITRATE_64);        // 01001011
		bitrateMap.put(0x5B, BitRate.Type.BITRATE_80);        // 01011011
		bitrateMap.put(0x6B, BitRate.Type.BITRATE_96);        // 01101011
		bitrateMap.put(0x7B, BitRate.Type.BITRATE_112);        // 01111011
		bitrateMap.put(0x8B, BitRate.Type.BITRATE_128);        // 10001011
		bitrateMap.put(0x9B, BitRate.Type.BITRATE_144);        // 10011011
		bitrateMap.put(0xAB, BitRate.Type.BITRATE_160);        // 10101011
		bitrateMap.put(0xBB, BitRate.Type.BITRATE_176);        // 10111011
		bitrateMap.put(0xCB, BitRate.Type.BITRATE_192);        // 11001011
		bitrateMap.put(0xDB, BitRate.Type.BITRATE_224);        // 11011011
		bitrateMap.put(0xEB, BitRate.Type.BITRATE_256);        // 11101011
		bitrateMap.put(0xFB, BitRate.Type.BITRATE_RESERVED);// 11111011
		// -- MPEG 2 Layer 2 (1010): A
		bitrateMap.put(0x0A, BitRate.Type.BITRATE_FREE);    // 00001010
		bitrateMap.put(0x1A, BitRate.Type.BITRATE_8);        // 00011010
		bitrateMap.put(0x2A, BitRate.Type.BITRATE_16);        // 00101010
		bitrateMap.put(0x3A, BitRate.Type.BITRATE_24);        // 00111010
		bitrateMap.put(0x4A, BitRate.Type.BITRATE_32);        // 01001010
		bitrateMap.put(0x5A, BitRate.Type.BITRATE_40);        // 01011010
		bitrateMap.put(0x6A, BitRate.Type.BITRATE_48);        // 01101010
		bitrateMap.put(0x7A, BitRate.Type.BITRATE_56);        // 01111010
		bitrateMap.put(0x8A, BitRate.Type.BITRATE_64);        // 10001010
		bitrateMap.put(0x9A, BitRate.Type.BITRATE_80);        // 10011010
		bitrateMap.put(0xAA, BitRate.Type.BITRATE_96);        // 10101010
		bitrateMap.put(0xBA, BitRate.Type.BITRATE_112);        // 10111010
		bitrateMap.put(0xCA, BitRate.Type.BITRATE_128);        // 11001010
		bitrateMap.put(0xDA, BitRate.Type.BITRATE_144);        // 11011010
		bitrateMap.put(0xEA, BitRate.Type.BITRATE_160);        // 11101010
		bitrateMap.put(0xFA, BitRate.Type.BITRATE_RESERVED);// 11111010
		// -- MPEG 2 Layer 3 (1001): 9
		bitrateMap.put(0x09, BitRate.Type.BITRATE_FREE);    // 00001001
		bitrateMap.put(0x19, BitRate.Type.BITRATE_8);        // 00011001
		bitrateMap.put(0x29, BitRate.Type.BITRATE_16);        // 00101001
		bitrateMap.put(0x39, BitRate.Type.BITRATE_24);        // 00111001
		bitrateMap.put(0x49, BitRate.Type.BITRATE_32);        // 01001001
		bitrateMap.put(0x59, BitRate.Type.BITRATE_40);        // 01011001
		bitrateMap.put(0x69, BitRate.Type.BITRATE_48);        // 01101001
		bitrateMap.put(0x79, BitRate.Type.BITRATE_56);        // 01111001
		bitrateMap.put(0x89, BitRate.Type.BITRATE_64);        // 10001001
		bitrateMap.put(0x99, BitRate.Type.BITRATE_80);        // 10011001
		bitrateMap.put(0xA9, BitRate.Type.BITRATE_96);        // 10101001
		bitrateMap.put(0xB9, BitRate.Type.BITRATE_112);        // 10111001
		bitrateMap.put(0xC9, BitRate.Type.BITRATE_128);        // 11001001
		bitrateMap.put(0xD9, BitRate.Type.BITRATE_144);        // 11011001
		bitrateMap.put(0xE9, BitRate.Type.BITRATE_160);        // 11101001
		bitrateMap.put(0xF9, BitRate.Type.BITRATE_RESERVED);// 11111001
		// -- MPEG 2.5 Layer 1 (0011): 3
		bitrateMap.put(0x03, BitRate.Type.BITRATE_FREE);    // 00000011
		bitrateMap.put(0x13, BitRate.Type.BITRATE_32);        // 00010011
		bitrateMap.put(0x23, BitRate.Type.BITRATE_48);        // 00100011
		bitrateMap.put(0x33, BitRate.Type.BITRATE_56);        // 00110011
		bitrateMap.put(0x43, BitRate.Type.BITRATE_64);        // 01000011
		bitrateMap.put(0x53, BitRate.Type.BITRATE_80);        // 01010011
		bitrateMap.put(0x63, BitRate.Type.BITRATE_96);        // 01100011
		bitrateMap.put(0x73, BitRate.Type.BITRATE_112);        // 01110011
		bitrateMap.put(0x83, BitRate.Type.BITRATE_128);        // 10000011
		bitrateMap.put(0x93, BitRate.Type.BITRATE_144);        // 10010011
		bitrateMap.put(0xA3, BitRate.Type.BITRATE_160);        // 10100011
		bitrateMap.put(0xB3, BitRate.Type.BITRATE_176);        // 10110011
		bitrateMap.put(0xC3, BitRate.Type.BITRATE_192);        // 11000011
		bitrateMap.put(0xD3, BitRate.Type.BITRATE_224);        // 11010011
		bitrateMap.put(0xE3, BitRate.Type.BITRATE_256);        // 11100011
		bitrateMap.put(0xF3, BitRate.Type.BITRATE_RESERVED);// 11110011
		// -- MPEG 2.5 Layer 2 (0010): 2
		bitrateMap.put(0x02, BitRate.Type.BITRATE_FREE);    // 00000010
		bitrateMap.put(0x12, BitRate.Type.BITRATE_8);        // 00010010
		bitrateMap.put(0x22, BitRate.Type.BITRATE_16);        // 00100010
		bitrateMap.put(0x32, BitRate.Type.BITRATE_24);        // 00110010
		bitrateMap.put(0x42, BitRate.Type.BITRATE_32);        // 01000010
		bitrateMap.put(0x52, BitRate.Type.BITRATE_40);        // 01010010
		bitrateMap.put(0x62, BitRate.Type.BITRATE_48);        // 01100010
		bitrateMap.put(0x72, BitRate.Type.BITRATE_56);        // 01110010
		bitrateMap.put(0x82, BitRate.Type.BITRATE_64);        // 10000010
		bitrateMap.put(0x92, BitRate.Type.BITRATE_80);        // 10010010
		bitrateMap.put(0xA2, BitRate.Type.BITRATE_96);        // 10100010
		bitrateMap.put(0xB2, BitRate.Type.BITRATE_112);        // 10110010
		bitrateMap.put(0xC2, BitRate.Type.BITRATE_128);        // 11000010
		bitrateMap.put(0xD2, BitRate.Type.BITRATE_144);        // 11010010
		bitrateMap.put(0xE2, BitRate.Type.BITRATE_160);        // 11100010
		bitrateMap.put(0xF2, BitRate.Type.BITRATE_RESERVED);// 11110010
		// -- MPEG 2.5 Layer 3 (0001): 1
		bitrateMap.put(0x01, BitRate.Type.BITRATE_FREE);    // 00000001
		bitrateMap.put(0x11, BitRate.Type.BITRATE_8);        // 00010001
		bitrateMap.put(0x21, BitRate.Type.BITRATE_16);        // 00100001
		bitrateMap.put(0x31, BitRate.Type.BITRATE_24);        // 00110001
		bitrateMap.put(0x41, BitRate.Type.BITRATE_32);        // 01000001
		bitrateMap.put(0x51, BitRate.Type.BITRATE_40);        // 01010001
		bitrateMap.put(0x61, BitRate.Type.BITRATE_48);        // 01100001
		bitrateMap.put(0x71, BitRate.Type.BITRATE_56);        // 01110001
		bitrateMap.put(0x81, BitRate.Type.BITRATE_64);        // 10000001
		bitrateMap.put(0x91, BitRate.Type.BITRATE_80);        // 10010001
		bitrateMap.put(0xA1, BitRate.Type.BITRATE_96);        // 10100001
		bitrateMap.put(0xB1, BitRate.Type.BITRATE_112);        // 10110001
		bitrateMap.put(0xC1, BitRate.Type.BITRATE_128);        // 11000001
		bitrateMap.put(0xD1, BitRate.Type.BITRATE_144);        // 11010001
		bitrateMap.put(0xE1, BitRate.Type.BITRATE_160);        // 11100001
		bitrateMap.put(0xF1, BitRate.Type.BITRATE_RESERVED);// 11110001
		BITRATE_MAP = Collections.unmodifiableMap(bitrateMap);
	}

	public static Function<byte[], Optional<BitRate.Type>> FIND_BITRATE = (header) -> {
		assert (header.length == 4);

		int BITRATE = header[2] & MASK_BITRATE;

		int LAYER = (header[1] & Layer.MASK_LAYER) >>> 1;

		int VERSION = (header[1] & (BIT_4 | BIT_3)) >>> 1;

		int index = BITRATE | VERSION | LAYER;
		return BITRATE_MAP.containsKey(index) ? Optional.of(BITRATE_MAP.get(index)) : Optional.empty();
	};
}

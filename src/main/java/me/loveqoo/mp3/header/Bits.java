package me.loveqoo.mp3.header;

import java.util.Optional;
import java.util.function.Function;

import static me.loveqoo.mp3.Bytes.*;

public class Bits {

	static final int MASK_PROTECTION_BIT = BIT_0;
	static final int MASK_PADDING_BIT = BIT_1;
	static final int MASK_PRIVATE_BIT = BIT_0;
	static final int MASK_COPYRIGHT_BIT = BIT_3;
	static final int MASK_ORIGINAL_BIT = BIT_2;

	public static Function<byte[], Optional<Boolean>> HAS_PROTECTION_BIT = (header) -> {
		assert (header.length == 4);
		return Optional.of((header[1] & MASK_PROTECTION_BIT) != 0);
	};

	public static Function<byte[], Optional<Boolean>> HAS_PADDING_BIT = (header) -> {
		assert (header.length == 4);
		return Optional.of((header[2] & MASK_PADDING_BIT) != 0);
	};

	public static Function<byte[], Optional<Boolean>> HAS_PRIVATE_BIT = (header) -> {
		assert (header.length == 4);
		return Optional.of((header[2] & MASK_PRIVATE_BIT) != 0);
	};

	public static Function<byte[], Optional<Boolean>> HAS_COPY_RIGHT_BIT = (header) -> {
		assert (header.length == 4);
		return Optional.of((header[3] & MASK_COPYRIGHT_BIT) != 0);
	};

	public static Function<byte[], Optional<Boolean>> HAS_ORIGINAL_BIT = (header) -> {
		assert (header.length == 4);
		return Optional.of((header[3] & MASK_ORIGINAL_BIT) != 0);
	};
}

package me.loveqoo.mp3;

import org.junit.Test;
import qoo.lib.util.Bytes;

import java.nio.channels.FileChannel;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static me.loveqoo.mp3.MP3Util.*;

public class MP3Test {

	@Test
	public void findHead() {
		String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("sample_vbr_vbri_16_mono.mp3")).getFile();

		Function<FileChannel, Optional<Long>> WRAPPED_HEADER_POS = HOLD_POSITION(HEADER_POSITION);
		Function<FileChannel, Optional<byte[]>> WRAPPED_HEADER_RAW = HOLD_POSITION(HEADER_RAW);

		WithFile.work(filePath, HOLD_POSITION((ch) -> {
			Optional<Long> headerPos = WRAPPED_HEADER_POS.apply(ch);
			assert (headerPos.isPresent());
			CHANNEL_POSITION_SET.accept(ch, headerPos.get());
			Optional<byte[]> rawHeader = WRAPPED_HEADER_RAW.apply(ch);
			assert (rawHeader.isPresent());
			for (byte raw : rawHeader.get()) {
				System.out.println(Bytes.byteToHex(raw));
			}
			return Optional.<Boolean>empty();
		}));

	}
}

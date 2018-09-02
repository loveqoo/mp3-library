package me.loveqoo.mp3;

import me.loveqoo.mp3.header.BitRate;
import me.loveqoo.mp3.header.Layer;
import me.loveqoo.mp3.header.SamplingRate;
import me.loveqoo.mp3.header.Version;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static me.loveqoo.mp3.MP3Util.*;

public class MP3Test {

	private String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("sample_vbr_vbri_16_mono.mp3")).getFile();

	@Test
	public void findHead() {
		Optional<byte[]> rawHeader = WithFile.work(filePath, (ch) -> {
			Optional<Long> headerPos = HOLD_POSITION(GET_HEADER_POSITION).apply(ch);
			assert (headerPos.isPresent());
			return HOLD_POSITION(GET_HEADER_RAW).apply(ch, headerPos.get());
		});
		assert (rawHeader.isPresent());
		byte[] expect = new byte[] { (byte) 0xFF, (byte) 0xF3, (byte) 0xB9, (byte) 0xC0 };
		assert (Arrays.equals(expect, rawHeader.get()));
	}

	@Test
	public void findVersion() {
		Optional<Version.Type> version = WithFile.work(filePath, (ch) -> {
			Optional<Long> headerPos = HOLD_POSITION(GET_HEADER_POSITION).apply(ch);
			Optional<byte[]> rawHeader = HOLD_POSITION(GET_HEADER_RAW).apply(ch, headerPos.get());
			return Version.FIND_VERSION.apply(rawHeader.get());
		});
		assert (version.isPresent());
		assert (version.get() == Version.Type.VERSION_2); // 10
	}

	@Test
	public void findLayer() {
		Optional<Layer.Type> version = WithFile.work(filePath, (ch) -> {
			Optional<Long> headerPos = HOLD_POSITION(GET_HEADER_POSITION).apply(ch);
			Optional<byte[]> rawHeader = HOLD_POSITION(GET_HEADER_RAW).apply(ch, headerPos.get());
			return Layer.FIND_LAYER.apply(rawHeader.get());
		});
		assert (version.isPresent());
		assert (version.get() == Layer.Type.LAYER_3); // 01
	}

	@Test
	public void findBitRate() {
		Optional<BitRate.Type> version = WithFile.work(filePath, (ch) -> {
			Optional<Long> headerPos = HOLD_POSITION(GET_HEADER_POSITION).apply(ch);
			Optional<byte[]> rawHeader = HOLD_POSITION(GET_HEADER_RAW).apply(ch, headerPos.get());
			return BitRate.FIND_BITRATE.apply(rawHeader.get());
		});
		assert (version.isPresent());
		assert (version.get() == BitRate.Type.BITRATE_112); // 1011
	}

	@Test
	public void findSamplingRate() {
		Optional<SamplingRate.Type> samplingRate = WithFile.work(filePath, (ch) -> {
			Optional<Long> headerPos = HOLD_POSITION(GET_HEADER_POSITION).apply(ch);
			Optional<byte[]> rawHeader = HOLD_POSITION(GET_HEADER_RAW).apply(ch, headerPos.get());
			return SamplingRate.FIND_SAMPLING_RATE.apply(rawHeader.get());
		});
		assert (samplingRate.isPresent());
		assert (samplingRate.get() == SamplingRate.Type.SAMPLING_RATE_16000);
	}
}

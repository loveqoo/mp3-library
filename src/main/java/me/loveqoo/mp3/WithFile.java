package me.loveqoo.mp3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class WithFile {

	static public <T> Optional<T> work(String filePath, Function<FileChannel, Optional<T>> f) {
		assert(filePath != null);
		File file = new File(filePath);
		assert(file.exists());
		assert(file.isFile());
		try (FileInputStream fis = new FileInputStream(file);
			FileChannel ch = fis.getChannel()) {
			return f.apply(ch);
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static BiConsumer<FileChannel, ByteBuffer> READ_CHANNEL = (ch, buf) -> {
		try {
			ch.read(buf);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	};

	public static Function<FileChannel, Long> GET_CHANNEL_POSITION = (ch) -> {
		try {
			return ch.position();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	};

	public static BiConsumer<FileChannel, Long> SET_CHANNEL_POSITION = (ch, pos) -> {
		try {
			ch.position(pos);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	};

	public static BiFunction<FileChannel, Long, Optional<byte[]>> GET_HEADER_RAW = (ch, pos) -> {
		SET_CHANNEL_POSITION.accept(ch, pos);
		ByteBuffer buf = ByteBuffer.allocateDirect(4);
		byte[] data = new byte[4];
		READ_CHANNEL.accept(ch, buf);
		buf.flip();
		buf.get(data);
		return Optional.of(data);
	};

	public static Function<FileChannel, Optional<Long>> GET_HEADER_POSITION = (ch) -> {
		ByteBuffer buf = ByteBuffer.allocateDirect(2);
		boolean needToFind = true;
		while (needToFind) {
			READ_CHANNEL.accept(ch, buf);
			needToFind = !((buf.get(0) & 0xFF) == 0xFF && (buf.get(1) & 0xE0) == 0xE0);
			buf.clear();
			SET_CHANNEL_POSITION.accept(ch, GET_CHANNEL_POSITION.apply(ch) - 1L);
		}
		return Optional.of(GET_CHANNEL_POSITION.apply(ch) - 1L);
	};

	public static <T> Function<FileChannel, Optional<T>> HOLD_POSITION(Function<FileChannel, Optional<T>> f) {
		return (ch) -> {
			long originPos = -1L;
			try {
				originPos = GET_CHANNEL_POSITION.apply(ch);
				return f.apply(ch);
			} finally {
				if (originPos != -1L) {
					SET_CHANNEL_POSITION.accept(ch, originPos);
				}
			}
		};
	}

	public static <T, S> BiFunction<FileChannel, S, Optional<T>> HOLD_POSITION(BiFunction<FileChannel, S, Optional<T>> f) {
		return (ch, s) -> {
			long originPos = -1L;
			try {
				originPos = GET_CHANNEL_POSITION.apply(ch);
				return f.apply(ch, s);
			} finally {
				if (originPos != -1L) {
					SET_CHANNEL_POSITION.accept(ch, originPos);
				}
			}
		};
	}
}

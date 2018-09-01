package me.loveqoo.mp3;

import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.util.Optional;
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
}

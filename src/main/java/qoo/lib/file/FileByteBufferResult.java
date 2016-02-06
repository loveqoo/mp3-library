package qoo.lib.file;

import java.nio.ByteBuffer;

public interface FileByteBufferResult {

    ByteBuffer getByteBuffer();

    long getCurrentLocation();
}

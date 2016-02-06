package qoo.lib.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import qoo.lib.audio.exception.AudioException;
import qoo.lib.concurrent.NotThreadSafe;

@NotThreadSafe
public class FileByteArrayIteratorEachByteImpl extends FileByteArrayIteratorEachBufferImpl {

    private final long step;

    public FileByteArrayIteratorEachByteImpl(FileChannel ch, ByteBuffer buf) {
        super(ch, buf);
        this.step = 1l;
    }

    public FileByteArrayIteratorEachByteImpl(FileChannel ch, ByteBuffer buf, long step) {
        super(ch, buf);
        this.step = step;
    }

    @Override
    public byte[] next() {
        final byte[] sliced;
        final long holdPosition;
        try {
            buf.clear();
            holdPosition = ch.position();
            remainLength = ch.size() - holdPosition;
            if (remainLength <= 0) {
                return new byte[0];
            }
            if (bufLength < remainLength) {
                sliced = new byte[bufLength];
            } else {
                sliced = new byte[(int) remainLength];
            }
            ch.read(buf);
            buf.flip();
            buf.get(sliced);
            // -- reset channel position for byte movement
            ch.position(holdPosition + step);
        } catch (IOException e) {
            throw new AudioException(e.getCause());
        }
        return sliced;
    }
}

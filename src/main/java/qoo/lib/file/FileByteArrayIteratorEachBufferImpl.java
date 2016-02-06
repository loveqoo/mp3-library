package qoo.lib.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import qoo.lib.audio.exception.AudioException;
import qoo.lib.concurrent.NotThreadSafe;

@NotThreadSafe
public class FileByteArrayIteratorEachBufferImpl implements FileByteArrayIterator {

    protected final FileChannel ch;
    protected final ByteBuffer buf;
    protected final int bufLength;
    protected long remainLength;

    public FileByteArrayIteratorEachBufferImpl(FileChannel ch, ByteBuffer buf) {
        this.ch = ch;
        this.buf = buf;
        this.bufLength = buf.capacity();
    }

    @Override
    public boolean hasNext() {
        try {
            return ch.size() > ch.position();
        } catch (IOException e) {
            throw new AudioException(e.getCause());
        }
    }

    @Override
    public byte[] next() {
        final byte[] sliced;
        try {
            buf.clear();
            remainLength = ch.size() - ch.position();
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
        } catch (IOException e) {
            throw new AudioException(e.getCause());
        }
        return sliced;
    }

    @Override
    public void skip(long position) {
        try {
            long newPosition = ch.position() + position;
            if (ch.size() >= newPosition) {
                ch.position(newPosition);
            } else {
                throw new IndexOutOfBoundsException("requested " + newPosition + ", but max " + Long.toString(ch.size()));
            }
        } catch (IOException e) {
            throw new AudioException(e.getCause());
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

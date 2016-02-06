package qoo.lib.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import qoo.lib.audio.exception.AudioException;
import qoo.lib.concurrent.NotThreadSafe;

@NotThreadSafe
public class FileByteBufferIteratorImpl implements FileByteBufferIterator {

    protected final FileChannel ch;
    protected final ByteBuffer buf;
    protected boolean isCallHasNext = false;

    public FileByteBufferIteratorImpl(FileChannel ch, ByteBuffer buf) {
        this.ch = ch;
        this.buf = buf;
    }

    @Override
    public boolean hasNext() {
        try {
            isCallHasNext = true;
            return ch.size() > ch.position();
        } catch (IOException e) {
            throw new AudioException(e.getCause());
        }
    }

    @Override
    public FileByteBufferResult next() {
        if (!isCallHasNext && !hasNext()) {
            throw new AudioException("It has not next item.");
        }
        final long positionHold;
        try {
            buf.clear();
            positionHold = ch.position();
            // -- doesn't move location of file pointer
            ch.read(buf, positionHold);
            buf.flip();
        } catch (IOException e) {
            throw new AudioException(e.getCause());
        }
        isCallHasNext = false;
        return new FileByteBufferResult() {

            @Override
            public long getCurrentLocation() {
                return positionHold;
            }

            @Override
            public ByteBuffer getByteBuffer() {
                return buf;
            }
        };
    }

    @Override
    public void skip(long position) {
        try {
            skipAbs(ch.position() + position);
        } catch (IOException e) {
            throw new AudioException(e.getCause());
        }
    }

    @Override
    public void skipAbs(long position) {
        try {
            if (ch.size() >= position) {
                ch.position(position);
            } else {
                throw new IndexOutOfBoundsException("requested " + position + ", but max " + Long.toString(ch.size()));
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

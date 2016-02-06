package qoo.lib.audio;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidParameterException;
import java.util.Iterator;

import qoo.lib.audio.mp3.AudioIteratorMP3;

public class AudioIteratorFactory {

    static public Iterator<AudioSlice> createIterator(FileChannel ch, ByteBuffer buf, AudioType type) {
        return createIterator(ch, buf, null);
    }

    static public Iterator<AudioSlice> createIterator(FileChannel ch, ByteBuffer buf, AudioType type, AudioIteratorFilter filter) {
        if (ch == null || !ch.isOpen()) {
            throw new InvalidParameterException("File Channel is invalid.");
        }
        if (buf == null || buf.capacity() < 20) {
            throw new InvalidParameterException("ByteBuffer is invalid.");
        }
        AudioIterator audioIterator = null;
        switch (type) {
        case MP3:
            audioIterator = new AudioIteratorMP3(ch, buf);
            break;
        case M4A:
            throw new UnsupportedOperationException();
        default:
            throw new UnsupportedOperationException();
        }
        if (filter != null) {
            audioIterator.registFilter(filter);
        }
        return audioIterator;
    }

    static public void registAudioIteratorFilter(Iterator<AudioSlice> iterator, AudioIteratorFilter filter) {
        if (iterator instanceof AudioIterator) {
            ((AudioIterator) iterator).registFilter(filter);
        }
    }
}

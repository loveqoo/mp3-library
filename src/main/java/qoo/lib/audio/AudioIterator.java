package qoo.lib.audio;

import java.util.Iterator;

import qoo.lib.file.FileByteBufferResult;

public interface AudioIterator extends Iterator<AudioSlice> {

    boolean validAudioSlice(FileByteBufferResult sliced);

    void skip(long position);

    void registFilter(AudioIteratorFilter filter);
}

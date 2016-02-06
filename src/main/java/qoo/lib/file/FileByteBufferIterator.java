package qoo.lib.file;

import java.util.Iterator;

public interface FileByteBufferIterator extends Iterator<FileByteBufferResult> {

    void skip(long position);

    void skipAbs(long position);
}

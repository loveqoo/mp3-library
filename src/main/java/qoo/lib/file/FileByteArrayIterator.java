package qoo.lib.file;

import java.util.Iterator;

public interface FileByteArrayIterator extends Iterator<byte[]> {

    void skip(long position);

}

package qoo.lib.file;

import org.junit.Before;
import org.junit.Test;
import qoo.lib.util.Bytes;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static org.junit.Assert.*;

public class FileByteArrayIteratorTest {

    private static final String resourceDir = System.getProperty("user.dir") + File.separatorChar + "src" + File.separatorChar + "test" + File.separatorChar + "resources" + File.separatorChar;
    private File sample = new File(resourceDir + "sample_vbr_vbri_22_stereo.mp3");

    @Before
    public void before() {
        assertTrue(sample.exists());
        assertTrue(sample.isFile());
    }

    @Test
    public void testIterate() throws Exception {
        FileChannel ch = null;
        try {
            ch = new FileInputStream(sample).getChannel();
            ByteBuffer buf = ByteBuffer.allocateDirect(1000);
            FileByteArrayIterator iter = new FileByteArrayIteratorEachBufferImpl(ch, buf);
            int idx = 0;
            while (iter.hasNext()) {
                byte[] sliced = iter.next();
                System.out.println(idx + " : " + Bytes.byteArrayToString(sliced, 50));
                idx++;
            }
        } catch (Exception e) {
            if(ch != null && ch.isOpen()){
                ch.close();  
            }
            throw e;
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSkip() throws Exception {
        FileChannel ch = null;
        try {
            ch = new FileInputStream(sample).getChannel();
            ByteBuffer buf = ByteBuffer.allocateDirect(1000);
            FileByteArrayIterator iter = new FileByteArrayIteratorEachBufferImpl(ch, buf);
            iter.skip(sample.length());
            assertFalse(iter.hasNext());
            iter.next();
        } catch (Exception e) {
            fail();
            throw e;
        }
        try {
            ch = new FileInputStream(sample).getChannel();
            ByteBuffer buf = ByteBuffer.allocateDirect(1000);
            FileByteArrayIterator iter = new FileByteArrayIteratorEachBufferImpl(ch, buf);
            iter.skip(sample.length() + 1);
        } catch (Exception e) {
            if(ch != null && ch.isOpen()){
                ch.close();
            }
            throw e;
        }
        fail();
    }

    @Test
    public void oneByteIteratorTest() throws Exception {
        FileChannel ch = null;
        try {
            ch = new FileInputStream(sample).getChannel();
            ByteBuffer buf = ByteBuffer.allocateDirect(10);
            FileByteArrayIterator iter = new FileByteArrayIteratorEachByteImpl(ch, buf);
            byte[] data0 = iter.next();
            System.out.println("data0 : " + Bytes.byteArrayToString(data0, 10));
            byte[] data1 = iter.next();
            System.out.println("data1 : " + Bytes.byteArrayToString(data1, 10));
            byte[] data2 = iter.next();
            System.out.println("data2 : " + Bytes.byteArrayToString(data2, 10));
            assertTrue(data0[1] == data1[0]);
            assertTrue(data0[2] == data2[0]);
            assertTrue(data1[1] == data2[0]);
        } catch (Exception e) {
            if(ch != null && ch.isOpen()){
                ch.close();
            }
            throw e;
        }
    }
}

package qoo.lib.audio.mp3;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import qoo.lib.util.Bytes;

public class OtherTest {

    @Test
    public void test() {
        byte[] origin = new byte[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k' };
        byte[] cutted0 = Arrays.copyOfRange(origin, 1, 3);
        byte[] cutted1 = Arrays.copyOf(origin, origin.length);
        byte[] cutted2 = Arrays.copyOfRange(origin, 0, origin.length);
        origin[1] = 'l';
        assertTrue(cutted0.length == 2);
        System.out.println(Bytes.byteArrayToString(origin));
        System.out.println(Bytes.byteArrayToString(cutted0));
        System.out.println(Bytes.byteArrayToString(cutted1));
        System.out.println(Bytes.byteArrayToString(cutted2));

        System.out.println(234234235l / 255);
    }

}

package qoo.lib.audio.domain;

/**
 * Created by Anthony Jeong on 13. 12. 6.
 */
public interface MP3HeaderVBR extends MP3Header {

    long getBytes();

    long getFrameLength();

    long getQuality();

    long[] getToc();

    byte[] getRawToc();
}

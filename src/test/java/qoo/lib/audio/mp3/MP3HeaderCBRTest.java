package qoo.lib.audio.mp3;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MP3HeaderCBRTest {

    @Test
    public void test() throws Exception {
        byte[] header = new byte[] { (byte) 0xFF, (byte) 0xFB, (byte) 0xB0, (byte) 0x64 };
        assertTrue(MP3.VERSION.VERSION_1 == MP3.findVersion(header));
        assertTrue(MP3.LAYER.LAYER_3 == MP3.findLayer(header));
        assertTrue(MP3.BITRATE.BITRATE_192 == MP3.findBitrate(header));
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_44100 == MP3.findSamplingRate(header));
        assertTrue(MP3.CHANNEL_MODE.JOINT_STEREO == MP3.findChannelMode(header));
        assertTrue(MP3.MODE_EXTENSION.MS_ON_INTENSITY_OFF == MP3.findModeExtension(header));
        assertTrue(MP3.EMPHASIS.EMPHASIS_NONE == MP3.findEmphasis(header));
        assertTrue(MP3.SAMPLES_PER_FRAME.SAMPLES_1152 == MP3.findSamplesPerFrame(header));
        assertTrue(0 == MP3.findPaddingSize(header));
        assertTrue(626 == MP3.calculateFrameSize(header));
    }

    @Test
    public void versionTest() throws Exception {
        // -- MPEG 2.5 : 1111 1111 111[0 0]000
        assertTrue(MP3.VERSION.VERSION_2_5 == MP3.findVersion(new byte[] { (byte) 0xFF, (byte) 0xE0 }));
        // -- Reserved : 1111 1111 111[0 1]000
        assertTrue(MP3.VERSION.VERSION_RESERVED == MP3.findVersion(new byte[] { (byte) 0xFF, (byte) 0xE8 }));
        // -- MPEG 2 : 1111 1111 111[1 0]000
        assertTrue(MP3.VERSION.VERSION_2 == MP3.findVersion(new byte[] { (byte) 0xFF, (byte) 0xF0 }));
        // -- MPEG 1 : 1111 1111 111[1 1]000
        assertTrue(MP3.VERSION.VERSION_1 == MP3.findVersion(new byte[] { (byte) 0xFF, (byte) 0xF8 }));
    }

    @Test
    public void layerTest() throws Exception {
        // -- MPEG 1
        // -- Reserved : 1111 1111 1111 1[00]0
        assertTrue(MP3.LAYER.LAYER_RESERVED == MP3.findLayer(new byte[] { (byte) 0xFF, (byte) 0xF8 }));
        // -- layer 3 : 1111 1111 1111 1[01]0
        assertTrue(MP3.LAYER.LAYER_3 == MP3.findLayer(new byte[] { (byte) 0xFF, (byte) 0xFA }));
        // -- layer 2 : 1111 1111 1111 1[10]0
        assertTrue(MP3.LAYER.LAYER_2 == MP3.findLayer(new byte[] { (byte) 0xFF, (byte) 0xFC }));
        // -- layer 1 : 1111 1111 1111 1[11]0
        assertTrue(MP3.LAYER.LAYER_1 == MP3.findLayer(new byte[] { (byte) 0xFF, (byte) 0xFE }));
    }

    @Test
    public void bitrateTest() throws Exception {
        // MPEG 1, Layer 1, bitrate 0000
        assertTrue(MP3.BITRATE.BITRATE_FREE == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x00 }));
        // MPEG 1, Layer 1, bitrate 0001
        assertTrue(MP3.BITRATE.BITRATE_32 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x10 }));
        // MPEG 1, Layer 1, bitrate 0010
        assertTrue(MP3.BITRATE.BITRATE_64 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x20 }));
        // MPEG 1, Layer 1, bitrate 0011
        assertTrue(MP3.BITRATE.BITRATE_96 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x30 }));
        // MPEG 1, Layer 1, bitrate 0100
        assertTrue(MP3.BITRATE.BITRATE_128 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x40 }));
        // MPEG 1, Layer 1, bitrate 0101
        assertTrue(MP3.BITRATE.BITRATE_160 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x50 }));
        // MPEG 1, Layer 1, bitrate 0110
        assertTrue(MP3.BITRATE.BITRATE_192 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x60 }));
        // MPEG 1, Layer 1, bitrate 0111
        assertTrue(MP3.BITRATE.BITRATE_224 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x70 }));
        // MPEG 1, Layer 1, bitrate 1000
        assertTrue(MP3.BITRATE.BITRATE_256 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x80 }));
        // MPEG 1, Layer 1, bitrate 1001
        assertTrue(MP3.BITRATE.BITRATE_288 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x90 }));
        // MPEG 1, Layer 1, bitrate 1010
        assertTrue(MP3.BITRATE.BITRATE_320 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0xA0 }));
        // MPEG 1, Layer 1, bitrate 1011
        assertTrue(MP3.BITRATE.BITRATE_352 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0xB0 }));
        // MPEG 1, Layer 1, bitrate 1100
        assertTrue(MP3.BITRATE.BITRATE_384 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0xC0 }));
        // MPEG 1, Layer 1, bitrate 1101
        assertTrue(MP3.BITRATE.BITRATE_416 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0xD0 }));
        // MPEG 1, Layer 1, bitrate 1110
        assertTrue(MP3.BITRATE.BITRATE_448 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0xE0 }));
        // MPEG 1, Layer 1, bitrate 1111
        assertTrue(MP3.BITRATE.BITRATE_RESERVED == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0xF0 }));
        // MPEG 1, Layer 2, bitrate 0000
        assertTrue(MP3.BITRATE.BITRATE_FREE == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x00 }));
        // MPEG 1, Layer 2, bitrate 0001
        assertTrue(MP3.BITRATE.BITRATE_32 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x10 }));
        // MPEG 1, Layer 2, bitrate 0010
        assertTrue(MP3.BITRATE.BITRATE_48 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x20 }));
        // MPEG 1, Layer 2, bitrate 0011
        assertTrue(MP3.BITRATE.BITRATE_56 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x30 }));
        // MPEG 1, Layer 2, bitrate 0100
        assertTrue(MP3.BITRATE.BITRATE_64 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x40 }));
        // MPEG 1, Layer 2, bitrate 0101
        assertTrue(MP3.BITRATE.BITRATE_80 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x50 }));
        // MPEG 1, Layer 2, bitrate 0110
        assertTrue(MP3.BITRATE.BITRATE_96 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x60 }));
        // MPEG 1, Layer 2, bitrate 0111
        assertTrue(MP3.BITRATE.BITRATE_112 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x70 }));
        // MPEG 1, Layer 2, bitrate 1000
        assertTrue(MP3.BITRATE.BITRATE_128 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x80 }));
        // MPEG 1, Layer 2, bitrate 1001
        assertTrue(MP3.BITRATE.BITRATE_160 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x90 }));
        // MPEG 1, Layer 2, bitrate 1010
        assertTrue(MP3.BITRATE.BITRATE_192 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0xA0 }));
        // MPEG 1, Layer 2, bitrate 1011
        assertTrue(MP3.BITRATE.BITRATE_224 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0xB0 }));
        // MPEG 1, Layer 2, bitrate 1100
        assertTrue(MP3.BITRATE.BITRATE_256 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0xC0 }));
        // MPEG 1, Layer 2, bitrate 1101
        assertTrue(MP3.BITRATE.BITRATE_320 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0xD0 }));
        // MPEG 1, Layer 2, bitrate 1110
        assertTrue(MP3.BITRATE.BITRATE_384 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0xE0 }));
        // MPEG 1, Layer 2, bitrate 1111
        assertTrue(MP3.BITRATE.BITRATE_RESERVED == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0xF0 }));
        // MPEG 1, Layer 3, bitrate 0000
        assertTrue(MP3.BITRATE.BITRATE_FREE == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x00 }));
        // MPEG 1, Layer 3, bitrate 0001
        assertTrue(MP3.BITRATE.BITRATE_32 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x10 }));
        // MPEG 1, Layer 3, bitrate 0010
        assertTrue(MP3.BITRATE.BITRATE_40 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x20 }));
        // MPEG 1, Layer 3, bitrate 0011
        assertTrue(MP3.BITRATE.BITRATE_48 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x30 }));
        // MPEG 1, Layer 3, bitrate 0100
        assertTrue(MP3.BITRATE.BITRATE_56 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x40 }));
        // MPEG 1, Layer 3, bitrate 0101
        assertTrue(MP3.BITRATE.BITRATE_64 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x50 }));
        // MPEG 1, Layer 3, bitrate 0110
        assertTrue(MP3.BITRATE.BITRATE_80 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x60 }));
        // MPEG 1, Layer 3, bitrate 0111
        assertTrue(MP3.BITRATE.BITRATE_96 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x70 }));
        // MPEG 1, Layer 3, bitrate 1000
        assertTrue(MP3.BITRATE.BITRATE_112 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x80 }));
        // MPEG 1, Layer 3, bitrate 1001
        assertTrue(MP3.BITRATE.BITRATE_128 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x90 }));
        // MPEG 1, Layer 3, bitrate 1010
        assertTrue(MP3.BITRATE.BITRATE_160 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0xA0 }));
        // MPEG 1, Layer 3, bitrate 1011
        assertTrue(MP3.BITRATE.BITRATE_192 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0xB0 }));
        // MPEG 1, Layer 3, bitrate 1100
        assertTrue(MP3.BITRATE.BITRATE_224 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0xC0 }));
        // MPEG 1, Layer 3, bitrate 1101
        assertTrue(MP3.BITRATE.BITRATE_256 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0xD0 }));
        // MPEG 1, Layer 3, bitrate 1110
        assertTrue(MP3.BITRATE.BITRATE_320 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0xE0 }));
        // MPEG 1, Layer 3, bitrate 1111
        assertTrue(MP3.BITRATE.BITRATE_RESERVED == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0xF0 }));

        // MPEG 2, Layer 1, bitrate 0000
        assertTrue(MP3.BITRATE.BITRATE_FREE == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x00 }));
        // MPEG 2, Layer 1, bitrate 0001
        assertTrue(MP3.BITRATE.BITRATE_32 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x10 }));
        // MPEG 2, Layer 1, bitrate 0010
        assertTrue(MP3.BITRATE.BITRATE_48 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x20 }));
        // MPEG 2, Layer 1, bitrate 0011
        assertTrue(MP3.BITRATE.BITRATE_56 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x30 }));
        // MPEG 2, Layer 1, bitrate 0100
        assertTrue(MP3.BITRATE.BITRATE_64 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x40 }));
        // MPEG 2, Layer 1, bitrate 0101
        assertTrue(MP3.BITRATE.BITRATE_80 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x50 }));
        // MPEG 2, Layer 1, bitrate 0110
        assertTrue(MP3.BITRATE.BITRATE_96 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x60 }));
        // MPEG 2, Layer 1, bitrate 0111
        assertTrue(MP3.BITRATE.BITRATE_112 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x70 }));
        // MPEG 2, Layer 1, bitrate 1000
        assertTrue(MP3.BITRATE.BITRATE_128 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x80 }));
        // MPEG 2, Layer 1, bitrate 1001
        assertTrue(MP3.BITRATE.BITRATE_144 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x90 }));
        // MPEG 2, Layer 1, bitrate 1010
        assertTrue(MP3.BITRATE.BITRATE_160 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0xA0 }));
        // MPEG 2, Layer 1, bitrate 1011
        assertTrue(MP3.BITRATE.BITRATE_176 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0xB0 }));
        // MPEG 2, Layer 1, bitrate 1100
        assertTrue(MP3.BITRATE.BITRATE_192 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0xC0 }));
        // MPEG 2, Layer 1, bitrate 1101
        assertTrue(MP3.BITRATE.BITRATE_224 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0xD0 }));
        // MPEG 2, Layer 1, bitrate 1110
        assertTrue(MP3.BITRATE.BITRATE_256 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0xE0 }));
        // MPEG 2, Layer 1, bitrate 1111
        assertTrue(MP3.BITRATE.BITRATE_RESERVED == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0xF0 }));
        // MPEG 2, Layer 2, bitrate 0000
        assertTrue(MP3.BITRATE.BITRATE_FREE == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0x00 }));
        // MPEG 2, Layer 2, bitrate 0001
        assertTrue(MP3.BITRATE.BITRATE_8 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0x10 }));
        // MPEG 2, Layer 2, bitrate 0010
        assertTrue(MP3.BITRATE.BITRATE_16 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0x20 }));
        // MPEG 2, Layer 2, bitrate 0011
        assertTrue(MP3.BITRATE.BITRATE_24 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0x30 }));
        // MPEG 2, Layer 2, bitrate 0100
        assertTrue(MP3.BITRATE.BITRATE_32 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0x40 }));
        // MPEG 2, Layer 2, bitrate 0101
        assertTrue(MP3.BITRATE.BITRATE_40 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0x50 }));
        // MPEG 2, Layer 2, bitrate 0110
        assertTrue(MP3.BITRATE.BITRATE_48 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0x60 }));
        // MPEG 2, Layer 2, bitrate 0111
        assertTrue(MP3.BITRATE.BITRATE_56 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0x70 }));
        // MPEG 2, Layer 2, bitrate 1000
        assertTrue(MP3.BITRATE.BITRATE_64 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0x80 }));
        // MPEG 2, Layer 2, bitrate 1001
        assertTrue(MP3.BITRATE.BITRATE_80 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0x90 }));
        // MPEG 2, Layer 2, bitrate 1010
        assertTrue(MP3.BITRATE.BITRATE_96 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0xA0 }));
        // MPEG 2, Layer 2, bitrate 1011
        assertTrue(MP3.BITRATE.BITRATE_112 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0xB0 }));
        // MPEG 2, Layer 2, bitrate 1100
        assertTrue(MP3.BITRATE.BITRATE_128 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0xC0 }));
        // MPEG 2, Layer 2, bitrate 1101
        assertTrue(MP3.BITRATE.BITRATE_144 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0xD0 }));
        // MPEG 2, Layer 2, bitrate 1110
        assertTrue(MP3.BITRATE.BITRATE_160 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0xE0 }));
        // MPEG 2, Layer 2, bitrate 1111
        assertTrue(MP3.BITRATE.BITRATE_RESERVED == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF4, (byte) 0xF0 }));
        // MPEG 2, Layer 3, bitrate 0000
        assertTrue(MP3.BITRATE.BITRATE_FREE == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0x00 }));
        // MPEG 2, Layer 3, bitrate 0001
        assertTrue(MP3.BITRATE.BITRATE_8 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0x10 }));
        // MPEG 2, Layer 3, bitrate 0010
        assertTrue(MP3.BITRATE.BITRATE_16 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0x20 }));
        // MPEG 2, Layer 3, bitrate 0011
        assertTrue(MP3.BITRATE.BITRATE_24 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0x30 }));
        // MPEG 2, Layer 3, bitrate 0100
        assertTrue(MP3.BITRATE.BITRATE_32 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0x40 }));
        // MPEG 2, Layer 3, bitrate 0101
        assertTrue(MP3.BITRATE.BITRATE_40 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0x50 }));
        // MPEG 2, Layer 3, bitrate 0110
        assertTrue(MP3.BITRATE.BITRATE_48 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0x60 }));
        // MPEG 2, Layer 3, bitrate 0111
        assertTrue(MP3.BITRATE.BITRATE_56 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0x70 }));
        // MPEG 2, Layer 3, bitrate 1000
        assertTrue(MP3.BITRATE.BITRATE_64 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0x80 }));
        // MPEG 2, Layer 3, bitrate 1001
        assertTrue(MP3.BITRATE.BITRATE_80 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0x90 }));
        // MPEG 2, Layer 3, bitrate 1010
        assertTrue(MP3.BITRATE.BITRATE_96 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0xA0 }));
        // MPEG 2, Layer 3, bitrate 1011
        assertTrue(MP3.BITRATE.BITRATE_112 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0xB0 }));
        // MPEG 2, Layer 3, bitrate 1100
        assertTrue(MP3.BITRATE.BITRATE_128 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0xC0 }));
        // MPEG 2, Layer 3, bitrate 1101
        assertTrue(MP3.BITRATE.BITRATE_144 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0xD0 }));
        // MPEG 2, Layer 3, bitrate 1110
        assertTrue(MP3.BITRATE.BITRATE_160 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0xE0 }));
        // MPEG 2, Layer 3, bitrate 1111
        assertTrue(MP3.BITRATE.BITRATE_RESERVED == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xF2, (byte) 0xF0 }));

        // MPEG 2.5, Layer 1, bitrate 0000
        assertTrue(MP3.BITRATE.BITRATE_FREE == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x00 }));
        // MPEG 2.5, Layer 1, bitrate 0001
        assertTrue(MP3.BITRATE.BITRATE_32 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x10 }));
        // MPEG 2.5, Layer 1, bitrate 0010
        assertTrue(MP3.BITRATE.BITRATE_48 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x20 }));
        // MPEG 2.5, Layer 1, bitrate 0011
        assertTrue(MP3.BITRATE.BITRATE_56 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x30 }));
        // MPEG 2.5, Layer 1, bitrate 0100
        assertTrue(MP3.BITRATE.BITRATE_64 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x40 }));
        // MPEG 2.5, Layer 1, bitrate 0101
        assertTrue(MP3.BITRATE.BITRATE_80 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x50 }));
        // MPEG 2.5, Layer 1, bitrate 0110
        assertTrue(MP3.BITRATE.BITRATE_96 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x60 }));
        // MPEG 2.5, Layer 1, bitrate 0111
        assertTrue(MP3.BITRATE.BITRATE_112 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x70 }));
        // MPEG 2.5, Layer 1, bitrate 1000
        assertTrue(MP3.BITRATE.BITRATE_128 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x80 }));
        // MPEG 2.5, Layer 1, bitrate 1001
        assertTrue(MP3.BITRATE.BITRATE_144 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x90 }));
        // MPEG 2.5, Layer 1, bitrate 1010
        assertTrue(MP3.BITRATE.BITRATE_160 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0xA0 }));
        // MPEG 2.5, Layer 1, bitrate 1011
        assertTrue(MP3.BITRATE.BITRATE_176 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0xB0 }));
        // MPEG 2.5, Layer 1, bitrate 1100
        assertTrue(MP3.BITRATE.BITRATE_192 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0xC0 }));
        // MPEG 2.5, Layer 1, bitrate 1101
        assertTrue(MP3.BITRATE.BITRATE_224 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0xD0 }));
        // MPEG 2.5, Layer 1, bitrate 1110
        assertTrue(MP3.BITRATE.BITRATE_256 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0xE0 }));
        // MPEG 2.5, Layer 1, bitrate 1111
        assertTrue(MP3.BITRATE.BITRATE_RESERVED == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0xF0 }));
        // MPEG 2.5, Layer 2, bitrate 0000
        assertTrue(MP3.BITRATE.BITRATE_FREE == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0x00 }));
        // MPEG 2.5, Layer 2, bitrate 0001
        assertTrue(MP3.BITRATE.BITRATE_8 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0x10 }));
        // MPEG 2.5, Layer 2, bitrate 0010
        assertTrue(MP3.BITRATE.BITRATE_16 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0x20 }));
        // MPEG 2.5, Layer 2, bitrate 0011
        assertTrue(MP3.BITRATE.BITRATE_24 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0x30 }));
        // MPEG 2.5, Layer 2, bitrate 0100
        assertTrue(MP3.BITRATE.BITRATE_32 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0x40 }));
        // MPEG 2.5, Layer 2, bitrate 0101
        assertTrue(MP3.BITRATE.BITRATE_40 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0x50 }));
        // MPEG 2.5, Layer 2, bitrate 0110
        assertTrue(MP3.BITRATE.BITRATE_48 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0x60 }));
        // MPEG 2.5, Layer 2, bitrate 0111
        assertTrue(MP3.BITRATE.BITRATE_56 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0x70 }));
        // MPEG 2.5, Layer 2, bitrate 1000
        assertTrue(MP3.BITRATE.BITRATE_64 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0x80 }));
        // MPEG 2.5, Layer 2, bitrate 1001
        assertTrue(MP3.BITRATE.BITRATE_80 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0x90 }));
        // MPEG 2.5, Layer 2, bitrate 1010
        assertTrue(MP3.BITRATE.BITRATE_96 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0xA0 }));
        // MPEG 2.5, Layer 2, bitrate 1011
        assertTrue(MP3.BITRATE.BITRATE_112 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0xB0 }));
        // MPEG 2.5, Layer 2, bitrate 1100
        assertTrue(MP3.BITRATE.BITRATE_128 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0xC0 }));
        // MPEG 2.5, Layer 2, bitrate 1101
        assertTrue(MP3.BITRATE.BITRATE_144 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0xD0 }));
        // MPEG 2.5, Layer 2, bitrate 1110
        assertTrue(MP3.BITRATE.BITRATE_160 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0xE0 }));
        // MPEG 2.5, Layer 2, bitrate 1111
        assertTrue(MP3.BITRATE.BITRATE_RESERVED == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE4, (byte) 0xF0 }));
        // MPEG 2.5, Layer 3, bitrate 0000
        assertTrue(MP3.BITRATE.BITRATE_FREE == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0x00 }));
        // MPEG 2.5, Layer 3, bitrate 0001
        assertTrue(MP3.BITRATE.BITRATE_8 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0x10 }));
        // MPEG 2.5, Layer 3, bitrate 0010
        assertTrue(MP3.BITRATE.BITRATE_16 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0x20 }));
        // MPEG 2.5, Layer 3, bitrate 0011
        assertTrue(MP3.BITRATE.BITRATE_24 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0x30 }));
        // MPEG 2.5, Layer 3, bitrate 0100
        assertTrue(MP3.BITRATE.BITRATE_32 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0x40 }));
        // MPEG 2.5, Layer 3, bitrate 0101
        assertTrue(MP3.BITRATE.BITRATE_40 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0x50 }));
        // MPEG 2.5, Layer 3, bitrate 0110
        assertTrue(MP3.BITRATE.BITRATE_48 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0x60 }));
        // MPEG 2.5, Layer 3, bitrate 0111
        assertTrue(MP3.BITRATE.BITRATE_56 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0x70 }));
        // MPEG 2.5, Layer 3, bitrate 1000
        assertTrue(MP3.BITRATE.BITRATE_64 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0x80 }));
        // MPEG 2.5, Layer 3, bitrate 1001
        assertTrue(MP3.BITRATE.BITRATE_80 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0x90 }));
        // MPEG 2.5, Layer 3, bitrate 1010
        assertTrue(MP3.BITRATE.BITRATE_96 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0xA0 }));
        // MPEG 2.5, Layer 3, bitrate 1011
        assertTrue(MP3.BITRATE.BITRATE_112 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0xB0 }));
        // MPEG 2.5, Layer 3, bitrate 1100
        assertTrue(MP3.BITRATE.BITRATE_128 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0xC0 }));
        // MPEG 2.5, Layer 3, bitrate 1101
        assertTrue(MP3.BITRATE.BITRATE_144 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0xD0 }));
        // MPEG 2.5, Layer 3, bitrate 1110
        assertTrue(MP3.BITRATE.BITRATE_160 == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0xE0 }));
        // MPEG 2.5, Layer 3, bitrate 1111
        assertTrue(MP3.BITRATE.BITRATE_RESERVED == MP3.findBitrate(new byte[] { (byte) 0xFF, (byte) 0xE2, (byte) 0xF0 }));
    }

    @Test
    public void samplingRateTest() throws Exception {
        // -- MPEG 1, Layer 1, bitrate free, sampling rate 00
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_44100 == MP3.findSamplingRate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x00 }));
        // -- MPEG 1, Layer 1, bitrate free, sampling rate 01
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_48000 == MP3.findSamplingRate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x04 }));
        // -- MPEG 1, Layer 1, bitrate free, sampling rate 02
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_32000 == MP3.findSamplingRate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x08 }));
        // -- MPEG 1, Layer 1, bitrate free, sampling rate reserved
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_RESERVED == MP3.findSamplingRate(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C }));

        // -- MPEG 2, Layer 1, bitrate free, sampling rate 00
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_22050 == MP3.findSamplingRate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x00 }));
        // -- MPEG 2, Layer 1, bitrate free, sampling rate 01
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_24000 == MP3.findSamplingRate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x04 }));
        // -- MPEG 2, Layer 1, bitrate free, sampling rate 02
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_16000 == MP3.findSamplingRate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x08 }));
        // -- MPEG 2, Layer 1, bitrate free, sampling rate reserved
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_RESERVED == MP3.findSamplingRate(new byte[] { (byte) 0xFF, (byte) 0xF6, (byte) 0x0C }));

        // -- MPEG 2.5, Layer 1, bitrate free, sampling rate 00
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_11025 == MP3.findSamplingRate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x00 }));
        // -- MPEG 2.5, Layer 1, bitrate free, sampling rate 01
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_12000 == MP3.findSamplingRate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x04 }));
        // -- MPEG 2.5, Layer 1, bitrate free, sampling rate 02
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_8000 == MP3.findSamplingRate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x08 }));
        // -- MPEG 2.5, Layer 1, bitrate free, sampling rate reserved
        assertTrue(MP3.SAMPLING_RATE.SAMPLING_RATE_RESERVED == MP3.findSamplingRate(new byte[] { (byte) 0xFF, (byte) 0xE6, (byte) 0x0C }));
    }

    @Test
    public void channelModeTest() throws Exception {
        // -- MPEG 1, Layer 1, bitrate free, sampling rate reserved, channel
        // mode stereo 00
        assertTrue(MP3.CHANNEL_MODE.STEREO == MP3.findChannelMode(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C, (byte) 0x00 }));
        // -- MPEG 1, Layer 1, bitrate free, sampling rate reserved, channel
        // mode joint stereo 01
        assertTrue(MP3.CHANNEL_MODE.JOINT_STEREO == MP3.findChannelMode(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C, (byte) 0x40 }));
        // -- MPEG 1, Layer 1, bitrate free, sampling rate reserved, channel
        // mode dual channel 10
        assertTrue(MP3.CHANNEL_MODE.DUAL_CHANNEL == MP3.findChannelMode(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C, (byte) 0x80 }));
        // -- MPEG 1, Layer 1, bitrate free, sampling rate reserved, channel
        // mode single channel 11
        assertTrue(MP3.CHANNEL_MODE.SINGLE_CHANNEL == MP3.findChannelMode(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C, (byte) 0xC0 }));
    }

    @Test
    public void modeExtensionTest() throws Exception {
        // -- MPEG 1, Layer 1, mode extention 00
        assertTrue(MP3.MODE_EXTENSION.BANDS_4_TO_31 == MP3.findModeExtension(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C, (byte) 0x00 }));
        // -- MPEG 1, Layer 1, mode extention 01
        assertTrue(MP3.MODE_EXTENSION.BANDS_8_TO_31 == MP3.findModeExtension(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C, (byte) 0x10 }));
        // -- MPEG 1, Layer 1, mode extention 10
        assertTrue(MP3.MODE_EXTENSION.BANDS_12_TO_31 == MP3.findModeExtension(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C, (byte) 0x20 }));
        // -- MPEG 1, Layer 1, mode extention 11
        assertTrue(MP3.MODE_EXTENSION.BANDS_16_TO_31 == MP3.findModeExtension(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C, (byte) 0x30 }));

        // -- MPEG 1, Layer 2, mode extention 00
        assertTrue(MP3.MODE_EXTENSION.BANDS_4_TO_31 == MP3.findModeExtension(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x0C, (byte) 0x00 }));
        // -- MPEG 1, Layer 2, mode extention 01
        assertTrue(MP3.MODE_EXTENSION.BANDS_8_TO_31 == MP3.findModeExtension(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x0C, (byte) 0x10 }));
        // -- MPEG 1, Layer 2, mode extention 10
        assertTrue(MP3.MODE_EXTENSION.BANDS_12_TO_31 == MP3.findModeExtension(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x0C, (byte) 0x20 }));
        // -- MPEG 1, Layer 2, mode extention 11
        assertTrue(MP3.MODE_EXTENSION.BANDS_16_TO_31 == MP3.findModeExtension(new byte[] { (byte) 0xFF, (byte) 0xFC, (byte) 0x0C, (byte) 0x30 }));

        // -- MPEG 1, Layer 3, mode extention 00
        assertTrue(MP3.MODE_EXTENSION.MS_OFF_INTENSITY_OFF == MP3
                .findModeExtension(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x0C, (byte) 0x00 }));
        // -- MPEG 1, Layer 3, mode extention 01
        assertTrue(MP3.MODE_EXTENSION.MS_OFF_INTENSITY_ON == MP3.findModeExtension(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x0C, (byte) 0x10 }));
        // -- MPEG 1, Layer 3, mode extention 10
        assertTrue(MP3.MODE_EXTENSION.MS_ON_INTENSITY_OFF == MP3.findModeExtension(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x0C, (byte) 0x20 }));
        // -- MPEG 1, Layer 3, mode extention 11
        assertTrue(MP3.MODE_EXTENSION.MS_ON_INTENSITY_ON == MP3.findModeExtension(new byte[] { (byte) 0xFF, (byte) 0xFA, (byte) 0x0C, (byte) 0x30 }));
    }

    @Test
    public void emphasisTest() throws Exception {
        // -- emphasis 00
        assertTrue(MP3.EMPHASIS.EMPHASIS_NONE == MP3.findEmphasis(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C, (byte) 0x00 }));
        // -- emphasis 01
        assertTrue(MP3.EMPHASIS.EMPHASIS_50_15_MS == MP3.findEmphasis(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C, (byte) 0x01 }));
        // -- emphasis 10
        assertTrue(MP3.EMPHASIS.EMPHASIS_RESERVED == MP3.findEmphasis(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C, (byte) 0x02 }));
        // -- emphasis 11
        assertTrue(MP3.EMPHASIS.EMPHASIS_CCIT_J_17 == MP3.findEmphasis(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x0C, (byte) 0x03 }));
    }

    @Test
    public void protectionBitTest() throws Exception {
        assertTrue(MP3.hasProtectionBit(new byte[] { (byte) 0xFF, (byte) 0xFF }));
        assertFalse(MP3.hasProtectionBit(new byte[] { (byte) 0xFF, (byte) 0xFE }));
    }

    @Test
    public void paddingBitTest() throws Exception {
        assertTrue(MP3.hasPaddingBit(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }));
        assertFalse(MP3.hasPaddingBit(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0xFC }));
    }

    @Test
    public void privateBitTest() throws Exception {
        assertTrue(MP3.hasPrivateBit(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }));
        assertFalse(MP3.hasPrivateBit(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0xFE }));
    }

    @Test
    public void copyrightBitTest() throws Exception {
        assertTrue(MP3.hasCopyrightBit(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }));
        assertFalse(MP3.hasCopyrightBit(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0xFF, (byte) 0xF7 }));
    }

    @Test
    public void originalBitTest() throws Exception {
        assertTrue(MP3.hasOriginalBit(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }));
        assertFalse(MP3.hasOriginalBit(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0xFF, (byte) 0xFB }));
    }

}

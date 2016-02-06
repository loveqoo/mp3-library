package qoo.lib.audio.mp3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import qoo.lib.audio.AudioIterator;
import qoo.lib.audio.AudioSlice;
import qoo.lib.audio.mp3.MP3.*;
import qoo.lib.util.Strings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AudioIteratorMP3Test {

    private String resourceDir = System.getProperty("user.dir") + File.separatorChar + "src" + File.separatorChar + "test" + File.separatorChar + "resources" + File.separatorChar;
    private File sampleXing = new File(resourceDir + "sample_vbr_xing.mp3");
    private File sampleVbri16m = new File(resourceDir + "sample_vbr_vbri_16_mono.mp3");
    private File sampleVbri22m = new File(resourceDir + "sample_vbr_vbri_22_mono.mp3");
    private File sampleVbri22s = new File(resourceDir + "sample_vbr_vbri_22_stereo.mp3");
    private AudioIterator audioIteratorVbr;

    private FileChannel chXing;
    private FileChannel chVbri;

    @Before
    public void before() throws IOException {
        assertTrue(sampleXing.exists());
        assertTrue(sampleXing.isFile());
        assertTrue(sampleVbri16m.exists());
        assertTrue(sampleVbri16m.isFile());
        assertTrue(sampleVbri22m.exists());
        assertTrue(sampleVbri22m.isFile());
        assertTrue(sampleVbri22s.exists());
        assertTrue(sampleVbri22s.isFile());
    }

    @After
    public void after() throws IOException {
        if (chXing != null)
            chXing.close();
        if (chVbri != null)
            chVbri.close();
    }

    @Test
    public void cbrTest() throws IOException {
        chXing = new FileInputStream(sampleXing).getChannel();
        ByteBuffer buf = ByteBuffer.allocateDirect(500);
        audioIteratorVbr = new AudioIteratorMP3(chXing, buf);
        assertTrue(audioIteratorVbr.hasNext());
        AudioSlice slice = audioIteratorVbr.next();
        assertNotNull(slice);
        Map<String, String> analyzed = ((AudioSliceMP3) slice).getHeader().analyze();
        System.out.println(analyzed);
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.VERSION.toString()).equals(VERSION.VERSION_1.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.LAYER.toString()).equals(LAYER.LAYER_3.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.BITRATE.toString()).equals(BITRATE.BITRATE_48.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.SAMPLING_RATE.toString()).equals(SAMPLING_RATE.SAMPLING_RATE_44100.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.SAMPLES_PER_FRAME.toString()).equals(SAMPLES_PER_FRAME.SAMPLES_1152.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.CHANNEL_MODE.toString()).equals(CHANNEL_MODE.JOINT_STEREO.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.PADDING_SIZE.toString()).equals("0"));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.COPYRIGHT_BIT.toString()).equals(Boolean.TRUE.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.PRIVATE_BIT.toString()).equals(Boolean.FALSE.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.ORIGINAL_BIT.toString()).equals(Boolean.TRUE.toString()));

        assertTrue(audioIteratorVbr.hasNext());
        slice = audioIteratorVbr.next();
        assertNotNull(slice);
        analyzed = ((AudioSliceMP3) slice).getHeader().analyze();
        System.out.println(analyzed);
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.VERSION.toString()).equals(VERSION.VERSION_1.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.LAYER.toString()).equals(LAYER.LAYER_3.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.BITRATE.toString()).equals(BITRATE.BITRATE_192.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.SAMPLING_RATE.toString()).equals(SAMPLING_RATE.SAMPLING_RATE_44100.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.SAMPLES_PER_FRAME.toString()).equals(SAMPLES_PER_FRAME.SAMPLES_1152.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.CHANNEL_MODE.toString()).equals(CHANNEL_MODE.JOINT_STEREO.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.PADDING_SIZE.toString()).equals("0"));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.COPYRIGHT_BIT.toString()).equals(Boolean.TRUE.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.PRIVATE_BIT.toString()).equals(Boolean.FALSE.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.ORIGINAL_BIT.toString()).equals(Boolean.TRUE.toString()));

        assertTrue(audioIteratorVbr.hasNext());
        slice = audioIteratorVbr.next();
        assertNotNull(slice);
        analyzed = ((AudioSliceMP3) slice).getHeader().analyze();
        System.out.println(analyzed);
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.VERSION.toString()).equals(VERSION.VERSION_1.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.LAYER.toString()).equals(LAYER.LAYER_3.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.BITRATE.toString()).equals(BITRATE.BITRATE_112.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.SAMPLING_RATE.toString()).equals(SAMPLING_RATE.SAMPLING_RATE_44100.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.SAMPLES_PER_FRAME.toString()).equals(SAMPLES_PER_FRAME.SAMPLES_1152.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.CHANNEL_MODE.toString()).equals(CHANNEL_MODE.JOINT_STEREO.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.PADDING_SIZE.toString()).equals("0"));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.COPYRIGHT_BIT.toString()).equals(Boolean.TRUE.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.PRIVATE_BIT.toString()).equals(Boolean.FALSE.toString()));
        assertTrue(analyzed.get(MP3.ANALYZE_ATTRIBUTE.ORIGINAL_BIT.toString()).equals(Boolean.TRUE.toString()));
    }

    @Test
    public void vbrXingTest() throws IOException {
        chXing = new FileInputStream(sampleXing).getChannel();
        ByteBuffer buf = ByteBuffer.allocateDirect(500);
        audioIteratorVbr = new AudioIteratorMP3(chXing, buf);
        assertTrue(audioIteratorVbr.hasNext());
        AudioSlice slice = audioIteratorVbr.next();
        assertNotNull(slice);
        // System.out.println(slice);
        MP3HeaderCBR cbrHeader = ((AudioSliceMP3) slice).getHeader();
        MP3HeaderVBR vbrHeader = ((AudioSliceMP3) slice).getVBRHeader();
        assertNotNull(cbrHeader);
        assertNotNull(vbrHeader);
        assertTrue(vbrHeader.getVbrType() == VBR_TYPE.VBR_XING_X);
        assertTrue(vbrHeader.getBytes() == sampleXing.length());

        assertTrue(audioIteratorVbr.hasNext());
        slice = audioIteratorVbr.next();
        assertNotNull(slice);
        // System.out.println(slice);
        cbrHeader = ((AudioSliceMP3) slice).getHeader();
        vbrHeader = ((AudioSliceMP3) slice).getVBRHeader();
        assertNotNull(cbrHeader);
        assertNotNull(vbrHeader);
        assertTrue(vbrHeader.getVbrType() == VBR_TYPE.VBR_XING_X);
        assertTrue(vbrHeader.getBytes() == sampleXing.length());
        long[] toc = vbrHeader.getToc();
        assertNotNull(toc);
        assertTrue(toc.length > 0);
        System.out.println(Strings.join(toc, ", "));
    }

    @Test
    public void vbrVbriTest() throws IOException {
        chVbri = new FileInputStream(sampleVbri16m).getChannel();
        ByteBuffer buf = ByteBuffer.allocateDirect(500);
        audioIteratorVbr = new AudioIteratorMP3(chVbri, buf);
        assertTrue(audioIteratorVbr.hasNext());
        AudioSlice slice = audioIteratorVbr.next();
        assertNotNull(slice);
        System.out.println(slice);
        MP3HeaderVBRVbri vbriHeader = (MP3HeaderVBRVbri) (((AudioSliceMP3) slice).getVBRHeader());
        assertTrue(vbriHeader.getVbriVersion() == 1);
        assertTrue(vbriHeader.getVbriDelay() == 1777);
        assertTrue(vbriHeader.getQuality() == 50);
        assertTrue(vbriHeader.getBytes() == 3924l);
        assertTrue(vbriHeader.getFrameLength() == 32);
        assertTrue(vbriHeader.getNumberOfEntries() == 31);
        assertTrue(vbriHeader.getScaleFactorOfTocTable() == 1);
        assertTrue(vbriHeader.getSizePerTableEntry() == 2);
        assertTrue(vbriHeader.getFramesPerTableEntry() == 1);
        assertTrue(vbriHeader.getRawToc().length == vbriHeader.getNumberOfEntries() * vbriHeader.getSizePerTableEntry());
        assertTrue(vbriHeader.getToc().length == vbriHeader.getNumberOfEntries() + 1);
        long[] toc = vbriHeader.getToc();
        assertNotNull(toc);
        assertTrue(toc.length > 0);
        System.out.println(Strings.join(toc, ", "));
        chVbri.close();

        chVbri = new FileInputStream(sampleVbri22m).getChannel();
        buf.clear();
        audioIteratorVbr = new AudioIteratorMP3(chVbri, buf);
        assertTrue(audioIteratorVbr.hasNext());
        slice = audioIteratorVbr.next();
        assertNotNull(slice);
        System.out.println(slice);
        vbriHeader = (MP3HeaderVBRVbri) (((AudioSliceMP3) slice).getVBRHeader());
        assertTrue(vbriHeader.getVbriVersion() == 1);
        assertTrue(vbriHeader.getVbriDelay() == 1777);
        assertTrue(vbriHeader.getQuality() == 50);
        assertTrue(vbriHeader.getBytes() == 5022l);
        assertTrue(vbriHeader.getFrameLength() == 43);
        assertTrue(vbriHeader.getNumberOfEntries() == 42);
        assertTrue(vbriHeader.getScaleFactorOfTocTable() == 1);
        assertTrue(vbriHeader.getSizePerTableEntry() == 2);
        assertTrue(vbriHeader.getFramesPerTableEntry() == 1);
        assertTrue(vbriHeader.getRawToc().length == vbriHeader.getNumberOfEntries() * vbriHeader.getSizePerTableEntry());
        assertTrue(vbriHeader.getToc().length == vbriHeader.getNumberOfEntries() + 1);
        toc = vbriHeader.getToc();
        assertNotNull(toc);
        assertTrue(toc.length > 0);
        System.out.println(Strings.join(toc, ", "));
        chVbri.close();

        chVbri = new FileInputStream(sampleVbri22s).getChannel();
        buf.clear();
        audioIteratorVbr = new AudioIteratorMP3(chVbri, buf);
        assertTrue(audioIteratorVbr.hasNext());
        slice = audioIteratorVbr.next();
        assertNotNull(slice);
        System.out.println(slice);
        vbriHeader = (MP3HeaderVBRVbri) (((AudioSliceMP3) slice).getVBRHeader());
        assertTrue(vbriHeader.getVbriVersion() == 1);
        assertTrue(vbriHeader.getVbriDelay() == 1777);
        assertTrue(vbriHeader.getQuality() == 50);
        assertTrue(vbriHeader.getBytes() == 6973l);
        assertTrue(vbriHeader.getFrameLength() == 43);
        assertTrue(vbriHeader.getNumberOfEntries() == 42);
        assertTrue(vbriHeader.getScaleFactorOfTocTable() == 1);
        assertTrue(vbriHeader.getSizePerTableEntry() == 2);
        assertTrue(vbriHeader.getFramesPerTableEntry() == 1);
        assertTrue(vbriHeader.getRawToc().length == vbriHeader.getNumberOfEntries() * vbriHeader.getSizePerTableEntry());
        assertTrue(vbriHeader.getToc().length == vbriHeader.getNumberOfEntries() + 1);
        toc = vbriHeader.getToc();
        assertNotNull(toc);
        assertTrue(toc.length > 0);
        System.out.println(Strings.join(toc, ", "));
    }

    @Test
    public void iterateTest() throws IOException {
        chVbri = new FileInputStream(sampleVbri16m).getChannel();
        ByteBuffer buf = ByteBuffer.allocateDirect(500);
        audioIteratorVbr = new AudioIteratorMP3(chVbri, buf);
        assertTrue(audioIteratorVbr.hasNext());
        AudioSlice slice = audioIteratorVbr.next();
        System.out.println(slice);
        MP3HeaderVBR vbrHeader = ((AudioSliceMP3) slice).getVBRHeader();
        int loop = 1;
        while (audioIteratorVbr.hasNext()) {
            slice = audioIteratorVbr.next();
            // if (slice.getSlice()[0] == 3708) {
            // System.out.println("DEBUG START");
            // }
            loop++;
            System.out.println(slice);
        }
        assertTrue(loop == vbrHeader.getFrameLength());
        System.out.println("loop : " + loop + ", frames : " + vbrHeader.getFrameLength());
    }
}

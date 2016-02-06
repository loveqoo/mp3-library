package qoo.lib.audio.mp3;

import qoo.lib.audio.exception.AudioException;
import qoo.lib.audio.mp3.MP3.VBR_TYPE;

public class MP3HeaderVBRXing implements MP3HeaderVBR {

    private byte[] header;
    private VBR_TYPE vbrType;
    private long bytes = 0l;
    private long frameLength = 0l;
    private long quality = 0l;
    private byte[] rawToc;
    private long[] toc;

    public MP3HeaderVBRXing(byte[] header) {
        this.header = header;
    }

    @Override
    public VBR_TYPE getVbrType() {
        return vbrType == null ? vbrType = MP3.findVbrType(header) : vbrType;
    }

    @Override
    public long getBytes() {
        return bytes <= 0l ? bytes = MP3.findVbrNumberOfBytes(header) : bytes;
    }

    @Override
    public long getFrameLength() {
        return frameLength <= 0l ? frameLength = MP3.findVbrNumberOfFrames(header) : frameLength;
    }

    @Override
    public long getQuality() {
        return quality <= 0 ? quality = MP3.findVbrQualityIndicator(header) : quality;
    }

    @Override
    public byte[] getRawToc() {
        return rawToc == null ? rawToc = MP3.findVbrRawToc(header) : rawToc;
    }

    @Override
    public long[] getToc() {
        return toc == null ? toc = MP3.findVbrToc(header) : toc;
    }

    @Override
    public void refresh(byte[] rawData) throws AudioException {
        this.header = rawData;
        vbrType = null;
        bytes = 0l;
        frameLength = 0l;
        quality = 0;
        toc = null;
        rawToc = null;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("VBR TYPE : " + getVbrType().toString());
        sb.append(", " + getBytes() + " bytes, ");
        sb.append(getFrameLength() + " frames, ");
        sb.append(getQuality() + " quality.");
        return sb.toString();
    }
}

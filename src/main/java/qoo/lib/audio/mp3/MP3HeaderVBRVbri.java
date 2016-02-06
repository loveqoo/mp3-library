package qoo.lib.audio.mp3;

import qoo.lib.audio.exception.AudioException;
import qoo.lib.audio.mp3.MP3.VBR_TYPE;

public class MP3HeaderVBRVbri implements MP3HeaderVBR {

    private byte[] header;
    private long bytes = 0l;
    private long frameLength = 0l;
    private long quality = 0l;
    private byte[] rawToc;
    private long[] toc;

    private int vbriVersion;
    private int delay;
    private int numberOfEntries;
    private int scaleFactorOfTocTable;
    private int sizePerTableEntry;
    private int framesPerTableEntry;

    public MP3HeaderVBRVbri(byte[] header) {
        this.header = header;
    }

    @Override
    public VBR_TYPE getVbrType() {
        return VBR_TYPE.VBR_VBRI;
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

    public int getVbriVersion() {
        return vbriVersion <= 0 ? vbriVersion = MP3.findVbrVbriVersion(header) : vbriVersion;
    }

    public int getVbriDelay() {
        return delay <= 0 ? delay = MP3.findVbrVbriDelay(header) : delay;
    }

    public int getNumberOfEntries() {
        return numberOfEntries <= 0 ? numberOfEntries = MP3.findVbrVbriNumberOfEntries(header) : numberOfEntries;
    }

    public int getScaleFactorOfTocTable() {
        return scaleFactorOfTocTable <= 0 ? scaleFactorOfTocTable = MP3.findVbrVbriScaleFactorOfTocTableEntries(header) : scaleFactorOfTocTable;
    }

    public int getSizePerTableEntry() {
        return sizePerTableEntry <= 0 ? sizePerTableEntry = MP3.findVbrVbriSizePerTableEntry(header) : sizePerTableEntry;
    }

    public int getFramesPerTableEntry() {
        return framesPerTableEntry <= 0 ? framesPerTableEntry = MP3.findVbrVbriFramesPerTableEntry(header) : framesPerTableEntry;
    }

    @Override
    public void refresh(byte[] rawData) throws AudioException {
        this.header = rawData;
        bytes = 0l;
        frameLength = 0l;
        quality = 0;
        numberOfEntries = 0;
        scaleFactorOfTocTable = 0;
        sizePerTableEntry = 0;
        framesPerTableEntry = 0;
        toc = null;
        rawToc = null;
        vbriVersion = 0;
        delay = 0;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("VBR TYPE : " + getVbrType().toString());
        sb.append(", " + getBytes() + " bytes, ");
        sb.append(getFrameLength() + " frames, ");
        sb.append(getQuality() + " quality, ");
        sb.append(getNumberOfEntries() + " entries, ");
        sb.append(getScaleFactorOfTocTable() + " scale factor, ");
        sb.append(getSizePerTableEntry() + " size per entry, ");
        sb.append(getFramesPerTableEntry() + " frames per entry, ");
        sb.append("TOC size " + (getNumberOfEntries() * getSizePerTableEntry()));
        return sb.toString();
    }
}

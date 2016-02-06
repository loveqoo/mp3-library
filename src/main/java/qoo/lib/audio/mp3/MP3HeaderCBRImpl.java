package qoo.lib.audio.mp3;

import java.util.HashMap;
import java.util.Map;

import qoo.lib.audio.exception.AudioException;
import qoo.lib.audio.mp3.MP3.ANALYZE_ATTRIBUTE;
import qoo.lib.audio.mp3.MP3.BITRATE;
import qoo.lib.audio.mp3.MP3.CHANNEL_MODE;
import qoo.lib.audio.mp3.MP3.EMPHASIS;
import qoo.lib.audio.mp3.MP3.LAYER;
import qoo.lib.audio.mp3.MP3.MODE_EXTENSION;
import qoo.lib.audio.mp3.MP3.SAMPLING_RATE;
import qoo.lib.audio.mp3.MP3.VERSION;
import qoo.lib.concurrent.NotThreadSafe;

@NotThreadSafe
public class MP3HeaderCBRImpl implements MP3HeaderCBR {

    private byte[] header;
    private VERSION version;
    private LAYER layer;
    private BITRATE bitrate;
    private SAMPLING_RATE samplingRate;

    public MP3HeaderCBRImpl(byte[] headerRaw) {
        this.header = headerRaw;
    }

    @Override
    public VERSION getVersion() throws AudioException {
        return version == null ? version = MP3.findVersion(header) : version;
    }

    @Override
    public LAYER getLayer() throws AudioException {
        return layer == null ? layer = MP3.findLayer(header) : layer;
    }

    @Override
    public BITRATE getBitrate() throws AudioException {
        return bitrate == null ? bitrate = MP3.findBitrate(header) : bitrate;
    }

    @Override
    public SAMPLING_RATE getSamplingRate() throws AudioException {
        return samplingRate == null ? samplingRate = MP3.findSamplingRate(header) : samplingRate;
    }

    @Override
    public CHANNEL_MODE getChannelMode() throws AudioException {
        return MP3.findChannelMode(header);
    }

    @Override
    public MODE_EXTENSION getModeExtension() throws AudioException {
        return MP3.findModeExtension(header);
    }

    @Override
    public EMPHASIS getEmphasis() throws AudioException {
        return MP3.findEmphasis(header);
    }

    @Override
    public boolean hasProtectionBit() throws AudioException {
        return MP3.hasProtectionBit(header);
    }

    @Override
    public boolean hasPaddingBit() throws AudioException {
        return MP3.hasPaddingBit(header);
    }

    @Override
    public boolean hasPrivateBit() throws AudioException {
        return MP3.hasPrivateBit(header);
    }

    @Override
    public boolean hasCopyrightBit() throws AudioException {
        return MP3.hasCopyrightBit(header);
    }

    @Override
    public boolean hasOriginalBit() throws AudioException {
        return MP3.hasOriginalBit(header);
    }

    @Override
    public long getFrameSize() throws AudioException {
        return MP3.calculateFrameSize(header);
    }

    @Override
    public long getDuration() throws AudioException {
        return MP3.calculateDurationPerFrame(header);
    }

    @Override
    public void refresh(byte[] headerRaw) throws AudioException {
        this.header = headerRaw;
        this.version = null;
        this.layer = null;
        this.bitrate = null;
        this.samplingRate = null;
        getVersion();
        getLayer();
        getBitrate();
        getSamplingRate();
    }

    @Override
    public Map<String, String> analyze() throws AudioException {
        Map<String, String> mp3AnalyzeMap = new HashMap<String, String>(16);
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.VERSION.toString(), getVersion().toString());
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.LAYER.toString(), getLayer().toString());
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.BITRATE.toString(), getBitrate().toString());
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.SAMPLING_RATE.toString(), getSamplingRate().toString());
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.CHANNEL_MODE.toString(), getChannelMode().toString());
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.MODE_EXTENSION.toString(), getModeExtension().toString());
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.EMPHASIS.toString(), getEmphasis().toString());
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.SAMPLES_PER_FRAME.toString(), MP3.findSamplesPerFrame(header).toString());
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.PADDING_SIZE.toString(), Integer.toString(MP3.findPaddingSize(header)));
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.FRAME_SIZE.toString(), Long.toString(MP3.calculateFrameSize(header)));
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.DURATION.toString(), Long.toString(getDuration()));
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.PROTECTION_BIT.toString(), Boolean.toString(hasProtectionBit()));
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.PADDING_BIT.toString(), Boolean.toString(hasPaddingBit()));
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.PRIVATE_BIT.toString(), Boolean.toString(hasPrivateBit()));
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.COPYRIGHT_BIT.toString(), Boolean.toString(hasCopyrightBit()));
        mp3AnalyzeMap.put(ANALYZE_ATTRIBUTE.ORIGINAL_BIT.toString(), Boolean.toString(hasOriginalBit()));
        return mp3AnalyzeMap;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Version : ");
        sb.append(getVersion());
        sb.append(", Layer : ");
        sb.append(getLayer());
        sb.append(", Bitrate : ");
        sb.append(getBitrate().toInt());
        sb.append(", Sampling rate : ");
        sb.append(getSamplingRate().toInt());
        return sb.toString();
    }
}

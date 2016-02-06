package qoo.lib.audio.mp3;

import static qoo.lib.util.Bytes.BIT_0;
import static qoo.lib.util.Bytes.BIT_1;
import static qoo.lib.util.Bytes.BIT_2;
import static qoo.lib.util.Bytes.BIT_3;
import static qoo.lib.util.Bytes.BIT_4;
import static qoo.lib.util.Bytes.BIT_5;
import static qoo.lib.util.Bytes.BIT_6;
import static qoo.lib.util.Bytes.BIT_7;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import qoo.lib.audio.exception.AudioException;
import qoo.lib.audio.exception.InvalidResultException;
import qoo.lib.concurrent.ThreadSafe;
import qoo.lib.util.Bytes;

@ThreadSafe
public class MP3 {

    static final int MASK_ID = BIT_3;
    static final int MASK_VERSION = BIT_4 | BIT_3;
    static final int MASK_LAYER = BIT_2 | BIT_1;
    static final int MASK_BITRATE = BIT_7 | BIT_6 | BIT_5 | BIT_4;
    static final int MASK_SAMPLING_RATE = BIT_3 | BIT_2;
    static final int MASK_CHANNEL_MODE = BIT_7 | BIT_6;
    static final int MASK_MODE_EXTENSION = BIT_5 | BIT_4;
    static final int MASK_EMPHASIS = BIT_1 | BIT_0;
    static final int MASK_PROTECTION_BIT = BIT_0;
    static final int MASK_PADDING_BIT = BIT_1;
    static final int MASK_PRIVATE_BIT = BIT_0;
    static final int MASK_COPYRIGHT_BIT = BIT_3;
    static final int MASK_ORIGINAL_BIT = BIT_2;

    static final int MASK_SYNC_BYTE1 = 0xFF;
    static final int MASK_SYNC_BYTE2 = 0xE0;

    static final int MASK_VBR_XING_FRAMES = BIT_0;
    static final int MASK_VBR_XING_BYTES = BIT_1;
    static final int MASK_VBR_XING_TOC = BIT_2;
    static final int MASK_VBR_XING_QUALITY = BIT_3;

    static final int MASK_VBR_VBRI_FRAMES = 0;
    static final int MASK_VBR_VBRI_BYTES = 0;
    static final int MASK_VBR_VBRI_QUALITY = 0;

    static final Map<Integer, MP3.VERSION> VERSION_MAP;
    static final Map<Integer, MP3.LAYER> LAYER_MAP;
    static final Map<Integer, MP3.BITRATE> BITRATE_MAP;
    static final Map<Integer, MP3.SAMPLING_RATE> SAMPLING_RATE_MAP;
    static final Map<Integer, MP3.SAMPLES_PER_FRAME> SAMPLES_PER_FRAME_MAP;
    static final Map<Integer, MP3.MODE_EXTENSION> MODE_EXTENSION_MAP;
    static final Map<Integer, MP3.CHANNEL_MODE> CHANNEL_MODE_MAP;
    static final Map<Integer, MP3.EMPHASIS> EMPHASIS_MAP;
    static final byte[][] VBR_IDENTIFIRE;

    static {
        Map<Integer, MP3.VERSION> versionMap = new HashMap<Integer, MP3.VERSION>(4);
        versionMap.put(0, VERSION.VERSION_2_5);
        versionMap.put(1, VERSION.VERSION_RESERVED);
        versionMap.put(2, VERSION.VERSION_2);
        versionMap.put(3, VERSION.VERSION_1);
        VERSION_MAP = Collections.unmodifiableMap(versionMap);

        Map<Integer, MP3.LAYER> layerMap = new HashMap<Integer, MP3.LAYER>(4);
        layerMap.put(0x00, LAYER.LAYER_RESERVED);
        layerMap.put(0x01, LAYER.LAYER_3);
        layerMap.put(0x02, LAYER.LAYER_2);
        layerMap.put(0x03, LAYER.LAYER_1);
        LAYER_MAP = Collections.unmodifiableMap(layerMap);

        Map<Integer, MP3.BITRATE> bitrateMap = new HashMap<Integer, MP3.BITRATE>(96);
        // -- MPEG 1 Layer 1
        bitrateMap.put(0x0E, BITRATE.BITRATE_FREE);
        bitrateMap.put(0x1E, BITRATE.BITRATE_32);
        bitrateMap.put(0x2E, BITRATE.BITRATE_64);
        bitrateMap.put(0x3E, BITRATE.BITRATE_96);
        bitrateMap.put(0x4E, BITRATE.BITRATE_128);
        bitrateMap.put(0x5E, BITRATE.BITRATE_160);
        bitrateMap.put(0x6E, BITRATE.BITRATE_192);
        bitrateMap.put(0x7E, BITRATE.BITRATE_224);
        bitrateMap.put(0x8E, BITRATE.BITRATE_256);
        bitrateMap.put(0x9E, BITRATE.BITRATE_288);
        bitrateMap.put(0xAE, BITRATE.BITRATE_320);
        bitrateMap.put(0xBE, BITRATE.BITRATE_352);
        bitrateMap.put(0xCE, BITRATE.BITRATE_384);
        bitrateMap.put(0xDE, BITRATE.BITRATE_416);
        bitrateMap.put(0xEE, BITRATE.BITRATE_448);
        bitrateMap.put(0xFE, BITRATE.BITRATE_RESERVED);
        // -- MPEG 1 Layer 2
        bitrateMap.put(0x0C, BITRATE.BITRATE_FREE);
        bitrateMap.put(0x1C, BITRATE.BITRATE_32);
        bitrateMap.put(0x2C, BITRATE.BITRATE_48);
        bitrateMap.put(0x3C, BITRATE.BITRATE_56);
        bitrateMap.put(0x4C, BITRATE.BITRATE_64);
        bitrateMap.put(0x5C, BITRATE.BITRATE_80);
        bitrateMap.put(0x6C, BITRATE.BITRATE_96);
        bitrateMap.put(0x7C, BITRATE.BITRATE_112);
        bitrateMap.put(0x8C, BITRATE.BITRATE_128);
        bitrateMap.put(0x9C, BITRATE.BITRATE_160);
        bitrateMap.put(0xAC, BITRATE.BITRATE_192);
        bitrateMap.put(0xBC, BITRATE.BITRATE_224);
        bitrateMap.put(0xCC, BITRATE.BITRATE_256);
        bitrateMap.put(0xDC, BITRATE.BITRATE_320);
        bitrateMap.put(0xEC, BITRATE.BITRATE_384);
        bitrateMap.put(0xFC, BITRATE.BITRATE_RESERVED);
        // -- MPEG 1 Layer 3
        bitrateMap.put(0x0A, BITRATE.BITRATE_FREE);
        bitrateMap.put(0x1A, BITRATE.BITRATE_32);
        bitrateMap.put(0x2A, BITRATE.BITRATE_40);
        bitrateMap.put(0x3A, BITRATE.BITRATE_48);
        bitrateMap.put(0x4A, BITRATE.BITRATE_56);
        bitrateMap.put(0x5A, BITRATE.BITRATE_64);
        bitrateMap.put(0x6A, BITRATE.BITRATE_80);
        bitrateMap.put(0x7A, BITRATE.BITRATE_96);
        bitrateMap.put(0x8A, BITRATE.BITRATE_112);
        bitrateMap.put(0x9A, BITRATE.BITRATE_128);
        bitrateMap.put(0xAA, BITRATE.BITRATE_160);
        bitrateMap.put(0xBA, BITRATE.BITRATE_192);
        bitrateMap.put(0xCA, BITRATE.BITRATE_224);
        bitrateMap.put(0xDA, BITRATE.BITRATE_256);
        bitrateMap.put(0xEA, BITRATE.BITRATE_320);
        bitrateMap.put(0xFA, BITRATE.BITRATE_RESERVED);
        // -- MPEG 2 or MPEG 2.5 Layer 1
        bitrateMap.put(0x06, BITRATE.BITRATE_FREE);
        bitrateMap.put(0x16, BITRATE.BITRATE_32);
        bitrateMap.put(0x26, BITRATE.BITRATE_48);
        bitrateMap.put(0x36, BITRATE.BITRATE_56);
        bitrateMap.put(0x46, BITRATE.BITRATE_64);
        bitrateMap.put(0x56, BITRATE.BITRATE_80);
        bitrateMap.put(0x66, BITRATE.BITRATE_96);
        bitrateMap.put(0x76, BITRATE.BITRATE_112);
        bitrateMap.put(0x86, BITRATE.BITRATE_128);
        bitrateMap.put(0x96, BITRATE.BITRATE_144);
        bitrateMap.put(0xA6, BITRATE.BITRATE_160);
        bitrateMap.put(0xB6, BITRATE.BITRATE_176);
        bitrateMap.put(0xC6, BITRATE.BITRATE_192);
        bitrateMap.put(0xD6, BITRATE.BITRATE_224);
        bitrateMap.put(0xE6, BITRATE.BITRATE_256);
        bitrateMap.put(0xF6, BITRATE.BITRATE_RESERVED);
        // -- MPEG 2 or MPEG 2.5 Layer 2
        bitrateMap.put(0x04, BITRATE.BITRATE_FREE);
        bitrateMap.put(0x14, BITRATE.BITRATE_8);
        bitrateMap.put(0x24, BITRATE.BITRATE_16);
        bitrateMap.put(0x34, BITRATE.BITRATE_24);
        bitrateMap.put(0x44, BITRATE.BITRATE_32);
        bitrateMap.put(0x54, BITRATE.BITRATE_40);
        bitrateMap.put(0x64, BITRATE.BITRATE_48);
        bitrateMap.put(0x74, BITRATE.BITRATE_56);
        bitrateMap.put(0x84, BITRATE.BITRATE_64);
        bitrateMap.put(0x94, BITRATE.BITRATE_80);
        bitrateMap.put(0xA4, BITRATE.BITRATE_96);
        bitrateMap.put(0xB4, BITRATE.BITRATE_112);
        bitrateMap.put(0xC4, BITRATE.BITRATE_128);
        bitrateMap.put(0xD4, BITRATE.BITRATE_144);
        bitrateMap.put(0xE4, BITRATE.BITRATE_160);
        bitrateMap.put(0xF4, BITRATE.BITRATE_RESERVED);
        // -- MPEG 2 or MPEG 2.5 Layer 3
        bitrateMap.put(0x02, BITRATE.BITRATE_FREE);
        bitrateMap.put(0x12, BITRATE.BITRATE_8);
        bitrateMap.put(0x22, BITRATE.BITRATE_16);
        bitrateMap.put(0x32, BITRATE.BITRATE_24);
        bitrateMap.put(0x42, BITRATE.BITRATE_32);
        bitrateMap.put(0x52, BITRATE.BITRATE_40);
        bitrateMap.put(0x62, BITRATE.BITRATE_48);
        bitrateMap.put(0x72, BITRATE.BITRATE_56);
        bitrateMap.put(0x82, BITRATE.BITRATE_64);
        bitrateMap.put(0x92, BITRATE.BITRATE_80);
        bitrateMap.put(0xA2, BITRATE.BITRATE_96);
        bitrateMap.put(0xB2, BITRATE.BITRATE_112);
        bitrateMap.put(0xC2, BITRATE.BITRATE_128);
        bitrateMap.put(0xD2, BITRATE.BITRATE_144);
        bitrateMap.put(0xE2, BITRATE.BITRATE_160);
        bitrateMap.put(0xF2, BITRATE.BITRATE_RESERVED);
        BITRATE_MAP = Collections.unmodifiableMap(bitrateMap);

        Map<Integer, MP3.SAMPLING_RATE> samplingRateMap = new HashMap<Integer, MP3.SAMPLING_RATE>(12);
        // -- MPEG 1
        samplingRateMap.put(0x03, SAMPLING_RATE.SAMPLING_RATE_44100);
        samplingRateMap.put(0x13, SAMPLING_RATE.SAMPLING_RATE_48000);
        samplingRateMap.put(0x23, SAMPLING_RATE.SAMPLING_RATE_32000);
        samplingRateMap.put(0x33, SAMPLING_RATE.SAMPLING_RATE_RESERVED);
        // -- MPEG 2
        samplingRateMap.put(0x02, SAMPLING_RATE.SAMPLING_RATE_22050);
        samplingRateMap.put(0x12, SAMPLING_RATE.SAMPLING_RATE_24000);
        samplingRateMap.put(0x22, SAMPLING_RATE.SAMPLING_RATE_16000);
        samplingRateMap.put(0x32, SAMPLING_RATE.SAMPLING_RATE_RESERVED);
        // -- MPEG 2.5
        samplingRateMap.put(0x00, SAMPLING_RATE.SAMPLING_RATE_11025);
        samplingRateMap.put(0x10, SAMPLING_RATE.SAMPLING_RATE_12000);
        samplingRateMap.put(0x20, SAMPLING_RATE.SAMPLING_RATE_8000);
        samplingRateMap.put(0x30, SAMPLING_RATE.SAMPLING_RATE_RESERVED);
        SAMPLING_RATE_MAP = Collections.unmodifiableMap(samplingRateMap);

        Map<Integer, MP3.SAMPLES_PER_FRAME> samplesPerFrameMap = new HashMap<Integer, MP3.SAMPLES_PER_FRAME>(9);
        // -- MPEG 1 Layer 1
        samplesPerFrameMap.put(0x33, SAMPLES_PER_FRAME.SAMPLES_384);
        // -- MPEG 1 Layer 2
        samplesPerFrameMap.put(0x23, SAMPLES_PER_FRAME.SAMPLES_1152);
        // -- MPEG 1 Layer 3
        samplesPerFrameMap.put(0x13, SAMPLES_PER_FRAME.SAMPLES_1152);
        // -- MPEG 2 Layer 1
        samplesPerFrameMap.put(0x32, SAMPLES_PER_FRAME.SAMPLES_384);
        // -- MPEG 2 Layer 2
        samplesPerFrameMap.put(0x22, SAMPLES_PER_FRAME.SAMPLES_1152);
        // -- MPEG 2 Layer 3
        samplesPerFrameMap.put(0x12, SAMPLES_PER_FRAME.SAMPLES_576);
        // -- MPEG 2.5 Layer 1
        samplesPerFrameMap.put(0x30, SAMPLES_PER_FRAME.SAMPLES_384);
        // -- MPEG 2.5 Layer 2
        samplesPerFrameMap.put(0x20, SAMPLES_PER_FRAME.SAMPLES_1152);
        // -- MPEG 2.5 Layer 3
        samplesPerFrameMap.put(0x10, SAMPLES_PER_FRAME.SAMPLES_576);
        SAMPLES_PER_FRAME_MAP = Collections.unmodifiableMap(samplesPerFrameMap);

        Map<Integer, MP3.MODE_EXTENSION> modeExtensionMap = new HashMap<Integer, MP3.MODE_EXTENSION>(12);
        // -- Layer 1
        modeExtensionMap.put(0x06, MODE_EXTENSION.BANDS_4_TO_31);
        modeExtensionMap.put(0x16, MODE_EXTENSION.BANDS_8_TO_31);
        modeExtensionMap.put(0x26, MODE_EXTENSION.BANDS_12_TO_31);
        modeExtensionMap.put(0x36, MODE_EXTENSION.BANDS_16_TO_31);
        // -- Layer 2
        modeExtensionMap.put(0x04, MODE_EXTENSION.BANDS_4_TO_31);
        modeExtensionMap.put(0x14, MODE_EXTENSION.BANDS_8_TO_31);
        modeExtensionMap.put(0x24, MODE_EXTENSION.BANDS_12_TO_31);
        modeExtensionMap.put(0x34, MODE_EXTENSION.BANDS_16_TO_31);
        // -- Layer 3
        modeExtensionMap.put(0x02, MODE_EXTENSION.MS_OFF_INTENSITY_OFF);
        modeExtensionMap.put(0x12, MODE_EXTENSION.MS_OFF_INTENSITY_ON);
        modeExtensionMap.put(0x22, MODE_EXTENSION.MS_ON_INTENSITY_OFF);
        modeExtensionMap.put(0x32, MODE_EXTENSION.MS_ON_INTENSITY_ON);
        MODE_EXTENSION_MAP = Collections.unmodifiableMap(modeExtensionMap);

        Map<Integer, MP3.CHANNEL_MODE> channelModeMap = new HashMap<Integer, MP3.CHANNEL_MODE>(4);
        channelModeMap.put(0x00, CHANNEL_MODE.STEREO);
        channelModeMap.put(0x01, CHANNEL_MODE.JOINT_STEREO);
        channelModeMap.put(0x02, CHANNEL_MODE.DUAL_CHANNEL);
        channelModeMap.put(0x03, CHANNEL_MODE.SINGLE_CHANNEL);
        CHANNEL_MODE_MAP = Collections.unmodifiableMap(channelModeMap);

        Map<Integer, MP3.EMPHASIS> emphasisMap = new HashMap<Integer, MP3.EMPHASIS>(4);
        emphasisMap.put(0x00, EMPHASIS.EMPHASIS_NONE);
        emphasisMap.put(0x01, EMPHASIS.EMPHASIS_50_15_MS);
        emphasisMap.put(0x02, EMPHASIS.EMPHASIS_RESERVED);
        emphasisMap.put(0x03, EMPHASIS.EMPHASIS_CCIT_J_17);
        EMPHASIS_MAP = Collections.unmodifiableMap(emphasisMap);

        VBR_IDENTIFIRE = new byte[][] { { 'X', 'i', 'n', 'g' }, { 'I', 'n', 'f', 'o' }, { 'V', 'B', 'R', 'I' } };
    }

    static public enum VERSION {
        VERSION_1, VERSION_2, VERSION_2_5, VERSION_RESERVED;
    }

    static public enum LAYER {
        LAYER_1, LAYER_2, LAYER_3, LAYER_RESERVED;
    }

    static public enum BITRATE {
        BITRATE_RESERVED, BITRATE_FREE, BITRATE_8, BITRATE_16, BITRATE_24, BITRATE_32, BITRATE_40, BITRATE_48, BITRATE_56, BITRATE_64, BITRATE_80, BITRATE_96, BITRATE_112, BITRATE_128, BITRATE_144, BITRATE_160, BITRATE_176, BITRATE_192, BITRATE_224, BITRATE_256, BITRATE_288, BITRATE_320, BITRATE_352, BITRATE_384, BITRATE_416, BITRATE_448;

        public int toInt() {
            switch (this) {
            case BITRATE_8:
                return 8;
            case BITRATE_16:
                return 16;
            case BITRATE_24:
                return 24;
            case BITRATE_32:
                return 32;
            case BITRATE_40:
                return 40;
            case BITRATE_48:
                return 48;
            case BITRATE_56:
                return 56;
            case BITRATE_64:
                return 64;
            case BITRATE_80:
                return 80;
            case BITRATE_96:
                return 96;
            case BITRATE_112:
                return 112;
            case BITRATE_128:
                return 128;
            case BITRATE_144:
                return 144;
            case BITRATE_160:
                return 160;
            case BITRATE_176:
                return 176;
            case BITRATE_192:
                return 192;
            case BITRATE_224:
                return 224;
            case BITRATE_256:
                return 256;
            case BITRATE_288:
                return 288;
            case BITRATE_320:
                return 320;
            case BITRATE_352:
                return 352;
            case BITRATE_384:
                return 384;
            case BITRATE_416:
                return 416;
            case BITRATE_448:
                return 448;
            default:
                return 0;
            }
        }
    }

    static public enum SAMPLING_RATE {
        SAMPLING_RATE_RESERVED, SAMPLING_RATE_8000, SAMPLING_RATE_11025, SAMPLING_RATE_12000, SAMPLING_RATE_16000, SAMPLING_RATE_22050, SAMPLING_RATE_24000, SAMPLING_RATE_32000, SAMPLING_RATE_44100, SAMPLING_RATE_48000;

        public int toInt() {
            switch (this) {
            case SAMPLING_RATE_8000:
                return 8000;
            case SAMPLING_RATE_11025:
                return 11025;
            case SAMPLING_RATE_12000:
                return 12000;
            case SAMPLING_RATE_16000:
                return 16000;
            case SAMPLING_RATE_22050:
                return 22050;
            case SAMPLING_RATE_24000:
                return 24000;
            case SAMPLING_RATE_32000:
                return 32000;
            case SAMPLING_RATE_44100:
                return 44100;
            case SAMPLING_RATE_48000:
                return 48000;
            default:
                return 0;
            }
        }
    }

    static public enum SAMPLES_PER_FRAME {
        SAMPLES_384, SAMPLES_576, SAMPLES_1152;

        public int toInt() {
            switch (this) {
            case SAMPLES_384:
                return 384;
            case SAMPLES_576:
                return 576;
            case SAMPLES_1152:
                return 1152;
            }
            return -1;
        }
    }

    static public enum ANALYZE_ATTRIBUTE {
        VERSION, LAYER, BITRATE, SAMPLING_RATE, CHANNEL_MODE, MODE_EXTENSION, EMPHASIS, SAMPLES_PER_FRAME, PADDING_SIZE, FRAME_SIZE, DURATION, PROTECTION_BIT, PADDING_BIT, PRIVATE_BIT, COPYRIGHT_BIT, ORIGINAL_BIT;
    }

    static public enum CHANNEL_MODE {
        STEREO, JOINT_STEREO, DUAL_CHANNEL, SINGLE_CHANNEL
    }

    static public enum MODE_EXTENSION {
        BANDS_4_TO_31, BANDS_8_TO_31, BANDS_12_TO_31, BANDS_16_TO_31, MS_ON_INTENSITY_ON, MS_OFF_INTENSITY_OFF, MS_ON_INTENSITY_OFF, MS_OFF_INTENSITY_ON
    }

    static public enum EMPHASIS {
        EMPHASIS_NONE, EMPHASIS_50_15_MS, EMPHASIS_RESERVED, EMPHASIS_CCIT_J_17
    }

    static public enum VBR_TYPE {
        VBR_XING_X, VBR_XING_I, VBR_VBRI;
    }

    static public byte[] getVbrIdentifier(VBR_TYPE vbrType) {
        if (vbrType == VBR_TYPE.VBR_XING_X) {
            return VBR_IDENTIFIRE[0];
        } else if (vbrType == VBR_TYPE.VBR_XING_I) {
            return VBR_IDENTIFIRE[1];
        } else {
            return VBR_IDENTIFIRE[2];
        }
    }

    static public VERSION findVersion(byte[] header) {
        int version = ((header[1] & MASK_VERSION) >> 3);
        return VERSION_MAP.get(version);
    }

    static public LAYER findLayer(byte[] header) {
        int layer = ((header[1] & MASK_LAYER) >> 1);
        return LAYER_MAP.get(layer);
    }

    /**
     * <pre>
     * MPEG 2, 2.5 + Layer 3 0000 | 0010 = 0010 = 2
     * MPEG 2, 2.5 + Layer 2 0000 | 0100 = 0100 = 4
     * MPEG 2, 2.5 + Layer 1 0000 | 0110 = 0110 = 6
     * MPEG 1 + Layer 3 1000 | 0010 = 1010 = A
     * MPEG 1 + Layer 2 1000 | 0100 = 1100 = C
     * MPEG 1 + Layer 1 1000 | 0110 = 1110 = E
     * 
     * <pre>
     */
    static public BITRATE findBitrate(byte[] header) {
        int bitrateIndex = (header[2] & MASK_BITRATE | header[1] & MASK_ID | header[1] & MASK_LAYER);
        return BITRATE_MAP.get(bitrateIndex);
    }

    static public SAMPLING_RATE findSamplingRate(byte[] header) {
        int samplingRateIndex = ((header[2] & MASK_SAMPLING_RATE) << 2 | (header[1] & MASK_VERSION) >> 3);
        return SAMPLING_RATE_MAP.get(samplingRateIndex);
    }

    static public CHANNEL_MODE findChannelMode(byte[] header) {
        int channelModeIndex = ((header[3] & MASK_CHANNEL_MODE) >> 6);
        return CHANNEL_MODE_MAP.get(channelModeIndex);
    }

    static public MODE_EXTENSION findModeExtension(byte[] header) {
        int modeExtensionIndex = (header[3] & MASK_MODE_EXTENSION | header[1] & MASK_LAYER);
        return MODE_EXTENSION_MAP.get(modeExtensionIndex);
    }

    static public EMPHASIS findEmphasis(byte[] header) {
        int emphasisIndex = (header[3] & MASK_EMPHASIS);
        return EMPHASIS_MAP.get(emphasisIndex);
    }

    static public SAMPLES_PER_FRAME findSamplesPerFrame(byte[] header) {
        // header[1] & AudioHeader.MASK_VERSION >> 3 : 0000 00??
        // header[1] & AudioHeader.MASK_LAYER : 0000 0??0
        int samplesPerFrameIndex = ((header[1] & MASK_LAYER) << 3 | (header[1] & MASK_VERSION) >> 3);
        return SAMPLES_PER_FRAME_MAP.get(samplesPerFrameIndex);
    }

    /**
     * Layer 1 : 4 bytes Layer 2 : 1 bytes Layer 2.5 : 1 bytes
     */
    static public int findPaddingSize(byte[] header) {
        boolean isPadding = (header[2] & MASK_PADDING_BIT) != 0;
        if (!isPadding)
            return 0;
        MP3.LAYER layer = findLayer(header);
        if (layer == LAYER.LAYER_1)
            return 4;
        else
            return 1;
    }

    static public long calculateFrameSize(byte[] header) {
        long frameSize = ((findSamplesPerFrame(header).toInt() * findBitrate(header).toInt() * 125) / findSamplingRate(header).toInt())
                + findPaddingSize(header);
        if (frameSize <= 0)
            throw new InvalidResultException("Invalid Frame size : " + frameSize);
        return frameSize;
    }

    static public long calculateDurationPerFrame(byte[] header) {
        SAMPLING_RATE samplingRate = findSamplingRate(header);
        SAMPLES_PER_FRAME samplesPerFrame = findSamplesPerFrame(header);
        VERSION version = findVersion(header);
        long durationPerFrame = (long) samplesPerFrame.toInt() * 1000 / (long) samplingRate.toInt();
        if (version == VERSION.VERSION_2 || version == VERSION.VERSION_2_5) {
            CHANNEL_MODE channelMode = findChannelMode(header);
            if (channelMode == CHANNEL_MODE.SINGLE_CHANNEL) {
                return durationPerFrame / 2;
            }
        }
        return durationPerFrame;
    }

    static public long calculateDuration(long fileSize, byte[] header) {
        long duration = ((long) fileSize * 1000 / (long) (findBitrate(header).toInt() * 8000));
        if (duration <= 0)
            throw new InvalidResultException("Invalid Duration (sec) : " + duration);
        return duration;
    }

    static public boolean hasProtectionBit(byte[] header) {
        return (header[1] & MASK_PROTECTION_BIT) != 0;
    }

    static public boolean hasPaddingBit(byte[] header) {
        return (header[2] & MASK_PADDING_BIT) != 0;
    }

    static public boolean hasPrivateBit(byte[] header) {
        return (header[2] & MASK_PRIVATE_BIT) != 0;
    }

    static public boolean hasCopyrightBit(byte[] header) {
        return (header[3] & MASK_COPYRIGHT_BIT) != 0;
    }

    static public boolean hasOriginalBit(byte[] header) {
        return (header[3] & MASK_ORIGINAL_BIT) != 0;
    }

    static public VBR_TYPE findVbrType(byte[] vbrHeader) {
        byte[] identifier = Arrays.copyOf(vbrHeader, 4);
        if (Arrays.equals(identifier, VBR_IDENTIFIRE[0])) {
            return VBR_TYPE.VBR_XING_X;
        } else if (Arrays.equals(identifier, VBR_IDENTIFIRE[1])) {
            return VBR_TYPE.VBR_XING_I;
        } else if (Arrays.equals(identifier, VBR_IDENTIFIRE[2])) {
            return VBR_TYPE.VBR_VBRI;
        }
        return null;
    }

    static public int findVbrXingHeaderOffSet(VERSION version, CHANNEL_MODE channelMode) {
        return version == VERSION.VERSION_1 ? channelMode == CHANNEL_MODE.SINGLE_CHANNEL ? 21 : 36 : channelMode == CHANNEL_MODE.SINGLE_CHANNEL ? 13
                : 21;
    }

    static public int findVbrXingHeaderOffSet(byte[] header) {
        VERSION version = findVersion(header);
        CHANNEL_MODE channelMode = findChannelMode(header);
        return findVbrXingHeaderOffSet(version, channelMode);
    }

    static public int findVbrVbriHeaderOffset() {
        return 36;
    }

    static public int findVbrVbriVersion(byte[] vbrHeader) {
        return Bytes.getCharValue(vbrHeader, 4);
    }

    static public int findVbrVbriDelay(byte[] vbrHeader) {
        return Bytes.getCharValue(vbrHeader, 6);
    }

    static public int findVbrVbriNumberOfEntries(byte[] vbrHeader) {
        return Bytes.getCharValue(vbrHeader, 18);
    }

    static public int findVbrVbriScaleFactorOfTocTableEntries(byte[] vbrHeader) {
        return Bytes.getCharValue(vbrHeader, 20);
    }

    static public int findVbrVbriSizePerTableEntry(byte[] vbrHeader) {
        return Bytes.getCharValue(vbrHeader, 22);
    }

    static public int findVbrVbriFramesPerTableEntry(byte[] vbrHeader) {
        return Bytes.getCharValue(vbrHeader, 24);
    }

    static public byte[] findVbrRawToc(byte[] vbrHeader) {
        VBR_TYPE vbrType = findVbrType(vbrHeader);
        if (vbrType == VBR_TYPE.VBR_VBRI) {
            int tocSize = findVbrVbriNumberOfEntries(vbrHeader) * findVbrVbriSizePerTableEntry(vbrHeader);
            if (tocSize <= 0) {
                return null;
            }
            return Arrays.copyOfRange(vbrHeader, 26, 26 + tocSize);
        } else {
            boolean hasTocField = (vbrHeader[7] & MASK_VBR_XING_TOC) != 0;
            if (hasTocField) {
                return Arrays.copyOfRange(vbrHeader, 16, 116);
            }
        }
        return null;
    }

    static public long[] findVbrToc(byte[] vbrHeader) {
        VBR_TYPE vbrType = findVbrType(vbrHeader);
        if (vbrType == VBR_TYPE.VBR_VBRI) {
            byte[] rawToc = findVbrRawToc(vbrHeader);
            if (rawToc == null) {
                return null;
            }
            long[] rawToc2 = Bytes.byteArrayToLongArray(rawToc, 2);
            long[] toc = new long[(rawToc2.length) + 1];
            toc[0] = 0;
            for (int i = 1, k = 0, j = rawToc2.length; i <= j; i++, k++) {
                if (i <= 1) {
                    toc[i] = rawToc2[k];
                } else {
                    toc[i] = toc[i - 1] + rawToc2[k];
                }
            }
            return toc;
        } else {
            boolean hasTocField = (vbrHeader[7] & MASK_VBR_XING_TOC) != 0;
            if (!hasTocField) {
                throw new AudioException("Toc field is not set.");
            }
            long bytes = findVbrNumberOfBytes(vbrHeader);
            if (bytes <= 0) {
                throw new AudioException("Total Size(bytes) is not defined.");
            }
            byte[] rawToc = findVbrRawToc(vbrHeader);
            if (rawToc == null) {
                return null;
            }
            long[] toc = new long[rawToc.length];
            long unitSize = bytes / 255l;
            for (int i = 0, j = rawToc.length; i < j; i++) {
                toc[i] = (rawToc[i] & 0xff) * unitSize;
            }
            return toc;
        }
    }

    static public long findVbrNumberOfFrames(byte[] vbrHeader) {
        VBR_TYPE vbrType = findVbrType(vbrHeader);
        if (vbrType == VBR_TYPE.VBR_VBRI) {
            return Bytes.getIntValue(vbrHeader, 14);
        } else {
            boolean hasFrameField = (vbrHeader[7] & MASK_VBR_XING_FRAMES) != 0;
            if (hasFrameField) {
                return Bytes.getIntValue(vbrHeader, 8);
            }
        }
        return 0l;
    }

    static public long findVbrNumberOfBytes(byte[] vbrHeader) {
        VBR_TYPE vbrType = findVbrType(vbrHeader);
        if (vbrType == VBR_TYPE.VBR_VBRI) {
            return Bytes.getIntValue(vbrHeader, 10);
        } else {
            boolean hasByteField = (vbrHeader[7] & MASK_VBR_XING_BYTES) != 0;
            if (hasByteField) {
                return Bytes.getIntValue(vbrHeader, 12);
            }
        }
        return 0l;
    }

    static public long findVbrQualityIndicator(byte[] vbrHeader) {
        VBR_TYPE vbrType = findVbrType(vbrHeader);
        if (vbrType == VBR_TYPE.VBR_VBRI) {
            return Bytes.getCharValue(vbrHeader, 8);
        } else {
            boolean hasQualityField = (vbrHeader[7] & MASK_VBR_XING_QUALITY) != 0;
            boolean hasTocField = (vbrHeader[7] & MASK_VBR_XING_TOC) != 0;
            if (hasQualityField) {
                if (hasTocField) {
                    return Bytes.getIntValue(vbrHeader, 116);
                } else {
                    return Bytes.getIntValue(vbrHeader, 16);
                }
            }
        }
        return 0;
    }
}

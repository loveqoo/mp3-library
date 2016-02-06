package qoo.lib.audio.mp3;

import java.util.Map;

import qoo.lib.audio.Refreshable;
import qoo.lib.audio.exception.AudioException;

public interface MP3HeaderCBR extends Refreshable {

    MP3.VERSION getVersion() throws AudioException;

    MP3.LAYER getLayer() throws AudioException;

    MP3.BITRATE getBitrate() throws AudioException;

    MP3.SAMPLING_RATE getSamplingRate() throws AudioException;

    MP3.CHANNEL_MODE getChannelMode() throws AudioException;

    MP3.MODE_EXTENSION getModeExtension() throws AudioException;

    MP3.EMPHASIS getEmphasis() throws AudioException;

    boolean hasProtectionBit() throws AudioException;

    boolean hasPaddingBit() throws AudioException;

    boolean hasPrivateBit() throws AudioException;

    boolean hasCopyrightBit() throws AudioException;

    boolean hasOriginalBit() throws AudioException;

    long getFrameSize() throws AudioException;

    long getDuration() throws AudioException;

    void refresh(byte[] headerRaw) throws AudioException;

    Map<String, String> analyze() throws AudioException;
}

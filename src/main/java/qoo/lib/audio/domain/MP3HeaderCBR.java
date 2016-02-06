package qoo.lib.audio.domain;

import qoo.lib.audio.infrastructure.AudioException;

import static qoo.lib.audio.domain.MP3.*;

/**
 * Created by Anthony Jeong on 13. 12. 6.
 */
public interface MP3HeaderCBR extends MP3Header {

    VERSION getVersion() throws AudioException;

    LAYER getLayer() throws AudioException;

    BITRATE getBitrate() throws AudioException;

    SAMPLING_RATE getSamplingRate() throws AudioException;

    CHANNEL_MODE getChannelMode() throws AudioException;

    MODE_EXTENSION getModeExtension() throws AudioException;

    EMPHASIS getEmphasis() throws AudioException;

    boolean hasProtectionBit() throws AudioException;

    boolean hasPaddingBit() throws AudioException;

    boolean hasPrivateBit() throws AudioException;

    boolean hasCopyrightBit() throws AudioException;

    boolean hasOriginalBit() throws AudioException;

    long getFrameSize() throws AudioException;

    long getDuration() throws AudioException;
}

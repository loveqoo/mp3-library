package qoo.lib.audio;

import qoo.lib.audio.exception.AudioException;

public interface Refreshable {

    void refresh(byte[] rawData) throws AudioException;
}

package qoo.lib.audio.mp3;

import qoo.lib.audio.AudioSlice;

public interface AudioSliceMP3 extends AudioSlice {

    MP3HeaderCBR getHeader();

    MP3HeaderVBR getVBRHeader();

    void refresh(byte[] headerRaw);
}
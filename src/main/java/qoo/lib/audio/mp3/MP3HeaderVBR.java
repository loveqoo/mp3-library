package qoo.lib.audio.mp3;

import qoo.lib.audio.Refreshable;
import qoo.lib.audio.mp3.MP3.VBR_TYPE;

public interface MP3HeaderVBR extends Refreshable {

    VBR_TYPE getVbrType();

    long getBytes();

    long getFrameLength();

    long getQuality();

    long[] getToc();

    byte[] getRawToc();
}
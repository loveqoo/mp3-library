package qoo.lib.audio.mp3;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import qoo.lib.audio.AudioIterator;
import qoo.lib.audio.AudioIteratorFilter;
import qoo.lib.audio.AudioSlice;
import qoo.lib.audio.exception.AudioException;
import qoo.lib.audio.mp3.MP3.VBR_TYPE;
import qoo.lib.concurrent.NotThreadSafe;
import qoo.lib.file.FileByteBufferIterator;
import qoo.lib.file.FileByteBufferIteratorImpl;
import qoo.lib.file.FileByteBufferResult;

@NotThreadSafe
public class AudioIteratorMP3 implements AudioIterator {

    private final FileByteBufferIterator fileIter;
    private final List<AudioIteratorFilter> filters;
    private AudioSliceMP3 audioSlice;
    private boolean isCallHasNext = false;

    public AudioIteratorMP3(FileChannel ch, ByteBuffer buf) {
        this.fileIter = new FileByteBufferIteratorImpl(ch, buf);
        this.filters = new ArrayList<AudioIteratorFilter>();
    }

    @Override
    public boolean hasNext() {
        isCallHasNext = true;
        if (audioSlice != null) {
            fileIter.skip(audioSlice.getHeader().getFrameSize());
        }
        while (fileIter.hasNext()) {
            if (validAudioSlice(fileIter.next())) {
                return true;
            }
            fileIter.skip(1l);
        }
        return false;
    }

    @Override
    public AudioSlice next() {
        if (!isCallHasNext && !hasNext()) {
            throw new AudioException("It has not next item.");
        }
        isCallHasNext = false;
        return audioSlice;
    }

    @Override
    public void registFilter(AudioIteratorFilter filter) {
        filters.add(filter);
    }

    @Override
    public boolean validAudioSlice(final FileByteBufferResult fileByteBufferResult) {
        ByteBuffer buf = fileByteBufferResult.getByteBuffer();
        if (buf.limit() < 4)
            return false;
        int position = buf.position();
        boolean isMPEGHeader = ((buf.get(position) & MP3.MASK_SYNC_BYTE1) == MP3.MASK_SYNC_BYTE1)
                && ((buf.get(position + 1) & MP3.MASK_SYNC_BYTE2) == MP3.MASK_SYNC_BYTE2);
        if (!isMPEGHeader) {
            return false;
        }
        try {
            final byte[] mpegHeader;
            if (audioSlice == null) {
                mpegHeader = new byte[buf.limit()];
                buf.get(mpegHeader).position(position);
                audioSlice = new AudioSliceMP3() {

                    private MP3HeaderCBR headerCBR;
                    private MP3HeaderVBR headerVBR;
                    private long[] slice = new long[] { /* start */0l, /* end */0l, /* duration */0l, /* duration_acc */0l };
                    private boolean isFirstLoop = true;

                    @Override
                    public long[] getSlice() {
                        return slice;
                    }

                    @Override
                    public MP3HeaderCBR getHeader() {
                        return headerCBR;
                    }

                    @Override
                    public MP3HeaderVBR getVBRHeader() {
                        return headerVBR;
                    }

                    @Override
                    public void refresh(byte[] headerRaw) {
                        byte[] cbrHeaderRaw = headerRaw.length == 4 ? headerRaw : Arrays.copyOf(headerRaw, 4);
                        if (headerCBR == null) {
                            headerCBR = new MP3HeaderCBRImpl(cbrHeaderRaw);
                        }
                        headerCBR.refresh(cbrHeaderRaw);
                        if (headerVBR == null && isFirstLoop) {
                            // -- assume Xing header
                            int vbrOffSet = MP3.findVbrXingHeaderOffSet(headerCBR.getVersion(), headerCBR.getChannelMode());
                            byte[] vbrHeaderRaw = Arrays.copyOfRange(headerRaw, vbrOffSet, headerRaw.length);
                            VBR_TYPE vbrType = MP3.findVbrType(vbrHeaderRaw);
                            if (vbrType == null) {
                                // -- assume VBRI header
                                vbrOffSet = MP3.findVbrVbriHeaderOffset();
                                vbrHeaderRaw = Arrays.copyOfRange(headerRaw, vbrOffSet, headerRaw.length);
                                vbrType = MP3.findVbrType(vbrHeaderRaw);
                                if (vbrType == null) {
                                    // -- VBR header is not exists.
                                    return;
                                }
                            }
                            switch (vbrType) {
                            case VBR_XING_I:
                                headerVBR = new MP3HeaderVBRXing(vbrHeaderRaw);
                                break;
                            case VBR_XING_X:
                                headerVBR = new MP3HeaderVBRXing(vbrHeaderRaw);
                                break;
                            case VBR_VBRI:
                                headerVBR = new MP3HeaderVBRVbri(vbrHeaderRaw);
                                break;
                            default:
                                // -- ignore
                            }
                            headerVBR.refresh(vbrHeaderRaw);
                        }
                        isFirstLoop = false;
                    }

                    @Override
                    public String toString() {
                        StringBuffer sb = new StringBuffer();
                        sb.append(slice[0]);
                        sb.append(" - ");
                        sb.append(slice[1]);
                        sb.append(", header : ");
                        sb.append(headerCBR);
                        sb.append(", ");
                        sb.append(headerVBR);
                        return sb.toString();
                    }
                };
            } else {
                mpegHeader = new byte[4];
                buf.get(mpegHeader).position(position);
            }
            audioSlice.refresh(mpegHeader);
            audioSlice.getSlice()[0] = fileByteBufferResult.getCurrentLocation();
            audioSlice.getSlice()[1] = audioSlice.getSlice()[0] + audioSlice.getHeader().getFrameSize();
            audioSlice.getSlice()[2] = audioSlice.getHeader().getDuration();
            audioSlice.getSlice()[3] += audioSlice.getSlice()[2];
            if (filters.size() > 0) {
                for (AudioIteratorFilter filter : filters) {
                    if (filter.filter(audioSlice)) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        } catch (AudioException ae) {
            // -- ignore
            return false;
        }
    }

    @Override
    public void skip(long position) {
        fileIter.skipAbs(position);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

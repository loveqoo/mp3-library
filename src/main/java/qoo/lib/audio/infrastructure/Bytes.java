package qoo.lib.audio.infrastructure;

public class Bytes {

    static final public int BIT_0 = 0x01;
    static final public int BIT_1 = 0x02;
    static final public int BIT_2 = 0x04;
    static final public int BIT_3 = 0x08;
    static final public int BIT_4 = 0x10;
    static final public int BIT_5 = 0x20;
    static final public int BIT_6 = 0x40;
    static final public int BIT_7 = 0x80;

    /*
    static final public int BYTE_0 = 0;
    static final public int BYTE_1 = 1;
    static final public int BYTE_2 = 2;
    static final public int BYTE_3 = 3;
    */
    static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
            (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f' };

    static public String byteArrayToString(byte[] data, int columnCount) {
        StringBuffer sb = new StringBuffer();
        String space = " ";
        int column = 1;
        for (byte u : data) {
            sb.append(byteToHex(u));
            sb.append(space);
            if (column == columnCount) {
                sb.append("\n");
                column = 1;
                continue;
            }
            column++;
        }
        sb.append("\n");
        return sb.toString();
    }

    static public String byteArrayToString(byte[] data) {
        return byteArrayToString(data, 10);
    }

    static public String byteToHex(byte raw) {
        byte[] hex = new byte[2];
        int v = raw & 0xFF;
        hex[0] = HEX_CHAR_TABLE[v >>> 4];
        hex[1] = HEX_CHAR_TABLE[v & 0xF];
        return new String(hex);
    }
    /*
    static public long[] byteArrayToLongArray(byte[] data) {
        return byteArrayToLongArray(data, 8);
    }
    */
    static public long[] byteArrayToLongArray(byte[] data, int size) {
        int length = data.length / size;
        long[] result = new long[length];
        for (int i = 0, j = data.length, k = 0; i < j; i += size, k++) {
            switch (size) {
            case 1:
                result[k] = data[i] & 0xFF;
                break;
            case 2:
                result[k] = (data[i] & 0xFF) << 8 | data[i + 1] & 0xFF;
                break;
            case 3:
                result[k] = (data[i] & 0xFF) << 16 | (data[i + 1] & 0xFF) << 8 | data[i + 2] & 0xFF;
                break;
            case 4:
                result[k] = (data[i] & 0xFF) << 24 | (data[i + 1] & 0xFF) << 16 | (data[i + 2] & 0xFF) << 8 | data[i + 3] & 0xFF;
                break;
            case 5:
                result[k] = (data[i] & 0xFF) << 32 | (data[i + 1] & 0xFF) << 24 | (data[i + 2] & 0xFF) << 16 | (data[i + 3] & 0xFF) << 8
                        | data[i + 4] & 0xFF;
                break;
            case 6:
                result[k] = (data[i] & 0xFF) << 40 | (data[i + 1] & 0xFF) << 32 | (data[i + 2] & 0xFF) << 24 | (data[i + 3] & 0xFF) << 16
                        | (data[i + 4] & 0xFF) << 8 | data[i + 5] & 0xFF;
                break;
            case 7:
                result[k] = (data[i] & 0xFF) << 48 | (data[i + 1] & 0xFF) << 40 | (data[i + 2] & 0xFF) << 32 | (data[i + 3] & 0xFF) << 24
                        | (data[i + 4] & 0xFF) << 16 | (data[i + 5] & 0xFF) << 8 | data[i + 6] & 0xFF;
                break;
            case 8:
                result[k] = (data[i] & 0xFF) << 56 | (data[i + 1] & 0xFF) << 48 | (data[i + 2] & 0xFF) << 40 | (data[i + 3] & 0xFF) << 32
                        | (data[i + 4] & 0xFF) << 24 | (data[i + 5] & 0xFF) << 16 | (data[i + 6] & 0xFF) << 8 | data[i + 7] & 0xFF;
                break;
            }
        }
        return result;
    }
    /*
    static public long getLongValue(byte[] data, int offset) {
        return ((long) (data[offset] & 0xff) << 56) | ((long) (data[offset + 1] & 0xff) << 48) | ((long) (data[offset + 2] & 0xff) << 40)
                | ((long) (data[offset + 3] & 0xff) << 32) | ((long) (data[offset + 4] & 0xff) << 24) | ((long) (data[offset + 5] & 0xff) << 16)
                | ((long) (data[offset + 6] & 0xff) << 8) | ((long) (data[offset + 7] & 0xff));
    }
    */
    static public int getIntValue(byte[] data, int offset) {
        return ((data[offset] & 0xff) << 24) | ((data[offset + 1] & 0xff) << 16) | ((data[offset + 2] & 0xff) << 8) | (data[offset + 3] & 0xff);
    }

    static public char getCharValue(byte[] data, int offset) {
        return (char) (((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff));
    }
    /*
    static public long getLongValue(byte[] data) {
        return getLongValue(data, 0);
    }

    static public int getIntValue(byte[] data) {
        return getIntValue(data, 0);
    }

    static public char getCharValue(byte[] data) {
        return getCharValue(data, 0);
    }*/
}

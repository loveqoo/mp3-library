package qoo.lib.util;

import java.util.Random;

public class Strings {

    public static final String EMPTY_STRING = "";
    public static final String SLASH = "/";

    static public boolean isEmpty(String text) {
        return text == null || EMPTY_STRING.equals(text);
    }

    static public String getFirst(String text) {
        if (isEmpty(text))
            return EMPTY_STRING;
        return text.substring(0, 1);
    }

    static public String getLast(String text) {
        if (isEmpty(text))
            return EMPTY_STRING;
        return text.substring(text.length() - 1, text.length());
    }

    static public String removeFirst(String text) {
        return text.substring(1, text.length());
    }

    static public String removeLast(String text) {
        return text.substring(0, text.length() - 1);
    }

    static public String join(String[] text) {
        if (text == null || text.length == 0)
            return EMPTY_STRING;
        StringBuffer sb = new StringBuffer();
        for (String s : text)
            sb.append(s);
        return sb.toString();
    }

    static public String join(long[] text) {
        if (text == null || text.length == 0)
            return EMPTY_STRING;
        StringBuffer sb = new StringBuffer();
        for (long s : text)
            sb.append(s);
        return sb.toString();
    }

    static public String join(long[] text, String delimiter) {
        if (text == null || text.length == 0)
            return EMPTY_STRING;
        StringBuffer sb = new StringBuffer();
        int idx = 1;
        for (long s : text) {
            sb.append(s);
            if (idx < text.length)
                sb.append(delimiter);
            idx++;
        }
        return sb.toString();
    }

    static public String join(String[] text, String delimiter) {
        if (text == null || text.length == 0)
            return EMPTY_STRING;
        StringBuffer sb = new StringBuffer();
        int idx = 1;
        for (String s : text) {
            sb.append(s);
            if (idx < text.length)
                sb.append(delimiter);
            idx++;
        }
        return sb.toString();
    }

    static public String[] dimidiate(String source, String regex, String default0, String default1) {
        if (isEmpty(source)) {
            return new String[] { default0, default1 };
        }
        String[] parsed = source.split(regex);
        switch (parsed.length) {
        case 0:
            return new String[] { default0, default1 };
        case 1:
            return new String[] { parsed[0], default1 };
        case 2:
            if (isEmpty(parsed[0])) {
                return new String[] { default0, parsed[1] };
            } else {
                return new String[] { parsed[0], parsed[1] };
            }
        default:
            return new String[] { parsed[0], parsed[1] };
        }
    }

    static public String trim(String text, char trimChar) {
        if (isEmpty(text))
            return EMPTY_STRING;
        char[] val = text.toCharArray();
        int count = val.length;
        int len = val.length;
        int st = 0;
        int off = 0;

        while ((st < len) && (val[off + st] <= trimChar)) {
            st++;
        }
        while ((st < len) && (val[off + len - 1] <= trimChar)) {
            len--;
        }
        return ((st > 0) || (len < count)) ? text.substring(st, len) : text;
    }

    static public String generateRandomString(int length) {
        if (length <= 0)
            return EMPTY_STRING;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String chars[] = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");
        for (int i = 0; i < length; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        sb.append(System.currentTimeMillis());
        return sb.toString();
    }
}

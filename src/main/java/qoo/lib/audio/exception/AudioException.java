package qoo.lib.audio.exception;

public class AudioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AudioException() {
        super();
    }

    public AudioException(String msg) {
        super(msg);
    }

    public AudioException(Throwable cause) {
        super(cause);
    }

    public AudioException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

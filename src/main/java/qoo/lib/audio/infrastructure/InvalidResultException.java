package qoo.lib.audio.infrastructure;

public class InvalidResultException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidResultException() {
        super();
    }

    public InvalidResultException(String msg) {
        super(msg);
    }

    public InvalidResultException(Throwable cause) {
        super(cause);
    }

    public InvalidResultException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

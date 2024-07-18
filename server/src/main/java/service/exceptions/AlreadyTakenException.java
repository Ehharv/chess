package service.exceptions;

public class AlreadyTakenException extends Exception {
    /**
     * 403
     *
     * @param message
     */
    public AlreadyTakenException(String message) {
        super(message);
    }
}
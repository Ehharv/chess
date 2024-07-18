package service.exceptions;

public class BadRequestException extends Exception{
    /**
     * 400
     *
     * @param message
     */
    public BadRequestException(String message) {
        super(message);
    }
}

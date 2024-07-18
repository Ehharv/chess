package service.exceptions;

public class UnauthorizedException extends Exception{
    /**
     * 401
     *
     * @param message
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}

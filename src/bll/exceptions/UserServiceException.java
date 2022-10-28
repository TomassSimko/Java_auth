package bll.exceptions;

public class UserServiceException extends Throwable{
    private final String message;

    public UserServiceException(String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}

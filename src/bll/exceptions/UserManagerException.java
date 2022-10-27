package bll.exceptions;

public class UserManagerException extends Throwable {
    private final String message;

    public UserManagerException(String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}

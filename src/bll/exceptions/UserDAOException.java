package bll.exceptions;

public class UserDAOException extends Throwable{
    private final String message;

    public UserDAOException(String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}

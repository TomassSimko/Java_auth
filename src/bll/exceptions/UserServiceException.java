package bll.exceptions;

// TODO : DO NOT EXTEND TRHOWABLES OR ERRORS !!
// - NOT ERROR because they are separated from exceptions
// - NOT THROWABLES due to java convension


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

package bll.exceptions;

public class UserManagerException extends Exception {
    public UserManagerException(String message,Throwable cause){
        super(message,cause);
    }
    public UserManagerException(String message){
        super(message);
    }
}

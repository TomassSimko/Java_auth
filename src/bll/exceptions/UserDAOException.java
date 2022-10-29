package bll.exceptions;


import java.sql.SQLException;

// TODO : DO NOT EXTEND TRHOWABLES OR ERRORS !!
// - NOT ERROR because they are separated from exceptions
// - NOT THROWABLES due to java convension
// - BETTER to use some similiar exceptions
public class UserDAOException extends SQLException {
   // private final String message;

    public UserDAOException(String message,Throwable cause){
        super(message,cause);
    }

    public UserDAOException(String message){
        super(message);
    }


//    public UserDAOException(String message, Exception e) {
//        this.message = message;
//        e.printStackTrace();
//    }

//    @Override
//    public String getMessage() {
//        return message;
//    }
}

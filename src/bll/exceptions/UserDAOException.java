package bll.exceptions;

import java.sql.SQLException;

// TODO : DO NOT EXTEND TRHOWABLES OR ERRORS !!
// - NOT ERROR because they are separated from exceptions
// - NOT THROWABLES due to java convension
// - BETTER to use some similiar exceptions
public class UserDAOException extends SQLException {
    public UserDAOException(String message,Throwable cause){
        super(message,cause);
    }
}

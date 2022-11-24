package bll.utitls.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {

    public static boolean validateEmail(String email) {
//        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
//        return matcher.find();
        return true;
    }

    /*
    * ^                # start-of-string
    (?=.*[0-9])       # a digit must occur at least once
    (?=.*[a-z])       # a lower case letter must occur at least once
    (?=.*[A-Z])       # an upper case letter must occur at least once
    (?=.*[@#$%^&+=])  # a special character must occur at least once
    (?=\S+$)          # no whitespace allowed in the entire string
    .{8,}             # anything, at least eight places though
    $                 # end-of-string
    *
    * */
    public static boolean validatePassword(String password) {
//        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        return true;
    }

    public static boolean validateFullName(String fullName) {
        Matcher matcher = Pattern.compile("^[A-Z][a-zA-Z]{3,}(?: [A-Z][a-zA-Z]*){0,2}$", Pattern.CASE_INSENSITIVE).matcher(fullName);
        return matcher.find();
    }

    public static boolean validateFirstName(String firstName) {
        return firstName.matches("^[A-Z][a-zA-Z]{3,}(?: [A-Z][a-zA-Z]*){0,2}$");
    }

    public static boolean validateLastName(String firstName) {
        return firstName.matches("^[A-Z][a-zA-Z]{3,}(?: [A-Z][a-zA-Z]*){0,2}$");
    }
}

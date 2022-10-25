package bll.utitls.validations;

import javafx.scene.control.Alert;

public class NotificationHelper {
    public static void displayAlert(ValidationErrorType message,Alert.AlertType type){
        Alert a = new Alert(type);
        a.setContentText(message.toString().toLowerCase());
        a.show();
    }
}

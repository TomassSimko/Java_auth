package bll.utitls;

import bll.utitls.validations.ValidationErrorType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class NotificationHelper {
    public static void displayAlert(ValidationErrorType message, Alert.AlertType type){
        Alert a = new Alert(type);
        a.setContentText(message.toString().toLowerCase());
        a.show();
    }

    public static Optional<ButtonType> showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }

}

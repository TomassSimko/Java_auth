package auth.base.auth.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label lbl;

    @FXML
    private void clickBtn(ActionEvent actionEvent) {
        lbl.setText("Button");
    }
}

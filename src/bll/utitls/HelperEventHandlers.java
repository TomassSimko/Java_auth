package bll.utitls;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HelperEventHandlers {
    private double xOffset = 0;
    private double yOffset = 0;

    public HelperEventHandlers(){
    }
    public void useEventHandlers(Parent parent, Stage stage){
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }
}

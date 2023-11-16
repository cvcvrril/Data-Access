package ui.Screens;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import common.Constantes;
import ui.Screens.common.BaseScreenController;

public class WelcomeController extends BaseScreenController {

    @FXML
    private Label bienvenido;

    @Override
    public void principalCargado() {
        bienvenido.setText(Constantes.Welcome + getPrincipalController().getActualUser().getUsername());
    }
}

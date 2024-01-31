package ui.pantallas.welcome;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.login.LoginController;
import ui.pantallas.principal.PrincipalController;

public class WelcomeController extends BasePantallaController {


    @FXML
    private Label welcomeText;

    @Override
    public void principalCargado() {
        welcomeText.setText("Welcome " + this.getPrincipalController().getActualCredential().getUserName() + "!");
    }
}

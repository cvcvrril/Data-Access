package ui.pantallas.login;

import common.Constantes;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Credential;
import services.ServiceLogin;
import ui.pantallas.common.BasePantallaController;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginController extends BasePantallaController {

    @FXML
    private TextField userText;
    @FXML
    private TextField passwdText;
    private final ServiceLogin serviceLogin;

    @Inject
    public LoginController(ServiceLogin serviceLogin) {
        this.serviceLogin = serviceLogin;
    }

    @FXML
    private void Login() {
        String username = userText.getText();

        Credential credential = serviceLogin.getCredentialByUsername(username);
        if (credential != null) {
            if (serviceLogin.doLogin(credential)) {
                getPrincipalController().onLogin(credential.getId());
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(Constantes.USUARIO_O_CONTRASENA_INCORRECTOS);
                a.show();
            }
        }else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("No existe el usuario");
            a.show();
        }
    }

    @Override
    public void principalCargado() {

    }
}

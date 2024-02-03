package ui.pantallas.login;

import common.Constantes;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Credential;
import services.ServiceLogin;
import services.mongo.ServiceHibernateToMongo;
import ui.pantallas.common.BasePantallaController;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginController extends BasePantallaController {

    @FXML
    private TextField userText;
    @FXML
    private TextField passwdText;
    private final ServiceLogin serviceLogin;
    private final ServiceHibernateToMongo serviceHibernateToMongo;

    @Inject
    public LoginController(ServiceLogin serviceLogin, ServiceHibernateToMongo serviceHibernateToMongo) {
        this.serviceLogin = serviceLogin;
        this.serviceHibernateToMongo = serviceHibernateToMongo;
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
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("No existe el usuario");
            a.show();
        }
    }

    @FXML
    private void exportData() {
        try {
            if (serviceHibernateToMongo.transferCredentialToMongo().isRight()) {
                getPrincipalController().sacarAlertConf("Se ha exportado de forma correcta la información a Mongo");
            } else {
                getPrincipalController().sacarAlertError("Hubo un problema");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void principalCargado() {

    }
}

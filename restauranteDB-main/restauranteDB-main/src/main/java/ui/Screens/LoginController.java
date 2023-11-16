package ui.Screens;

import dao.implJDBC.DBConnection;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Data;
import model.Credentials;
import services.LoginService;
import ui.Screens.common.BaseScreenController;

@Data
public class LoginController extends BaseScreenController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    private Credentials cred;
    private LoginService loginService;
    private final DBConnection db;
    @Inject
    public LoginController(DBConnection db) {
        this.db = db;
        this.loginService = new LoginService(this.db);
    }

    public boolean isLoginOK(){
        cred=loginService.getAll().getOrNull().stream().filter(credentials -> credentials.getUsername().equalsIgnoreCase(username.getText())).findFirst().orElse(null);
        return username.getText().equals(cred.getUsername()) && password.getText().equals(cred.getPassword());
    }
    public void check() {

            if (isLoginOK())
            {
                this.getPrincipalController().onLoginHecho(cred.getId());

            }
            else {
                this.getPrincipalController().sacarAlertError("Invalid credentials");
            }
    }
    public void doLogin() {
        check();
    }

    @Override
    public void principalCargado() {

    }
}

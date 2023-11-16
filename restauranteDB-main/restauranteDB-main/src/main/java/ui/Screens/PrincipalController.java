package ui.Screens;

import common.Configuration;
import dao.implJDBC.DBConnection;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import model.Credentials;
import services.LoginService;
import ui.Screens.common.BaseScreenController;
import ui.Screens.common.Screens;
import java.io.IOException;
import java.util.Optional;

@Log4j2
public class PrincipalController {

    public Menu menuFile;
    public MenuItem menuItemLogout;
    public MenuItem menuItemExit;
    public Menu menuEdit;
    public MenuItem menuCustList;
    public MenuItem menuCustAdd;
    public MenuItem menuCustUpdate;
    public MenuItem menuCustDelete;
    public Menu menuHelp;
    public MenuItem menuOrdList;
    public MenuItem menuOrdAdd;
    public MenuItem menuOrdUpdate;
    public MenuItem menuOrdDelete;
    @FXML
    private MenuBar menuPrincipal;
    Instance<Object> instance;
    private Stage primaryStage;
    @Getter
    private Credentials actualUser;
    @FXML
    public BorderPane root;
    private final Alert alert;
    private LoginService loginService;

    private final DBConnection db;
    @Inject
    public PrincipalController(Instance<Object> instance, DBConnection db) {
        this.instance = instance;
        this.db = db;
        alert= new Alert(Alert.AlertType.NONE);

    }
    public void initialize() {
        menuPrincipal.setVisible(false);
        cargarPantalla(Screens.LOGIN);
    }
    public void closeWindowEvent(WindowEvent windowEvent){
        db.closePool();
    }
    private void cargarPantalla(Screens pantalla) {
        cambioPantalla(cargarPantalla(pantalla.getRuta()));
    }
    public void sacarAlertError(String mensaje) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.getDialogPane().setId("alert");
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("btn-ok");
        alert.showAndWait();
    }
    public void sacarAlertInfo(String mensaje) {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.isResizable();
        alert.getDialogPane().setId("alert");
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("btn-ok");
        alert.showAndWait();
    }
    public boolean sacarAlertConf(String mensaje) {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setContentText(mensaje);
        alert.isResizable();
        alert.getDialogPane().setId("alert");
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("btn-ok");
        alert.getDialogPane().lookupButton(ButtonType.CANCEL).setId("btn-cancel");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    private Pane cargarPantalla(String ruta) {
        Pane panePantalla = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(ruta));
            BaseScreenController pantallaController = fxmlLoader.getController();
            pantallaController.setPrincipalController(this);
            pantallaController.principalCargado();


        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return panePantalla;
    }
    private void cambioPantalla(Pane pantallaNueva) {
        root.setCenter(pantallaNueva);
    }
    public void exit(ActionEvent actionEvent) {
        primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST,this::closeWindowEvent);
    }
    public void logout() {
        actualUser = null;
        menuPrincipal.setVisible(false);
        cargarPantalla(Screens.LOGIN);
    }
    public void onLoginHecho(int id) {
        this.loginService=new LoginService(db);
        loginService.getAll();
        this.actualUser=loginService.get(id).getOrNull();
        if (actualUser.getId()>0){
            menuEdit.setVisible(false);
            menuOrdDelete.setVisible(false);
            menuOrdAdd.setVisible(true);
        }else{
            menuOrdAdd.setVisible(false);
            menuEdit.setVisible(true);
            menuOrdDelete.setVisible(true);
        }
        menuPrincipal.setVisible(true);
        cargarPantalla(Screens.WELCOME);
    }
    public void setStage(Stage stage) {
        primaryStage = stage;
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST,this::closeWindowEvent);
    }

    @FXML
    private void menuClick(ActionEvent actionEvent) {

        switch (((MenuItem)actionEvent.getSource()).getId())
        {
            case "menuCustList":
                cargarPantalla(Screens.CUSTOMERLIST);
                break;
            case "menuCustAdd":
                cargarPantalla(Screens.CUSTOMERADD);
                break;
            case "menuCustUpdate":
                cargarPantalla(Screens.CUSTOMERUPDATE);
                break;
            case "menuCustDelete":
                cargarPantalla(Screens.CUSTOMERDELETE);
                break;
            case "menuOrdList":
                cargarPantalla(Screens.ORDERLIST);
                break;
            case "menuOrdAdd":
                cargarPantalla(Screens.ORDERADD);
                break;
            case "menuOrdUpdate":
                cargarPantalla(Screens.ORDERUPDATE);
                break;
            case "menuOrdDelete":
                cargarPantalla(Screens.ORDERDELETE);
                break;
        }

    }
}
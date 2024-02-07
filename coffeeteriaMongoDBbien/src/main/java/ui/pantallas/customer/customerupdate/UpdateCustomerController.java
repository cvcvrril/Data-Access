package ui.pantallas.customer.customerupdate;

import common.Constantes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Credential;
import model.Customer;
import model.errors.ErrorCCustomer;
import model.errors.ErrorCObject;
import model.mongo.CredentialMongo;
import model.mongo.CustomerMongo;
import services.ServiceCustomer;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;
import java.util.ArrayList;

public class UpdateCustomerController extends BasePantallaController {
    private final ServiceCustomer serviceCustomer;


    @FXML
    private TextField firstNameField;
    @FXML
    private TextField secondNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker dateFieldd;

    @FXML
    private TableView<CustomerMongo> tableCustomers;
    @FXML
    private TableColumn<CustomerMongo, Integer> idC;
    @FXML
    private TableColumn<CustomerMongo, String> firstName;
    @FXML
    private TableColumn<CustomerMongo, String> secondName;
    @FXML
    private TableColumn<CustomerMongo, String> email;
    @FXML
    private TableColumn<CustomerMongo, Integer> phoneNumber;
    @FXML
    private TableColumn<CustomerMongo, LocalDate> date;
    @FXML
    private Button resetCustomerButton;
    @FXML
    private Button updateCustomerButton;

    @Inject
    public UpdateCustomerController(ServiceCustomer serviceCustomer) {
        this.serviceCustomer = serviceCustomer;
    }

    public void updateCustomer() {

        CustomerMongo selCustomer = tableCustomers.getSelectionModel().getSelectedItem();
        String firstNameCus = firstNameField.getText();
        String secondNameCus = secondNameField.getText();
        String emailCus = emailField.getText();
        int phoneNumberCus = Integer.parseInt(phoneField.getText());
        LocalDate dateText = dateFieldd.getValue();

        CredentialMongo credential = new CredentialMongo(null, getPrincipalController().getUser(), getPrincipalController().getPassword());
        CustomerMongo updatedCustomer = new CustomerMongo(null, firstNameCus, secondNameCus, emailCus, phoneNumberCus, dateText, new ArrayList<>());

        Either<ErrorCObject, Integer> res = serviceCustomer.update(updatedCustomer);
        if (res.isRight()) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText(Constantes.CUSTOMER_UPDATED);
            a.show();
            resetFields();
            tableCustomers.getItems().setAll(serviceCustomer.getAll().getOrNull());
        } else {
            ErrorCObject error = res.getLeft();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Error al actualizar el cliente");
            errorAlert.show();
        }
    }

    public void resetCustomer() {
        resetFields();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText(Constantes.SUCCESFULLY_RESET);
        a.show();
    }

    private void resetFields() {
        firstNameField.clear();
        secondNameField.clear();
        emailField.clear();
        phoneField.clear();
    }

    @Override
    public void principalCargado() {

        idC.setCellValueFactory(new PropertyValueFactory<>("_id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        secondName.setCellValueFactory(new PropertyValueFactory<>("second_name"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        date.setCellValueFactory(new PropertyValueFactory<>("date_of_birth"));
        tableCustomers.getItems().addAll(serviceCustomer.getAll().getOrNull());
        tableCustomers.setOnMouseClicked(this::handleTable);
    }

    private void handleTable(MouseEvent event){
        if (event.getClickCount() == 1){
            CustomerMongo selCustomer = tableCustomers.getSelectionModel().getSelectedItem();
            if (selCustomer != null) {
                firstNameField.setText(String.valueOf(selCustomer.getFirst_name()));
                secondNameField.setText(String.valueOf(selCustomer.getSecond_name()));
                emailField.setText(String.valueOf(selCustomer.getEmail()));
                phoneField.setText(String.valueOf(selCustomer.getPhone()));
                dateFieldd.setValue(selCustomer.getDate_of_birth());
            }
        }
    }

 {

    }
}

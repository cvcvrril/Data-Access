package ui.pantallas.customer.customerlist;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.mongo.CustomerMongo;
import services.ServiceCustomer;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;

public class CustomerListController extends BasePantallaController {

    private final ServiceCustomer serviceCustomer;

    @FXML
    private TableView<CustomerMongo> tableCustomers;
    @FXML
    private TableColumn<CustomerMongo, Integer> idC;
    @FXML
    private TableColumn<CustomerMongo, String> firstName;
    @FXML
    private TableColumn<CustomerMongo, String> secondName;
    @FXML
    private TableColumn<CustomerMongo,Integer> phoneNumber;
    @FXML
    private TableColumn<CustomerMongo,String> email;
    @FXML
    private TableColumn<CustomerMongo, LocalDate> date;


    /*Constructores*/

    @Inject
    public CustomerListController(ServiceCustomer serviceCustomer) {
        this.serviceCustomer = serviceCustomer;
    }

    /*Métodos*/

    @Override
    public void principalCargado() {

        idC.setCellValueFactory(new PropertyValueFactory<>("_id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        secondName.setCellValueFactory(new PropertyValueFactory<>("second_name"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        date.setCellValueFactory(new PropertyValueFactory<>("date_of_birth"));
        tableCustomers.getItems().addAll(serviceCustomer.getAll().getOrNull());

    }
}

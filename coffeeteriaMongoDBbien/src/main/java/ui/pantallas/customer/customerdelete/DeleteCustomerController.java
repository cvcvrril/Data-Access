package ui.pantallas.customer.customerdelete;

import common.Constantes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Order;
import model.errors.ErrorCObject;
import model.mongo.CustomerMongo;
import services.ServiceCustomer;
import services.ServiceOrder;
import services.ServiceOrderItem;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DeleteCustomerController extends BasePantallaController {

    private final ServiceCustomer serviceCustomer;
    private final ServiceOrder serviceOrder;
    private final ServiceOrderItem serviceOrderItem;

    @FXML
    private Button delCustomer;

    @FXML
    private  TableView<Order> tableOrdersCus;
    @FXML
    private TableColumn<Order, Integer> idOrd;
    @FXML
    private TableColumn<Order,Integer> idC2;
    @FXML
    private TableColumn<Order,Integer> idTable;
    @FXML
    private TableColumn<Order, LocalDate> dateOrder;

    @FXML
    private TableView<CustomerMongo> tableCustomers;
    @FXML
    private TableColumn<CustomerMongo, Integer> idC;
    @FXML
    private TableColumn<CustomerMongo, String> firstName;
    @FXML
    private TableColumn<CustomerMongo, String> secondName;
    @FXML
    private TableColumn<CustomerMongo,String> email;
    @FXML
    private TableColumn<CustomerMongo,Integer> phoneNumber;
    @FXML
    private TableColumn<CustomerMongo,LocalDate> date;

    @Inject
    public DeleteCustomerController(ServiceCustomer serviceCustomer, ServiceOrder serviceOrder, ServiceOrderItem serviceOrderItem) {
        this.serviceCustomer = serviceCustomer;
        this.serviceOrder = serviceOrder;
        this.serviceOrderItem = serviceOrderItem;
    }

    public void delCustomer() {
        boolean conf = false;
        CustomerMongo selCustomer = tableCustomers.getSelectionModel().getSelectedItem();
        if (selCustomer != null) {
            List<Order> customerOrders = serviceOrder.getOrdersByCustomer(0);
            for (Order order:customerOrders) {
                order.setOrderItems(serviceOrderItem.get(order.getIdOrd()).get());
            }
            if (!customerOrders.isEmpty()) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setContentText("This customer has associated orders. Do you want to delete it anyway?");
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                    conf = false;
                } else {
                    serviceOrder.save(serviceOrder.getOrdersByCustomer(0));
                    conf = true;
                }
            }
            Either<ErrorCObject, Integer> res = serviceCustomer.deleteCustomer(selCustomer, conf);
            if (res.isRight()) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText(Constantes.USER_DELETED);
                a.show();
            } else {
                ErrorCObject error = res.getLeft();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("There was an error when deleting the customer");
                errorAlert.show();
            }
            tableCustomers.getItems().remove(selCustomer);
        }
    }

    public void setTableOrdersCus(MouseEvent event){
        tableOrdersCus.getItems().clear();
        idC2.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_CO));
        idOrd.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_ORD));
        idTable.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_TABLE));
        dateOrder.setCellValueFactory(new PropertyValueFactory<>(Constantes.OR_DATE));
        tableOrdersCus.getItems().addAll(serviceOrder.getOrdersByCustomer(0));
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
        tableCustomers.setOnMouseClicked(this::setTableOrdersCus);

    }
}

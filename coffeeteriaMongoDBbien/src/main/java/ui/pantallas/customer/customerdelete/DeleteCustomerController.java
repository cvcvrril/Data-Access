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
import model.mongo.OrderMongo;
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
    private  TableView<OrderMongo> tableOrdersCus;
    @FXML
    private TableColumn<OrderMongo,Integer> idTable;
    @FXML
    private TableColumn<OrderMongo, LocalDate> dateOrder;

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
            List<OrderMongo> customerOrders = selCustomer.getOrders();
            for (OrderMongo order:customerOrders) {
                order.getOrder_items();
            }
            if (!customerOrders.isEmpty()) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setContentText("This customer has associated orders. Do you want to delete it anyway?");
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                    conf = false;
                } else {
                    //serviceOrder.save(serviceOrder.getOrdersByCustomer(0));
                    conf = true;
                }
            }
            Either<ErrorCObject, Integer> res = serviceCustomer.deleteCustomer(selCustomer, conf);
            if (res.isRight()) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText(Constantes.USER_DELETED);
                a.show();
            } else {
                getPrincipalController().sacarAlertError("There was an error when deleting the customer");
            }
            tableCustomers.getItems().remove(selCustomer);
        }
    }

    public void setTableOrdersCus(MouseEvent event){
        CustomerMongo customerMongo = tableCustomers.getSelectionModel().getSelectedItem();
        tableOrdersCus.getItems().clear();
        idTable.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        dateOrder.setCellValueFactory(new PropertyValueFactory<>("order_date"));
        tableOrdersCus.getItems().addAll(customerMongo.getOrders());
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

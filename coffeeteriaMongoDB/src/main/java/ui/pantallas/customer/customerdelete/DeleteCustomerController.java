package ui.pantallas.customer.customerdelete;

import common.Constantes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Customer;
import model.Order;
import model.errors.ErrorCCustomer;
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
    private TableView<Customer> tableCustomers;
    @FXML
    private TableColumn<Customer, Integer> idC;
    @FXML
    private TableColumn<Customer, String> firstName;
    @FXML
    private TableColumn<Customer, String> secondName;
    @FXML
    private TableColumn<Customer,String> email;
    @FXML
    private TableColumn<Customer,Integer> phoneNumber;
    @FXML
    private TableColumn<Customer,LocalDate> date;



    @Inject

    public DeleteCustomerController(ServiceCustomer serviceCustomer, ServiceOrder serviceOrder, ServiceOrderItem serviceOrderItem) {
        this.serviceCustomer = serviceCustomer;
        this.serviceOrder = serviceOrder;
        this.serviceOrderItem = serviceOrderItem;
    }

    public void delCustomer() {
        boolean conf = false;
        Customer selCustomer = tableCustomers.getSelectionModel().getSelectedItem();
        if (selCustomer != null) {
            List<Order> customerOrders = serviceOrder.getOrdersByCustomer(selCustomer.getIdC());
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
                    serviceOrder.save(serviceOrder.getOrdersByCustomer(selCustomer.getIdC()));
                    conf = true;
                }
            }

            Either<ErrorCCustomer, Integer> res = serviceCustomer.delete(selCustomer.getIdC(), conf);
            if (res.isRight()) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText(Constantes.USER_DELETED);
                a.show();
            } else {
                ErrorCCustomer error = res.getLeft();
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
        tableOrdersCus.getItems().addAll(serviceOrder.getOrdersByCustomer(tableCustomers.getSelectionModel().getSelectedItem().getIdC()));
    }

    @Override
    public void principalCargado() {
        idC.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_C));
        firstName.setCellValueFactory(new PropertyValueFactory<>(Constantes.FIRST_NAME));
        secondName.setCellValueFactory(new PropertyValueFactory<>(Constantes.SECOND_NAME));
        email.setCellValueFactory(new PropertyValueFactory<>(Constantes.EMAIL));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>(Constantes.PHONE_NUMBER));
        date.setCellValueFactory(new PropertyValueFactory<>(Constantes.DATE));
        tableCustomers.getItems().addAll(serviceCustomer.getAll().getOrNull());
        tableCustomers.setOnMouseClicked(this::setTableOrdersCus);

    }
}

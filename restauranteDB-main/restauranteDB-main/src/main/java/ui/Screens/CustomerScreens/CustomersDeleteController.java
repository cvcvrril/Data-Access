package ui.Screens.CustomerScreens;

import common.Constantes;
import dao.implJDBC.DBConnection;
import jakarta.inject.Inject;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Order;
import services.CustomerService;
import services.OrderService;
import ui.Screens.common.BaseScreenController;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CustomersDeleteController extends BaseScreenController {
    public TableView<Customer> CustDel0;
    public TableColumn<Customer, Integer> CustDel1;
    public TableColumn<Customer, String> CustDel2;
    public TableColumn<Customer, String> CustDel3;
    public TableColumn<Customer, LocalDate> CustDel4;
    public TableColumn<Customer, String> CustDel5;
    public TableColumn<Customer, Integer> CustDel6;
    public TableView<Order> CustDel7;
    public TableColumn<Order, Integer> CustDel8;
    public TableColumn<Order, LocalDateTime> CustDel9;
    public TableColumn<Order, Integer> CustDel10;
    public TableColumn<Order, Integer> CustDel11;
    public Button CustDel12;

    public CustomerService customerService;
    public OrderService orderService;
    private final DBConnection db;
    @Inject
    public CustomersDeleteController(DBConnection db) {
        this.db = db;
        this.orderService = new OrderService(this.db);
        this.customerService = new CustomerService(this.db);
    }

    public void delete() {
        boolean delete=this.getPrincipalController().sacarAlertConf(Constantes.UserDEL + CustDel0.getSelectionModel().getSelectedItem().toString());
        if (delete) {
            CustDel7.getItems().forEach(order -> orderService.delete(order, true));
            CustDel7.getItems().clear();
            customerService.delete(CustDel0.getSelectionModel().getSelectedItem(), true);
            CustDel0.getItems().remove(CustDel0.getSelectionModel().getSelectedItem());
            this.getPrincipalController().sacarAlertInfo("Customer and orders deleted");
        }else{
            this.getPrincipalController().sacarAlertInfo("Nothig deleted");
            CustDel7.getItems().forEach(order -> orderService.delete(order, false));
            customerService.delete(CustDel0.getSelectionModel().getSelectedItem(), false);
        }
        CustDel0.getItems().clear();
        principalCargado();
    }
    public void customerOrder() {
        CustDel7.getItems().clear();
        CustDel8.setCellValueFactory(new PropertyValueFactory<>("id"));
        CustDel9.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        CustDel10.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        CustDel11.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        CustDel7.getItems().addAll(orderService.getCustomerID(CustDel0.getSelectionModel().getSelectedItem().getId()));
    }
    @Override
    public void principalCargado() {
        CustDel1.setCellValueFactory(new PropertyValueFactory<>("id"));
        CustDel2.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        CustDel3.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        CustDel4.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        CustDel5.setCellValueFactory(new PropertyValueFactory<>("email"));
        CustDel6.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        CustDel0.getItems().addAll(customerService.getAll().getOrNull());
    }
}

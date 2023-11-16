package ui.Screens.CustomerScreens;

import dao.implJDBC.DBConnection;
import jakarta.inject.Inject;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import services.CustomerService;
import ui.Screens.common.BaseScreenController;
import java.time.LocalDate;

public class CustomersListController extends BaseScreenController {
    private final DBConnection db;
    public TableView<Customer> CustList0;
    public TableColumn<Customer,Integer> CustList1;
    public TableColumn<Customer,String> CustList2;
    public TableColumn<Customer,String> CustList3;
    public TableColumn<Customer, LocalDate> CustList4;
    public TableColumn<Customer,String> CustList5;
    public TableColumn<Customer,Integer> CustList6;

    public CustomerService customerService;
    @Inject
    public CustomersListController(DBConnection db) {
        this.db = db;
        this.customerService =new CustomerService(this.db);
    }
    @Override
    public void principalCargado(){
        CustList1.setCellValueFactory(new PropertyValueFactory<>("id"));
        CustList2.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        CustList3.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        CustList4.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        CustList5.setCellValueFactory(new PropertyValueFactory<>("email"));
        CustList6.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        CustList0.getItems().addAll(customerService.getAll().getOrNull());
    }
}
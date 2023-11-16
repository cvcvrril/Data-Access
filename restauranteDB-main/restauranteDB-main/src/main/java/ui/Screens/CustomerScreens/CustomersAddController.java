package ui.Screens.CustomerScreens;

import common.Constantes;
import dao.implJDBC.DBConnection;
import jakarta.inject.Inject;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import services.CustomerService;
import ui.Screens.common.BaseScreenController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomersAddController extends BaseScreenController {
    private final DBConnection db;
    public TableView<Customer> CustAdd0;
    public TableColumn<Customer,Integer> CustAdd1;
    public TableColumn<Customer,String> CustAdd2;
    public TableColumn<Customer,String> CustAdd3;
    public TableColumn<Customer,LocalDate> CustAdd4;
    public TableColumn<Customer,String> CustAdd5;
    public TableColumn<Customer,Integer> CustAdd6;
    public TextField CustAdd7;
    public TextField CustAdd8;
    public TextField CustAdd9;
    public TextField CustAdd10;
    public TextField CustAdd11;
    public TextField CustAdd12;
    public Button CustAdd13;

    public CustomerService customerService;
    @Inject
    public CustomersAddController(DBConnection db) {
        this.db = db;
        this.customerService =new CustomerService(this.db);
    }

    public void add(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Customer cust=new Customer(Integer.parseInt(CustAdd12.getText()),CustAdd7.getText(),CustAdd8.getText(),LocalDate.parse(CustAdd9.getText(),formatter),CustAdd10.getText(),Integer.parseInt(CustAdd11.getText()));
        customerService.save(cust);
        this.getPrincipalController().sacarAlertInfo(Constantes.UserADD + cust.toString());
        CustAdd12.setText("");
        CustAdd7.setText("");
        CustAdd8.setText("");
        CustAdd9.setText("");
        CustAdd10.setText("");
        CustAdd11.setText("");
        CustAdd0.getItems().clear();
        principalCargado();
    }

    @Override
    public void principalCargado() {
        CustAdd1.setCellValueFactory(new PropertyValueFactory<>("id"));
        CustAdd2.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        CustAdd3.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        CustAdd4.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        CustAdd5.setCellValueFactory(new PropertyValueFactory<>("email"));
        CustAdd6.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        CustAdd0.getItems().addAll(customerService.getAll().getOrNull());
    }
}

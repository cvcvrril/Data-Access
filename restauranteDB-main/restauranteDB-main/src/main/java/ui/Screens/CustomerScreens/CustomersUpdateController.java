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

public class CustomersUpdateController extends BaseScreenController {
    private final DBConnection db;
    public TableView<Customer> CustUpd0;
    public TableColumn<Customer, Integer> CustUpd1;
    public TableColumn<Customer, String> CustUpd2;
    public TableColumn<Customer, String> CustUpd3;
    public TableColumn<Customer, LocalDate> CustUpd4;
    public TableColumn<Customer, String> CustUpd5;
    public TableColumn<Customer, Integer> CustUpd6;
    public TextField CustUpd7;
    public TextField CustUpd8;
    public TextField CustUpd9;
    public TextField CustUpd10;
    public TextField CustUpd11;
    public Button CustUpd12;

    public CustomerService customerService;

    @Inject
    public CustomersUpdateController(DBConnection db) {
        this.db = db;
        this.customerService = new CustomerService(this.db);
    }

    public void update() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Customer selectedCustomer = CustUpd0.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            selectedCustomer.setFirstName(CustUpd7.getText());
            selectedCustomer.setLastName(CustUpd8.getText());
            selectedCustomer.setDateOfBirth(LocalDate.parse(CustUpd9.getText().replace("-", "/"), formatter));
            selectedCustomer.setEmail(CustUpd10.getText());
            selectedCustomer.setPhoneNumber(Integer.parseInt(CustUpd11.getText()));
            customerService.update(selectedCustomer);
            this.getPrincipalController().sacarAlertInfo(Constantes.UserUPD + CustUpd0.getSelectionModel().getSelectedItem().toString());
            CustUpd0.getItems().clear();
            principalCargado();
        }
    }
        @Override
        public void principalCargado () {
            CustUpd1.setCellValueFactory(new PropertyValueFactory<>("id"));
            CustUpd2.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            CustUpd3.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            CustUpd4.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
            CustUpd5.setCellValueFactory(new PropertyValueFactory<>("email"));
            CustUpd6.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            CustUpd0.getItems().addAll((customerService.getAll().getOrNull()));

            CustUpd0.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    CustUpd7.setText(CustUpd0.getSelectionModel().getSelectedItem().getFirstName());
                    CustUpd8.setText(CustUpd0.getSelectionModel().getSelectedItem().getLastName());
                    CustUpd9.setText(CustUpd0.getSelectionModel().getSelectedItem().getDateOfBirth().toString());
                    CustUpd10.setText(CustUpd0.getSelectionModel().getSelectedItem().getEmail());
                    CustUpd11.setText(String.valueOf(CustUpd0.getSelectionModel().getSelectedItem().getPhoneNumber()));
                }
            });

        }
    }

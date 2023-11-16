package ui.Screens.OrderScreens;

import dao.implJDBC.DBConnection;
import jakarta.inject.Inject;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import model.OrderItem;
import services.CustomerService;
import services.MenuItemServices;
import services.OrderItemServices;
import services.OrderService;
import ui.Screens.common.BaseScreenController;
import java.time.LocalDateTime;


public class OrderListController extends BaseScreenController {
    private final DBConnection db;
    @FXML
    private TableView<Order> OrderList0;
    @FXML
    private TableColumn<Order, Integer> OrderList1;
    @FXML
    private TableColumn<Order, LocalDateTime> OrderList2;
    @FXML
    private TableColumn<Order, Integer> OrderList3;
    @FXML
    private TableColumn<Order, Integer> OrderList4;
    public OrderService ordersService;
    public CustomerService customerService;
    public OrderItemServices orderItemService;
    public MenuItemServices menuItemService;
    public ComboBox<LocalDateTime> dateField;
    public ComboBox<Integer> customerOrderField;
    public Label OrderListTotal;
    @FXML
    private TableView<OrderItem> OrderList5;
    @FXML
    private TableColumn<OrderItem, String> OrderList6;
    @FXML
    private TableColumn<OrderItem, Integer> OrderList7;
    @FXML
    private TableColumn<OrderItem, String> OrderList8;
    @FXML
    private TableColumn<OrderItem, Float> OrderList9;
    @FXML
    private TextField OrderListCustomerName;
    @FXML
    private ComboBox<String> combox;

    @Inject
    public OrderListController(DBConnection db) {
        this.db = db;
        this.ordersService = new OrderService(this.db);
        this.customerService = new CustomerService(this.db);
        this.orderItemService = new OrderItemServices(this.db);
        this.menuItemService = new MenuItemServices(this.db);
    }

    @Override
    public void principalCargado() {
        ObservableList<Integer> observableList = FXCollections.observableArrayList();
        for (Order order:ordersService.getAll().getOrNull()) {
            observableList.add(order.getCustomerID());
        }
        ObservableList<LocalDateTime> observableList2 = FXCollections.observableArrayList();
        ordersService.getAll().getOrNull().forEach(order -> observableList2.add(order.getDateTime()));
        dateField.setVisible(false);
        customerOrderField.setVisible(false);
        combox.setItems(FXCollections.observableArrayList("CustomerID", "Date", "All"));
        customerOrderField.setItems(observableList);
        dateField.setItems(observableList2);
        OrderList1.setCellValueFactory(new PropertyValueFactory<>("id"));
        OrderList2.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        OrderList3.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        OrderList4.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        if (getPrincipalController().getActualUser().getId()>0){
            OrderList0.getItems().addAll(ordersService.getAll(getPrincipalController().getActualUser().getId()).getOrNull());
        }else {
            OrderList0.getItems().addAll(ordersService.getAll().getOrNull());
        }
        OrderList6.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMenuItem().getName()));
        OrderList7.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        OrderList8.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMenuItem().priceToString()));

        OrderList0.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            customerService.get(OrderList0.getSelectionModel().getSelectedItem().getId());
            OrderList5.getItems().clear();
            if (newSelection != null) {
                OrderListCustomerName.setText(customerService.get(OrderList0.getSelectionModel().getSelectedItem().getCustomerID()).get().getFirstName() + " " + customerService.get(OrderList0.getSelectionModel().getSelectedItem().getCustomerID()).get().getLastName());
                OrderList5.getItems().addAll(orderItemService.getAll(OrderList0.getSelectionModel().getSelectedItem().getId()).getOrNull());

            }
        });
    }

    public void filter(ActionEvent actionEvent) {
        String selectedCombox = combox.getSelectionModel().getSelectedItem();
        if (selectedCombox != null) {
            if (selectedCombox.equalsIgnoreCase("CustomerID")) {
                customerOrderField.setVisible(true);
                dateField.setVisible(false);
            } else if (selectedCombox.equalsIgnoreCase("Date")) {
                dateField.setVisible(true);
                customerOrderField.setVisible(false);
            } else if (selectedCombox.equalsIgnoreCase("All")) {
                OrderList0.getItems().clear();
                dateField.setVisible(false);
                customerOrderField.setVisible(false);
                principalCargado();
            }
        }
    }

    public void filterByID(ActionEvent actionEvent) {
        OrderList0.getItems().clear();
        OrderListCustomerName.clear();
        if (customerOrderField.getSelectionModel().getSelectedItem() != null) {
            OrderList0.getItems().addAll(ordersService.getCustomerID(customerOrderField.getSelectionModel().getSelectedItem()));
        }
    }

    public void filterByDate(ActionEvent actionEvent) {
        OrderList0.getItems().clear();
        OrderListCustomerName.clear();
        if (dateField != null) {
            OrderList0.getItems().addAll(ordersService.getDate(dateField.getSelectionModel().getSelectedItem()));
        }
    }
}
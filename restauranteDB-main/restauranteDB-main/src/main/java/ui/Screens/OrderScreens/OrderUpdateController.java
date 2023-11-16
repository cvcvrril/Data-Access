package ui.Screens.OrderScreens;

import common.Constantes;
import dao.implJDBC.DBConnection;
import jakarta.inject.Inject;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.MenuItem;
import model.Order;
import model.OrderItem;
import services.CustomerService;
import services.MenuItemServices;
import services.OrderItemServices;
import services.OrderService;
import ui.Screens.common.BaseScreenController;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderUpdateController extends BaseScreenController {
    private final DBConnection db;
    public TableView<Order> ordersTable;
    public TableColumn<Integer, Order> idOrderColumn;
    public TableColumn<LocalDateTime, Order> dateOrderColumn;
    public TableColumn<Integer, Order> customerOrderColumn;
    public TableColumn<Integer, Order> tableOrderColumn;
    public TableView<OrderItem> itemsTable;
    public TableColumn<OrderItem, Integer> idItemColumn;
    public TableColumn<OrderItem, String> nameItemColumn;
    public TableColumn<OrderItem, String> priceItemColumn;
    public TableColumn<OrderItem, Integer> descriptionItemColumn;
    public TextField dateOrderField;
    public ComboBox<Integer> tableOrderField;
    public ComboBox<String> customerOrderField;
    public ComboBox<String> itemsComboBox;
    public Spinner<Integer> quantityItemField;
    public OrderService orderService;
    public OrderItemServices orderItemServices;
    public MenuItemServices menuItemServices;
    public CustomerService customerService;
    public int orderItemid;

    @Inject
    public OrderUpdateController(DBConnection db) {
        this.db = db;
        this.orderService = new OrderService(this.db);
        this.orderItemServices = new OrderItemServices(this.db);
        this.menuItemServices = new MenuItemServices(this.db);
        this.customerService = new CustomerService(this.db);
    }

    @Override
    public void principalCargado() {
        orderItemid = orderItemServices.getAll().get().size() + 1;
        tableOrderField.getItems().addAll(1, 2, 3, 4, 5, 6);
        ObservableList<String> observableList4 = FXCollections.observableArrayList();
        for (Customer customer : customerService.getAll().getOrNull()) {
            observableList4.add(customer.getFirstName() + " " + customer.getLastName());
        }
        ObservableList<String> observableList5 = FXCollections.observableArrayList();
        for (MenuItem menuItem : menuItemServices.getAll().getOrNull()) {
            observableList5.add(menuItem.getName());
        }
        customerOrderField.getItems().addAll(observableList4);//cust
        itemsComboBox.getItems().addAll(observableList5);//menuitems
        idOrderColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateOrderColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        customerOrderColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        tableOrderColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        if (getPrincipalController().getActualUser().getId()>0){
            ordersTable.getItems().addAll(orderService.getAll(getPrincipalController().getActualUser().getId()).getOrNull());
        }else {
            ordersTable.getItems().addAll(orderService.getAll().getOrNull());
        }
        idItemColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameItemColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMenuItem().getName()));
        descriptionItemColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceItemColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMenuItem().priceToString()));

        ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                itemsTable.getItems().clear();
                dateOrderField.setText(String.valueOf(ordersTable.getSelectionModel().getSelectedItem().getDateTime()));
                itemsTable.getItems().addAll(orderItemServices.getAll(ordersTable.getSelectionModel().getSelectedItem().getId()).getOrNull());
                tableOrderField.setValue(ordersTable.getSelectionModel().getSelectedItem().getTableNumber());
                customerOrderField.setValue(customerService.get(ordersTable.getSelectionModel().getSelectedItem().getCustomerID()).getOrNull().getFirstName() + " " + customerService.get(ordersTable.getSelectionModel().getSelectedItem().getCustomerID()).getOrNull().getLastName());
            }
        });
        itemsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
            if (newSelection != null) {
                itemsComboBox.setValue(itemsTable.getSelectionModel().getSelectedItem().getMenuItem().getName());
                valueFactory.setValue(itemsTable.getSelectionModel().getSelectedItem().getQuantity());
                quantityItemField.setValueFactory(valueFactory);
            }
        });

    }

    public void updateOrder(ActionEvent actionEvent) {
        this.getPrincipalController().sacarAlertInfo(Constantes.OrderUPD + ordersTable.getSelectionModel().getSelectedItem().toString());
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            selectedOrder.setCustomerID(customerService.getIdByName(customerOrderField.getSelectionModel().getSelectedItem()));
            selectedOrder.setTableNumber(tableOrderField.getSelectionModel().getSelectedItem());
            orderService.update(selectedOrder);
            //INES MIRA ESTO, ESTO ES LO QUE TIENES QUE HACER, NO HAGAS MAS METODOS!!!!!
            //MIRA MI DAO DE ORDERITEM DATABASE PARA EL METODO DE AÑADIR Y BORRAR!!!!!!!
            if(orderItemServices.getAll(selectedOrder.getId())!=null){
                orderItemServices.deleteByOrder(selectedOrder.getId());
            }
            List<OrderItem> orderItems = new ArrayList<>(itemsTable.getItems());
            orderItemServices.insertByOrder(orderItems, selectedOrder.getId());
        }
        ordersTable.getItems().clear();
        ordersTable.getItems().addAll(orderService.getAll().getOrNull());
    }

    public void addItem(ActionEvent actionEvent) {
        int menuid = 0;
        String menuName = itemsComboBox.getSelectionModel().getSelectedItem();
        int menuQuantity = quantityItemField.getValue();
        for (MenuItem menuItem : menuItemServices.getAll().getOrNull()) {
            if (menuItem.getName().equalsIgnoreCase(menuName)) {
                menuid = menuItem.getId();
            }
        }
        int orderid = orderService.getAll().get().size() + 1;
        itemsTable.getItems().add(new OrderItem(orderItemid, orderid, menuItemServices.get(menuid).getOrNull(), menuQuantity));
        orderItemid = orderItemid + 1;
    }
    public void removeItem(ActionEvent actionEvent) {
        itemsTable.getItems().remove(itemsTable.getSelectionModel().getSelectedItem());
        orderItemid = orderItemid - 1;
    }
}

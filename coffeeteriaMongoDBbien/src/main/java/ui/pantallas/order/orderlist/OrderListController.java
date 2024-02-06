package ui.pantallas.order.orderlist;

import common.Constantes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import model.Customer;
import model.Order;
import model.OrderItem;
import model.errors.ErrorCMenuItem;
import model.errors.ErrorCOrderItem;
import model.mongo.CustomerMongo;
import model.mongo.OrderItemMongo;
import model.mongo.OrderMongo;
import services.ServiceCustomer;
import services.ServiceMenuItems;
import services.ServiceOrder;
import services.ServiceOrderItem;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class OrderListController extends BasePantallaController {

    private final ServiceOrder serviceOrder;
    private final ServiceCustomer serviceCustomer;
    private final ServiceMenuItems serviceMenuItems;
    private final ServiceOrderItem serviceOrderItem;


    @FXML
    private TableView<OrderMongo> tableOrders;
    @FXML
    private TableColumn<OrderMongo, Integer> id_table;
    @FXML
    private TableColumn<OrderMongo, LocalDate> date_order;
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private DatePicker fechaDatePicker;
    @FXML
    private TextField customerField;
    @FXML
    private TextField customerNameField;
    @FXML
    private TableView<OrderItemMongo> orderItemsTable;
    @FXML
    private TableColumn<OrderItemMongo, String> menuItemName;
    @FXML
    private TableColumn<OrderItemMongo, Integer> orderItemQuantity;

    /*Constructores*/

    @Inject
    public OrderListController(ServiceOrder serviceOrder, ServiceCustomer serviceCustomer, ServiceMenuItems serviceMenuItems, ServiceOrderItem serviceOrderItem) {
        this.serviceOrder = serviceOrder;
        this.serviceCustomer = serviceCustomer;
        this.serviceMenuItems = serviceMenuItems;
        this.serviceOrderItem = serviceOrderItem;
    }

    /*Métodos*/
    @Override
    public void principalCargado() {
        id_table.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        date_order.setCellValueFactory(new PropertyValueFactory<>("order_date"));
        if (getPrincipalController().getActualCredential().getUserName().equals("root")) {
            tableOrders.getItems().addAll(serviceCustomer.getAllOrders().get());
        } else {
            tableOrders.getItems().addAll(serviceCustomer.getAllOrders().get());
        }
        filterComboBox.getItems().addAll("Date", "Customer", "None");
        fechaDatePicker.setVisible(false);
        customerField.setVisible(false);
        tableOrders.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                CustomerMongo customer = null;
                //CustomerMongo customer = serviceCustomer.getByDate(newSelection).getOrNull();
                if (customer != null) {
                    customerNameField.setText(customer.getFirst_name());
                }
                loadOrderItems(newSelection.getOrder_items());
            }
        });
        orderItemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menuItemName.setCellValueFactory(cellData ->
                new SimpleStringProperty(getMenuItemNameById(cellData.getValue().getMenu_item_id())));
    }

    @FXML
    private void handleFilterSelection(ActionEvent event) {
        String selectedItem = filterComboBox.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.equals("Date")) {
                LocalDate selectedDate = fechaDatePicker.getValue();
                List<OrderMongo> filteredOrders = serviceCustomer.getAllOrders().get().stream()
                        .filter(order -> order.getOrder_date().toLocalDate().isEqual(selectedDate))
                        .collect(Collectors.toList());
                updateTable(filteredOrders);
            } else if (selectedItem.equals("Customer")) {
                String selectedCustomer = customerField.getText();
                List<OrderMongo> filteredOrders = serviceCustomer.getAllOrders().get();
                updateTable(filteredOrders);
            } else if (selectedItem.equals("None")) {
                tableOrders.getItems().clear();
                tableOrders.getItems().addAll(serviceCustomer.getAllOrders().get());
                clearFields();
            }
        }
    }

    private void updateTable(List<OrderMongo> orders) {
        tableOrders.getItems().clear();
        tableOrders.getItems().addAll(orders);
    }

    private void clearFields() {
        customerField.clear();
        fechaDatePicker.setValue(null);
    }

    public void hide() {
        if (Objects.equals(filterComboBox.getSelectionModel().getSelectedItem(), "Date")) {
            fechaDatePicker.setVisible(true);
            customerField.setVisible(false);
        } else if (Objects.equals(filterComboBox.getSelectionModel().getSelectedItem(), "Customer")) {
            fechaDatePicker.setVisible(false);
            customerField.setVisible(true);
        } else if (Objects.equals(filterComboBox.getSelectionModel().getSelectedItem(), "None")) {
            fechaDatePicker.setVisible(false);
            customerField.setVisible(false);
        }
    }

    public void setTableOrders() {
        try {
            OrderMongo selectedOrder = tableOrders.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                //loadOrderItemsByOrderId(selectedOrder.getOrder_items());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void loadOrderItems(List<OrderItemMongo> orderItemMongos) {
        //Either<ErrorCOrderItem, List<OrderItem>> orderItems = serviceOrderItem.get(orderId);
        if (orderItemMongos != null) {
            orderItemsTable.getItems().clear();
            orderItemsTable.getItems().addAll(orderItemMongos);
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Error al mostrar los order items (loadOrderItems)");
            errorAlert.show();
        }
    }

    private void loadOrderItemsByOrderId(int orderId) {
        Either<ErrorCOrderItem, List<OrderItem>> orderItems = serviceOrderItem.get(orderId);
        if (orderItems.isRight()) {
            orderItemsTable.getItems().clear();
            //orderItemsTable.getItems().addAll(orderItems.get());
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Error al mostrar los Order Items(loadOrderItemsByOrderId)");
            errorAlert.show();
        }
    }

    public String getMenuItemNameById(int id) {
        Either<ErrorCMenuItem, String> result = serviceMenuItems.getMenuItemName(id);
        if (result.isRight()) {
            return result.get();
        } else {
            return null;
        }
    }

}

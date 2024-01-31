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
import model.MenuItem;
import model.Order;
import model.OrderItem;
import model.errors.ErrorCMenuItem;
import model.errors.ErrorCOrder;
import model.errors.ErrorCOrderItem;
import services.ServiceCustomer;
import services.ServiceMenuItems;
import services.ServiceOrder;
import services.ServiceOrderItem;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;
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
    private TableView<Order> tableOrders;
    @FXML
    private TableColumn<Order, Integer> id_ord;
    @FXML
    private TableColumn<Order, Integer> id_c;
    @FXML
    private TableColumn<Order, Integer> id_table;
    @FXML
    private TableColumn<Order, LocalDate> date_order;
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private DatePicker fechaDatePicker;
    @FXML
    private TextField customerField;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField totalAmountField;
    @FXML
    private TableView<OrderItem> orderItemsTable;
    @FXML
    private TableColumn<OrderItem, String> menuItemName;
    @FXML
    private TableColumn<OrderItem, Integer> orderItemQuantity;
    @FXML
    private TableColumn<OrderItem, Integer> orderItemID;
    @FXML
    private TableColumn<OrderItem, String> priceCol;

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
        id_ord.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_ORD));
        id_c.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_CO));
        id_table.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_TABLE));
        date_order.setCellValueFactory(new PropertyValueFactory<>(Constantes.OR_DATE));
        if (getPrincipalController().getActualCredential().getId() > 0) {
            tableOrders.getItems().addAll(serviceOrder.getOrders(this.getPrincipalController().getActualCredential().getId()).getOrNull());
        } else {
            tableOrders.getItems().addAll(serviceOrder.getAll());
        }
        filterComboBox.getItems().addAll("Date", "Customer", "None");
        fechaDatePicker.setVisible(false);
        customerField.setVisible(false);
        tableOrders.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int customerId = newSelection.getIdCo();
                Customer customer = serviceCustomer.get(customerId).getOrNull();
                if (customer != null) {
                    customerNameField.setText(customer.getFirstName());
                }
                loadOrderItems(newSelection.getIdOrd());
            }
        });
        orderItemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderItemID.setCellValueFactory(new PropertyValueFactory<>("id"));
        menuItemName.setCellValueFactory(cellData ->
                new SimpleStringProperty(getMenuItemNameById(cellData.getValue().getMenuItemObject().getIdMItem())));
        priceCol.setCellValueFactory(cellData -> {
            int menuItemId = cellData.getValue().getMenuItemObject().getIdMItem();
            String menuItemPrice = String.valueOf(menuItemId);
            return new SimpleStringProperty(menuItemPrice);
        });
    }

    @FXML
    private void handleFilterSelection(ActionEvent event) {
        String selectedItem = filterComboBox.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.equals("Date")) {
                LocalDate selectedDate = fechaDatePicker.getValue();
                List<Order> filteredOrders = serviceOrder.getAll().stream()
                        .filter(order -> order.getOrDate().toLocalDate().isEqual(selectedDate))
                        .collect(Collectors.toList());
                updateTable(filteredOrders);
            } else if (selectedItem.equals("Customer")) {
                int selectedCustomerId = Integer.parseInt(customerField.getText());
                List<Order> filteredOrders = serviceOrder.getOrdersByCustomer(selectedCustomerId);
                updateTable(filteredOrders);
            } else if (selectedItem.equals("None")) {
                tableOrders.getItems().clear();
                tableOrders.getItems().addAll(serviceOrder.getAll());
                clearFields();
            }
        }
    }

    private void updateTable(List<Order> orders) {
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
            Order selectedOrder = tableOrders.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                loadOrderItemsByOrderId(selectedOrder.getIdOrd());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    //TODO: Arreglar totalAmount (la multiplicación no la hace bien)

    private void loadOrderItems(int orderId) {
        Either<ErrorCOrderItem, List<OrderItem>> orderItems = serviceOrderItem.get(orderId);
        if (orderItems.isRight()) {
            orderItemsTable.getItems().clear();
            orderItemsTable.getItems().addAll(orderItems.get());

            double totalAmount = orderItems.get().stream()
                    .mapToDouble(orderItem -> {
                        int menuItemId = orderItem.getMenuItemObject().getIdMItem();
                        // Obtener el precio del menú item
                        Double menuItemPrice = Double.valueOf(priceCol.get);
                        int menuItemQuantity = orderItems.get().size();

                        return Double.parseDouble(String.valueOf(menuItemPrice * menuItemQuantity));

                    }).sum();


            // Actualizar el totalAmountField
            totalAmountField.setText(String.valueOf(totalAmount));

        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Error al mostrar los order items (loadOrderItems)");
            errorAlert.show();
        }
    }

    //INFO: Lista de Order -> Lista de OrderItem -> MenuItem -> precio del MenuItem

    private Either<ErrorCOrder, Double> setTotalAmountField(List<OrderItem> orderItemList) {
        Either<ErrorCOrder, Double> res;
        Double totalAmount;
        try {
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        }
        return null;
    }

    private void loadOrderItemsByOrderId(int orderId) {
        Either<ErrorCOrderItem, List<OrderItem>> orderItems = serviceOrderItem.get(orderId);
        if (orderItems.isRight()) {
            orderItemsTable.getItems().clear();
            orderItemsTable.getItems().addAll(orderItems.get());
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

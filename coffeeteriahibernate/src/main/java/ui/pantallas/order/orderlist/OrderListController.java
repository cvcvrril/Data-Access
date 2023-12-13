package ui.pantallas.order.orderlist;

import common.Constantes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
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
import services.SERVcustomer;
import services.SERVmenuItems;
import services.SERVorder;
import services.SERVorderItem;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class OrderListController extends BasePantallaController {

    private final SERVorder serVorder;
    private final SERVcustomer serVcustomer;
    private final SERVmenuItems serVmenuItems;
    private final SERVorderItem serVorderItem;


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
    public OrderListController(SERVorder serVorder, SERVcustomer serVcustomer, SERVmenuItems serVmenuItems, SERVorderItem serVorderItem) {
        this.serVorder = serVorder;
        this.serVcustomer = serVcustomer;
        this.serVmenuItems = serVmenuItems;
        this.serVorderItem = serVorderItem;
    }

    /*Métodos*/
    @Override
    public void principalCargado() {
        id_ord.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_ORD));
        id_c.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_CO));
        id_table.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_TABLE));
        date_order.setCellValueFactory(new PropertyValueFactory<>(Constantes.OR_DATE));
        if (getPrincipalController().getActualCredential().getId() > 0) {
            tableOrders.getItems().addAll(serVorder.getOrders(this.getPrincipalController().getActualCredential().getId()).getOrNull());
        } else {
            tableOrders.getItems().addAll(serVorder.getAll());
        }
        filterComboBox.getItems().addAll("Date", "Customer", "None");
        fechaDatePicker.setVisible(false);
        customerField.setVisible(false);
        tableOrders.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int customerId = newSelection.getIdCo();
                Customer customer = serVcustomer.get(customerId).getOrNull();
                if (customer != null) {
                    customerNameField.setText(customer.getFirstName());
                }
                loadOrderItems(newSelection.getIdOrd());
            }
        });
        orderItemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderItemID.setCellValueFactory(new PropertyValueFactory<>("id"));
        menuItemName.setCellValueFactory(cellData ->
                new SimpleStringProperty(getMenuItemNameById(cellData.getValue().getMenuItem())));
        priceCol.setCellValueFactory(cellData->{
            int menuItemId = cellData.getValue().getMenuItem();
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
                List<Order> filteredOrders = serVorder.getAll().stream()
                        .filter(order -> order.getOrDate().toLocalDate().isEqual(selectedDate))
                        .collect(Collectors.toList());
                updateTable(filteredOrders);
            } else if (selectedItem.equals("Customer")) {
                int selectedCustomerId = Integer.parseInt(customerField.getText());
                List<Order> filteredOrders = serVorder.getOrdersByCustomer(selectedCustomerId);
                updateTable(filteredOrders);
            } else if (selectedItem.equals("None")) {
                tableOrders.getItems().clear();
                tableOrders.getItems().addAll(serVorder.getAll());
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

    private void loadOrderItems(int orderId) {
        Either<ErrorCOrderItem, List<OrderItem>> orderItems = serVorderItem.get(orderId);
        if (orderItems.isRight()) {
            orderItemsTable.getItems().clear();
            orderItemsTable.getItems().addAll(orderItems.get());

            double totalAmount = orderItems.get().stream()
                    .mapToDouble(orderItem -> {
                        int menuItemId = orderItem.getMenuItem();
                        // Obtener el precio del menú item
                        Either<ErrorCMenuItem, Double> menuItemPrice = serVmenuItems.getMenuItemPrice(menuItemId);
                        if (menuItemPrice.isRight()) {
                            return Double.parseDouble(String.valueOf(menuItemPrice.get()));
                        } else {
                            // Puedes manejar el error de alguna manera, como imprimir un mensaje de error
                            log.error("Error al obtener el precio del MenuItem: {}", menuItemId);
                            return 0.0;
                        }
                    })
                    .sum();

            // Actualizar el totalAmountField
            totalAmountField.setText(String.valueOf(totalAmount));

        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Error al mostrar los order items (loadOrderItems)");
            errorAlert.show();
        }
    }

    private void loadOrderItemsByOrderId(int orderId) {
        Either<ErrorCOrderItem, List<OrderItem>> orderItems = serVorderItem.get(orderId);
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
        Either<ErrorCMenuItem, String> result = serVmenuItems.getMenuItemName(id);
        if (result.isRight()) {
            return result.get();
        } else {
            return null;
        }
    }

}

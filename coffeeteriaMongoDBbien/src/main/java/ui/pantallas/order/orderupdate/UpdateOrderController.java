package ui.pantallas.order.orderupdate;

import common.Constantes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MenuItem;
import model.Order;
import model.OrderItem;
import model.TableRestaurant;
import model.errors.ErrorCMenuItem;
import model.errors.ErrorCTables;
import model.mongo.MenuItemMongo;
import model.mongo.OrderItemMongo;
import model.mongo.OrderMongo;
import services.*;
import ui.pantallas.common.BasePantallaController;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpdateOrderController extends BasePantallaController {

    private final ServiceOrder serviceOrder;
    private final ServiceOrderItem serviceOrderItem;
    private final ServiceMenuItems serviceMenuItems;
    private final ServiceTablesRestaurant serviceTablesRestaurant;
    private final ServiceCustomer serviceCustomer;

    @FXML
    private TableView<OrderMongo> tableOrders;
    @FXML
    private TableColumn<OrderMongo, Integer> id_table;
    @FXML
    private TableColumn<OrderMongo, LocalDate> date_order;
    @FXML
    private TextField dateField;
    @FXML
    private ComboBox<Integer> tableComboBox;
    @FXML
    private ComboBox<Integer> customerComboBox;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<String> menuItemComboBox;

    @FXML
    private TableView<OrderItemMongo> orderItemTable;
    @FXML
    private TableColumn<OrderItemMongo, String> nameItemCell;
    @FXML
    private TableColumn<OrderItemMongo, Integer> quantityCell;
    private int lastOrderItemId;

    @Inject
    public UpdateOrderController(ServiceOrder serviceOrder, ServiceOrderItem serviceOrderItem, ServiceMenuItems serviceMenuItems, ServiceTablesRestaurant serviceTablesRestaurant, ServiceCustomer serviceCustomer) {
        this.serviceOrder = serviceOrder;
        this.serviceOrderItem = serviceOrderItem;
        this.serviceMenuItems = serviceMenuItems;
        this.serviceTablesRestaurant = serviceTablesRestaurant;
        this.serviceCustomer = serviceCustomer;
    }

    @Override
    public void principalCargado() {
        lastOrderItemId = serviceOrderItem.getAll().get().size() + 1;
        //Order Table Columns
        id_table.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        date_order.setCellValueFactory(new PropertyValueFactory<>("order_date"));
        //Llenar OrderTable
        if (!getPrincipalController().getActualCredential().getUsername().equals("root")) {
            tableOrders.getItems().addAll(serviceCustomer.getAllOrders().get());
        } else {
            tableOrders.getItems().addAll(serviceCustomer.getAllOrders().get());
        }
        tableOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                orderItemTable.getItems().clear();
                OrderMongo selOrder = tableOrders.getSelectionModel().getSelectedItem();
                if (selOrder != null) {
                    dateField.setText(String.valueOf(selOrder.getOrder_date()));
                }
                //orderItemTable.getItems().addAll(serviceOrderItem.get(tableOrders.getSelectionModel().getSelectedItem().getIdOrd()).getOrNull());
                assert selOrder != null;
                orderItemTable.getItems().addAll(selOrder.getOrder_items());
            }
        });
        //Columnas Item table
        quantityCell.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        nameItemCell.setCellValueFactory(cellData -> {
            int menuItemId = cellData.getValue().getMenu_item_id();
            String menuItemName = getMenuItemNameById(menuItemId);
            return new SimpleStringProperty(menuItemName);
        });
        //Llenar los campos de OrderItem
        orderItemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                orderItemTable.getSelectionModel().getSelectedItem().getMenu_item_id();
                menuItemComboBox.setValue(String.valueOf(orderItemTable.getSelectionModel().getSelectedItem().getMenu_item_id()));
                quantityField.setText(String.valueOf(orderItemTable.getSelectionModel().getSelectedItem().getQuantity()));
            }
        });
        //ComboBox
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (MenuItemMongo menuItem : serviceMenuItems.getAll().getOrNull()) {
            observableList.add(menuItem.getName());
        }
        menuItemComboBox.getItems().addAll(observableList);
        Either<ErrorCTables, List<TableRestaurant>> result = serviceTablesRestaurant.getAll();
        if (result.isRight()) {
            List<TableRestaurant> tables = result.get();
            for (TableRestaurant table : tables) {
                tableComboBox.getItems().add(table.getIdTable());
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Error al obtener la lista de mesas");
            errorAlert.show();
        }
        List<Integer> customerIDs = serviceOrder.getCustomerIDs();
        customerComboBox.getItems().addAll(customerIDs);
        if (!getPrincipalController().getActualCredential().getUsername().equals("root")) {
            customerComboBox.setVisible(false);
        }else {
            customerComboBox.setVisible(true);
        }
    }

    public void addItem() {
        String selectedItemName = menuItemComboBox.getSelectionModel().getSelectedItem();
        int quantity = Integer.parseInt(quantityField.getText());
        MenuItemMongo selectedMenuItem = serviceMenuItems.getMenuItemByName(selectedItemName).getOrNull();
        for (MenuItemMongo menuItem : serviceMenuItems.getAll().getOrElse(Collections.emptyList())) {
            if (menuItem.getName().equals(selectedItemName)) {
                selectedMenuItem = menuItem;
            }
        }
        if (selectedMenuItem != null) {

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText(Constantes.THE_MENU_ITEM_HAS_BEEN_ADDED);
            a.show();
            orderItemTable.getItems().add(new OrderItemMongo(selectedMenuItem.get_id(), quantity));
            menuItemComboBox.getSelectionModel().clearSelection();
            quantityField.clear();
            lastOrderItemId = lastOrderItemId + 1;
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Ítem de menú no encontrado");
            errorAlert.show();
        }


    }

    public void removeItem() {
        orderItemTable.getItems().remove(orderItemTable.getSelectionModel().getSelectedItem());
        quantityField.clear();
        lastOrderItemId = lastOrderItemId - 1;
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText(Constantes.THE_MENU_ITEM_HAS_BEEN_REMOVED);
        a.show();
    }

    public void updateOrder() {
        OrderMongo selectedOrder = tableOrders.getSelectionModel().getSelectedItem();
        List<OrderItemMongo> orderItemMongos = orderItemTable.getItems();
        if (selectedOrder != null) {
            selectedOrder.setTable_id(Integer.parseInt(String.valueOf(tableComboBox.getValue())));
            selectedOrder.setOrder_items(orderItemMongos);
            serviceCustomer.updateOrder(selectedOrder);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText(Constantes.ORDER_UPDATED);
            a.show();
        }
        orderItemTable.getItems().clear();
        orderItemTable.getItems().addAll(selectedOrder.getOrder_items());
    }

    public String getMenuItemNameById(int id) {
        Either<ErrorCMenuItem, String> result = serviceMenuItems.getMenuItemName(id);
        return result.get();
    }
}

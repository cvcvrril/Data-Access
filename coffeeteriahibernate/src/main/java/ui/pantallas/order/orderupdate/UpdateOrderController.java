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
import services.ServiceMenuItems;
import services.ServiceOrder;
import services.ServiceOrderItem;
import services.ServiceTablesRestaurant;
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
    private Button addItemButton;
    @FXML
    private Button removeItemButton;
    @FXML
    private Button updateOrderButton;
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
    private TableView<OrderItem> orderItemTable;
    @FXML
    private TableColumn<OrderItem, String> nameItemCell;
    @FXML
    private TableColumn<OrderItem, Integer> quantityCell;
    private int lastOrderItemId;

    @Inject
    public UpdateOrderController(ServiceOrder serviceOrder, ServiceOrderItem serviceOrderItem, ServiceMenuItems serviceMenuItems, ServiceTablesRestaurant serviceTablesRestaurant) {
        this.serviceOrder = serviceOrder;
        this.serviceOrderItem = serviceOrderItem;
        this.serviceMenuItems = serviceMenuItems;
        this.serviceTablesRestaurant = serviceTablesRestaurant;
    }

    @Override
    public void principalCargado() {
        lastOrderItemId = serviceOrderItem.getAll().get().size() + 1;
        //Order Table Columns
        id_ord.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_ORD));
        id_c.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_CO));
        id_table.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_TABLE));
        date_order.setCellValueFactory(new PropertyValueFactory<>(Constantes.OR_DATE));
        //Llenar OrderTable
        if (getPrincipalController().getActualCredential().getId() > 0) {
            tableOrders.getItems().addAll(serviceOrder.getOrders(this.getPrincipalController().getActualCredential().getId()).getOrNull());
        } else {
            tableOrders.getItems().addAll(serviceOrder.getAll());
        }
        tableOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                orderItemTable.getItems().clear();
                Order selOrder = tableOrders.getSelectionModel().getSelectedItem();
                if (selOrder != null) {
                    dateField.setText(String.valueOf(selOrder.getOrDate()));
                }
                orderItemTable.getItems().addAll(serviceOrderItem.get(tableOrders.getSelectionModel().getSelectedItem().getIdOrd()).getOrNull());

            }
        });
        //Columnas Item table
        quantityCell.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        nameItemCell.setCellValueFactory(cellData -> {
            int menuItemId = cellData.getValue().getMenuItemObject().getIdMItem();
            String menuItemName = getMenuItemNameById(menuItemId);
            return new SimpleStringProperty(menuItemName);
        });
        //Llenar los campos de OrderItem
        orderItemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                orderItemTable.getSelectionModel().getSelectedItem().setMenuItemObject(serviceMenuItems.get(orderItemTable.getSelectionModel().getSelectedItem().getMenuItemObject().getIdMItem()).getOrNull());
                menuItemComboBox.setValue(orderItemTable.getSelectionModel().getSelectedItem().getMenuItemObject().getNameMItem());
                quantityField.setText(String.valueOf(orderItemTable.getSelectionModel().getSelectedItem().getQuantity()));
            }
        });
        //ComboBox
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (MenuItem menuItem : serviceMenuItems.getAll().getOrNull()) {
            observableList.add(menuItem.getNameMItem());
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
        if (getPrincipalController().getActualCredential().getId() > 0) {
            customerComboBox.setVisible(false);
        }else {
            customerComboBox.setVisible(true);
        }
    }

    public void addItem() {
        String selectedItemName = menuItemComboBox.getSelectionModel().getSelectedItem();
        int quantity = Integer.parseInt(quantityField.getText());
        MenuItem selectedMenuItem = serviceMenuItems.getMenuItemByName(selectedItemName).getOrNull();
        for (MenuItem menuItem : serviceMenuItems.getAll().getOrElse(Collections.emptyList())) {
            if (menuItem.getNameMItem().equals(selectedItemName)) {
                selectedMenuItem = menuItem;
            }
        }
        if (selectedMenuItem != null) {

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText(Constantes.THE_MENU_ITEM_HAS_BEEN_ADDED);
            a.show();
            // Agregar el nuevo OrderItem a la tabla
            //orderItemTable.getItems().add(new OrderItem(lastOrderItemId, tableOrders.getSelectionModel().getSelectedItem().getIdOrd(), selectedMenuItem.getIdMItem(), quantity, serviceMenuItems.get(lastOrderItemId).getOrNull()));
            orderItemTable.getItems().add(new OrderItem(tableOrders.getSelectionModel().getSelectedItem().getIdOrd(), quantity, serviceMenuItems.get(lastOrderItemId).getOrNull(), serviceOrder.getOrder(tableOrders.getSelectionModel().getSelectedItem().getIdOrd()).getOrNull()));
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
        int customerId;
        if (getPrincipalController().getActualCredential().getId() > 0) {
            customerId = getPrincipalController().getActualCredential().getId();
        } else {
            customerId = customerComboBox.getValue();
        }
        Order selectedOrder = tableOrders.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            selectedOrder.setIdCo(customerId);
            selectedOrder.setIdTable(Integer.parseInt(String.valueOf(tableComboBox.getValue())));
            serviceOrder.updateOrder(selectedOrder);
            if (serviceOrderItem.get(selectedOrder.getIdOrd()) != null) {
                serviceOrderItem.delete(selectedOrder.getIdOrd());
            }
            List<OrderItem> orderItems =new ArrayList<>(orderItemTable.getItems());
            serviceOrderItem.add(orderItems, selectedOrder.getIdOrd());
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText(Constantes.ORDER_UPDATED);
            a.show();
        }
        orderItemTable.getItems().clear();
        orderItemTable.getItems().addAll(serviceOrderItem.getAll().getOrNull());
    }

    public String getMenuItemNameById(int id) {
        Either<ErrorCMenuItem, String> result = serviceMenuItems.getMenuItemName(id);
        return result.get();
    }
}

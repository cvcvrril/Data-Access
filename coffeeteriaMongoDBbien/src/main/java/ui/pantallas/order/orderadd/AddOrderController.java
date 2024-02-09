package ui.pantallas.order.orderadd;

import common.Constantes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MenuItem;
import model.Order;
import model.OrderItem;
import model.TableRestaurant;
import model.errors.ErrorCMenuItem;
import model.errors.ErrorCObject;
import model.errors.ErrorCOrder;
import model.errors.ErrorCTables;
import model.mongo.CustomerMongo;
import model.mongo.OrderItemMongo;
import model.mongo.OrderMongo;
import org.bson.types.ObjectId;
import services.*;
import ui.pantallas.common.BasePantallaController;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//TODO: Propongo arreglar esto

public class AddOrderController extends BasePantallaController {

    private final ServiceOrder serviceOrder;
    private final ServiceTablesRestaurant serviceTablesRestaurant;
    private final ServiceMenuItems serviceMenuItems;
    private final ServiceOrderItem serviceOrderItem;
    private final ServiceCustomer serviceCustomer;

    @FXML
    private TableView<OrderItemMongo> mItemTable;
    @FXML
    private TableColumn<OrderItemMongo, Integer> mItemIDCol;
    @FXML
    private TableColumn<OrderItemMongo, String> mItemNameCol;
    @FXML
    private TableColumn<OrderItemMongo, Integer> quantityCol;

    @FXML
    private ComboBox<Integer> tableComboBox;
    @FXML
    private ComboBox<String> menuItemsCBox;
    @FXML
    private TextField menuItemQuantity;

    @FXML
    private Button addOrderButton;
    @FXML
    private Button addItemButton;
    @FXML
    private Button removeItemButton;

    @Inject
    public AddOrderController(ServiceOrder serviceOrder, ServiceTablesRestaurant serviceTablesRestaurant, ServiceMenuItems serviceMenuItems, ServiceOrderItem serviceOrderItem, ServiceCustomer serviceCustomer) {
        this.serviceOrder = serviceOrder;
        this.serviceTablesRestaurant = serviceTablesRestaurant;
        this.serviceMenuItems = serviceMenuItems;
        this.serviceOrderItem = serviceOrderItem;
        this.serviceCustomer = serviceCustomer;
    }

    public void principalCargado() {
        List<MenuItem> menuItems = serviceMenuItems.getAll().getOrElse(Collections.emptyList());
        for (MenuItem menuItem : menuItems ){
            menuItemsCBox.getItems().add(menuItem.getNameMItem());
        }
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
        mItemIDCol.setCellValueFactory(new PropertyValueFactory<>("menu_item_id"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        mItemNameCol.setCellValueFactory(cellData -> {
            int menuItemId = cellData.getValue().getMenu_item_id();
            return new SimpleStringProperty(getMenuItemNameById(menuItemId));
        });

    }

    @FXML
    public void addOrder(ActionEvent actionEvent) {
        int tableId = tableComboBox.getValue();
        LocalDateTime orderDate = LocalDateTime.now();
        List<OrderItemMongo> orderItems = new ArrayList<>(mItemTable.getItems());
        OrderMongo newOrder = new OrderMongo(orderDate, tableId, orderItems);

        Either<ErrorCObject, Integer> saveResult = serviceCustomer.addOrder(newOrder);
        if (saveResult.isRight()) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText(Constantes.THE_ORDER_HAS_BEEN_ADDED);
            a.show();
            clearFields();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Error al agregar la orden");
            a.show();
        }
    }

    private void clearFields() {
        tableComboBox.getSelectionModel().clearSelection();
    }

    public void addItem() {
        String selectedItemName = menuItemsCBox.getValue();
        int quantity = Integer.parseInt(menuItemQuantity.getText());

        MenuItem selectedMenuItem;
        for (MenuItem menuItem : serviceMenuItems.getAll().getOrElse(Collections.emptyList())) {
            if (menuItem.getNameMItem().equals(selectedItemName)) {
                selectedMenuItem = menuItem;
                if (selectedMenuItem != null) {
                    OrderItemMongo newOrderItem = new OrderItemMongo (0, quantity);
                    mItemTable.getItems().add(newOrderItem);
                    menuItemsCBox.getSelectionModel().clearSelection();
                    menuItemQuantity.clear();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setContentText("Ítem de menú no encontrado");
                    errorAlert.show();
                }
                break;
            }
        }


    }

    public void removeItem() {
        mItemTable.getItems().clear();
    }

    public String getMenuItemNameById(int id) {
        Either<ErrorCMenuItem, String> result = serviceMenuItems.getMenuItemName(id);
        if(result.isRight()) {
            return result.get();
        } else {
            return null;
        }
    }
}

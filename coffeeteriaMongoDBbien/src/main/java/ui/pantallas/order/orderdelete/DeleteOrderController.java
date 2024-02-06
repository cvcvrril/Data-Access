package ui.pantallas.order.orderdelete;

import common.Constantes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrderItem;
import model.errors.ErrorCMenuItem;
import model.errors.ErrorCObject;
import model.mongo.OrderItemMongo;
import model.mongo.OrderMongo;
import services.ServiceCustomer;
import services.ServiceMenuItems;
import services.ServiceOrder;
import services.ServiceOrderItem;
import ui.pantallas.common.BasePantallaController;
;
import java.time.LocalDate;
import java.util.List;

public class DeleteOrderController extends BasePantallaController {

    private final ServiceOrder serviceOrder;
    private final ServiceOrderItem serviceOrderItem;
    private final ServiceMenuItems serviceMenuItems;
    private final ServiceCustomer serviceCustomer;

    @FXML
    private TableView<OrderItemMongo> orderItemsTable;
    @FXML
    private TableColumn<OrderItemMongo, String> nameItemCell;

    @FXML
    private TableColumn<OrderMongo, Integer> quantityCell;
    @FXML
    private TableView<OrderMongo> tableOrders;
    @FXML
    private TableColumn<OrderMongo, Integer> idTable;
    @FXML
    private TableColumn<OrderMongo, LocalDate> dateOrder;
    @FXML
    private Button delOrderButton;

    @Inject
    public DeleteOrderController(ServiceOrder serviceOrder, ServiceOrderItem serviceOrderItem, ServiceMenuItems serviceMenuItems, ServiceCustomer serviceCustomer) {
        this.serviceOrder = serviceOrder;
        this.serviceOrderItem = serviceOrderItem;
        this.serviceMenuItems = serviceMenuItems;
        this.serviceCustomer = serviceCustomer;
    }

    public void principalCargado() {
        idTable.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        dateOrder.setCellValueFactory(new PropertyValueFactory<>("order_date"));
        tableOrders.getItems().addAll(serviceCustomer.getAllOrders().get());
        tableOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateOrderItemsTable(newValue);
            }
        });
        quantityCell.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        nameItemCell.setCellValueFactory(cellData -> {
            int menuItemId = cellData.getValue().getMenu_item_id();
            String menuItemName = getMenuItemNameById(menuItemId);
            return new SimpleStringProperty(menuItemName);
        });
    }

    public void delOrder(ActionEvent actionEvent) {
        OrderMongo selectedOrder = tableOrders.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            Either<ErrorCObject, Integer> deleteResult = serviceCustomer.deleteOrder(selectedOrder);
            if (deleteResult.isRight()) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText(Constantes.ORDER_DELETED);
                a.show();
                orderItemsTable.getItems().clear();
                tableOrders.getItems().remove(selectedOrder);
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Error deleting order: " + deleteResult.getLeft());
                errorAlert.show();
            }
        } else {
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setContentText("Please select an order to delete.");
            infoAlert.show();
        }
    }

    public void updateOrderItemsTable(OrderMongo order) {
        List<OrderItemMongo> orderItems = order.getOrder_items();
        orderItemsTable.getItems().setAll(orderItems);
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

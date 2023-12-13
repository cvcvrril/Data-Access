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
import model.Order;
import model.OrderItem;
import model.errors.ErrorCMenuItem;
import model.errors.ErrorCOrder;
import services.SERVmenuItems;
import services.SERVorder;
import services.SERVorderItem;
import ui.pantallas.common.BasePantallaController;
;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class DeleteOrderController extends BasePantallaController {

    private final SERVorder serVorder;
    private final SERVorderItem serVorderItem;
    private final SERVmenuItems serVmenuItems;
    @FXML
    private TableView<OrderItem> orderItemsTable;
    @FXML
    private TableColumn<OrderItem, String> nameItemCell;
    @FXML
    private TableColumn<Order, Integer> quantityCell;

    @FXML
    private TableView<Order> tableOrders;
    @FXML
    private TableColumn<Order, Integer> idOrd;
    @FXML
    private TableColumn<Order, Integer> idC;
    @FXML
    private TableColumn<Order, Integer> idTable;
    @FXML
    private TableColumn<Order, LocalDate> dateOrder;
    @FXML
    private Button delOrderButton;

    @Inject
    public DeleteOrderController(SERVorder serVorder, SERVorderItem serVorderItem, SERVmenuItems serVmenuItems) {
        this.serVorder = serVorder;
        this.serVorderItem = serVorderItem;
        this.serVmenuItems = serVmenuItems;
    }

    public void principalCargado() {

        idOrd.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_ORD));
        idC.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_CO));
        idTable.setCellValueFactory(new PropertyValueFactory<>(Constantes.ID_TABLE));
        dateOrder.setCellValueFactory(new PropertyValueFactory<>(Constantes.OR_DATE));
        tableOrders.getItems().addAll(serVorder.getAll());
        tableOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateOrderItemsTable(newValue);
            }
        });
        quantityCell.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        nameItemCell.setCellValueFactory(cellData -> {
            int menuItemId = cellData.getValue().getMenuItem();
            String menuItemName = getMenuItemNameById(menuItemId);
            return new SimpleStringProperty(menuItemName);
        });
    }

    public void delOrder(ActionEvent actionEvent) {
        Order selectedOrder = tableOrders.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            Either<ErrorCOrder, Integer> deleteResult = serVorder.delOrder(selectedOrder.getIdOrd());
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

    public void updateOrderItemsTable(Order order) {
        List<OrderItem> orderItems = serVorderItem.get(order.getIdOrd()).getOrElse(Collections.emptyList());
        orderItemsTable.getItems().setAll(orderItems);
    }

    public String getMenuItemNameById(int id) {
        Either<ErrorCMenuItem, String> result = serVmenuItems.getMenuItemName(id);
        if(result.isRight()) {
            return result.get();
        } else {
            return null;
        }
    }
}

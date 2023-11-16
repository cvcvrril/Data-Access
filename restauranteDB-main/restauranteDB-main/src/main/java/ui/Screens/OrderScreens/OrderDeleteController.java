package ui.Screens.OrderScreens;

import common.Constantes;
import dao.implJDBC.DBConnection;
import jakarta.inject.Inject;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import model.OrderItem;
import services.OrderItemServices;
import services.OrderService;
import ui.Screens.common.BaseScreenController;
import java.time.LocalDateTime;

public class OrderDeleteController extends BaseScreenController {
    @FXML
    public TableView<Order> OrderDel0;
    @FXML
    public TableColumn<Order,Integer> OrderDel1;
    @FXML
    public TableColumn<Order, LocalDateTime> OrderDel2;
    @FXML
    public TableColumn<Order,Integer> OrderDel3;
    @FXML
    public TableColumn<Order,Integer> OrderDel4;
    @FXML
    public TableView<OrderItem> OrderDel5;
    public TableColumn<OrderItem,String> OrderDel6;
    public TableColumn<OrderItem,Integer> OrderDel7;
    public TableColumn<OrderItem,String> OrderDel8;
    public TableColumn<OrderItem,Float> OrderDel9;
    public Button OrderDel10;
    public OrderService orderService;
    public OrderItemServices orderItemServices;
    public boolean delete=false;
    private final DBConnection db;
    @Inject
    public OrderDeleteController(DBConnection db) {
        this.db = db;
        this.orderService =new OrderService(this.db);
        this.orderItemServices=new OrderItemServices(this.db);
    }
    @Override
    public void principalCargado() {

        OrderDel1.setCellValueFactory(new PropertyValueFactory<>("id"));
        OrderDel2.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        OrderDel3.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        OrderDel4.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        OrderDel0.getItems().addAll(orderService.getAll().getOrNull());
        OrderDel6.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMenuItem().getName()));
        OrderDel7.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        OrderDel8.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMenuItem().priceToString()));

        OrderDel0.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            OrderDel5.getItems().clear();
            if (newSelection != null) {
                OrderDel5.getItems().addAll(orderItemServices.getAll(OrderDel0.getSelectionModel().getSelectedItem().getId()).getOrNull());

            }
        });
    }
    public void delete() {
        delete = this.getPrincipalController().sacarAlertConf(Constantes.OrderDEL + OrderDel0.getSelectionModel().getSelectedItem().toString());
        if (delete) {
            orderService.delete(OrderDel0.getSelectionModel().getSelectedItem(), true);
            OrderDel0.getItems().remove(OrderDel0.getSelectionModel().getSelectedItem());
            this.getPrincipalController().sacarAlertInfo("Order has been deleted");
        } else {
            this.getPrincipalController().sacarAlertInfo("Nothig deleted");
        }
    }
}

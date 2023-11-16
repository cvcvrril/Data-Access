package ui.Screens.OrderScreens;

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

public class OrdersAddController extends BaseScreenController {
    public TableView<OrderItem> orderAdd0;
    public TableColumn<OrderItem, Integer> orderAdd1;
    public TableColumn<OrderItem, String> orderAdd2;
    public TableColumn<OrderItem, String> orderAdd3;
    public TableColumn<OrderItem, Integer> orderAdd4;
    public TextField orderAdd5;
    public ComboBox<Integer> orderAdd6;
    public ComboBox<String> orderAdd7;
    public ComboBox<String> orderAdd8;
    public Spinner<Integer> orderAdd9;
    public CustomerService customerService;
    public OrderService orderService;
    public MenuItemServices menuItemServices;
    public OrderItemServices orderItemServices;
    public int orderItemid;
    private final DBConnection db;

    @Inject
    public OrdersAddController(DBConnection db) {
        this.db = db;
        this.customerService = new CustomerService(this.db);
        this.orderService= new OrderService(this.db);
        this.menuItemServices=new MenuItemServices(this.db);
        this.orderItemServices=new OrderItemServices(this.db);
    }
    @Override
    public void principalCargado() {
        orderItemid=orderItemServices.getAll().get().size()+1;
        orderAdd5.setText(LocalDateTime.now().toString());
        orderAdd5.setDisable(true);
        orderAdd6.getItems().addAll(1,2,3,4,5,6);
        ObservableList<String> observableList = FXCollections.observableArrayList();
        if (getPrincipalController().getActualUser().getId()>0){
            Customer customer=customerService.get(getPrincipalController().getActualUser().getId()).getOrNull();
            observableList.add(customer.getFirstName()+" "+customer.getLastName());
        }else{
        for (Customer customer:customerService.getAll().getOrNull()) {
            observableList.add(customer.getFirstName()+" "+customer.getLastName());
        }
        }
        ObservableList<String> observableList2 = FXCollections.observableArrayList();
        for (MenuItem menuItem:menuItemServices.getAll().getOrNull()) {
            observableList2.add(menuItem.getName());
        }
        orderAdd7.getItems().addAll(observableList);//cust
        orderAdd8.getItems().addAll(observableList2);//menuitems
        orderAdd1.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderAdd2.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMenuItem().getName()));
        orderAdd4.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderAdd3.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMenuItem().priceToString()));
    }
    public void removeItem(ActionEvent actionEvent) {
    orderAdd0.getItems().remove(orderAdd0.getSelectionModel().getSelectedItem());
        orderItemid=orderItemid-1;
        }
    public void addOrder(ActionEvent actionEvent) {
        int custid = 0;
        for (Customer customer:customerService.getAll().getOrNull()) {
            if ((customer.getFirstName()+" "+customer.getLastName()).equalsIgnoreCase(orderAdd7.getSelectionModel().getSelectedItem())){
            custid=customer.getId();
        }
        }
        int orderid=orderService.getAll().getOrNull().size()+1;
        Order o=new Order(orderid,LocalDateTime.now(),custid,orderAdd6.getSelectionModel().getSelectedItem());
        List<OrderItem> orderItems = new ArrayList<>(orderAdd0.getItems());
        o.setOrderItems(orderItems);
        orderService.save(o);
        getPrincipalController().sacarAlertInfo("Order has been added");
    }

    public void addItem(ActionEvent actionEvent) {
        int menuid = 0;
        String menuName=orderAdd8.getSelectionModel().getSelectedItem();
        int menuQuantity=orderAdd9.getValue();
        for (MenuItem menuItem:menuItemServices.getAll().getOrNull()) {
            if (menuItem.getName().equalsIgnoreCase(menuName)){
                menuid=menuItem.getId();
            }
        }
        int orderid=orderService.getAll().get().size()+1;
        orderAdd0.getItems().add(new OrderItem(orderItemid,orderid,menuItemServices.get(menuid).getOrNull(),menuQuantity));
        orderItemid=orderItemid+1;
    }
}

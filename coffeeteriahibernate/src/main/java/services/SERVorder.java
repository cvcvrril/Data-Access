package services;

import dao.db.DAOcustomerDB;
import dao.db.DAOorderDB;
import dao.imp.DAOorderXML;
import dao.spring.DAOcustomerSpring;
import dao.spring.DAOorderSpring;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Customer;
import model.Order;
import model.errors.ErrorCCustomer;
import model.errors.ErrorCOrder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SERVorder {
    private final DAOcustomerDB daOcustomerDB;
    private final DAOorderDB daOorderDB;
    private final DAOorderSpring daOorderSpring;
    private final DAOcustomerSpring daOcustomerSpring;
    private final DAOorderXML daOorderXML;


    @Inject
    public SERVorder(DAOcustomerDB daOcustomerDB, DAOorderDB daOorderDB, DAOorderSpring daOorderSpring, DAOcustomerSpring daOcustomerSpring, DAOorderXML daOorderXML) {
        this.daOcustomerDB = daOcustomerDB;
        this.daOorderDB = daOorderDB;
        this.daOorderSpring = daOorderSpring;
        this.daOcustomerSpring = daOcustomerSpring;
        this.daOorderXML = daOorderXML;
    }

    /*Métodos*/

    public List<Order> getAll() {
        return daOorderSpring.getAll().getOrNull();
    }

    public Either<ErrorCOrder, Order> getOrder(int i) {
            return daOorderSpring.get(i);
    }

    public Either<ErrorCOrder, List<Order>> getOrders(int i){
        return daOorderDB.getOrders(i);
    }

    public Either<ErrorCOrder, Integer> add(Order order) {
        return daOorderDB.add(order);
    }
    public Either<ErrorCOrder, Integer> updateOrder(Order o) {
        return daOorderDB.update(o);
    }

    public Either<ErrorCOrder, Integer> delOrder(int i) {
       return daOorderDB.delete(i);
    }

    public Either<ErrorCOrder, Integer> save(List<Order> orderList){
        return daOorderXML.saveOrderToXML(orderList);
    }

    public List<Order> getOrdersByDate(LocalDate selectedDate) {
        List<Order> allOrders = daOorderDB.getAll().getOrNull();
        List<Order> filteredOrders = new ArrayList<>();
        if (selectedDate != null) {
            for (Order order : allOrders) {
                if (order.getOrDate() != null && order.getOrDate().isEqual(selectedDate.atStartOfDay())) {
                    filteredOrders.add(order);
                }
            }
        }
        return filteredOrders;
    }

    public List<Order> getOrdersByCustomer(int selectedCustomerId) {
        List<Order> allOrders = daOorderDB.getAll().getOrNull();
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getIdCo() == selectedCustomerId) {
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }

    public List<Integer> getCustomerIDs() {
        Either<ErrorCCustomer, List<Customer>> result = daOcustomerSpring.getAll();
        if (result.isRight()) {
            List<Customer> customers = result.get();
            return customers.stream().map(Customer::getIdC).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}

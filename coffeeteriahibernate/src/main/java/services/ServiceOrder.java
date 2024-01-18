package services;

import dao.db.DaoCustomerDb;
import dao.db.DaoOrderDb;
import dao.hibernate.DaoOrderHibernate;
import dao.imp.DaoOrderXML;
import dao.spring.DaoCustomerSpring;
import dao.spring.DaoOrderSpring;
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

public class ServiceOrder {
    private final DaoCustomerDb daOcustomerDB;
    private final DaoOrderDb daOorderDB;
    private final DaoOrderHibernate daoOrderHibernate;
    private final DaoOrderSpring daOorderSpring;
    private final DaoCustomerSpring daOcustomerSpring;
    private final DaoOrderXML daOorderXML;


    @Inject
    public ServiceOrder(DaoCustomerDb daOcustomerDB, DaoOrderDb daOorderDB, DaoOrderHibernate daoOrderHibernate, DaoOrderSpring daOorderSpring, DaoCustomerSpring daOcustomerSpring, DaoOrderXML daOorderXML) {
        this.daOcustomerDB = daOcustomerDB;
        this.daOorderDB = daOorderDB;
        this.daoOrderHibernate = daoOrderHibernate;
        this.daOorderSpring = daOorderSpring;
        this.daOcustomerSpring = daOcustomerSpring;
        this.daOorderXML = daOorderXML;
    }

    /*Métodos*/

    public List<Order> getAll() {
        //return daOorderSpring.getAll().getOrNull();
        return daoOrderHibernate.getAll().getOrNull();
    }

    public Either<ErrorCOrder, Order> getOrder(int i) {
            //return daOorderSpring.get(i);
        return daoOrderHibernate.get(i);
    }

    public Either<ErrorCOrder, List<Order>> getOrders(int i){
        //return daOorderDB.getOrders(i);
        return daoOrderHibernate.getListOrdersById(i);
    }

    public Either<ErrorCOrder, Integer> add(Order order) {
        return daOorderDB.add(order);
    }
    public Either<ErrorCOrder, Integer> updateOrder(Order o) {
        //return daOorderDB.update(o);
        return daoOrderHibernate.update(o);
    }

    public Either<ErrorCOrder, Integer> delOrder(int i) {
       //return daOorderDB.delete(i);
        return daoOrderHibernate.delete(i);
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

package services;

import dao.implJDBC.DBConnection;
import dao.implJDBC.OrdersDAOdb;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import common.Error;
import model.Order;
import model.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private OrdersDAOdb dao;
    private final DBConnection db;
    @Inject
    public OrderService(DBConnection db) {
        this.db = db;
        this.dao = new OrdersDAOdb(this.db);
    }
    public Either<Error, List<Order>> getAll() {
        return dao.getAll();
    }

    public Either<Error, Order> get(int id) {
        return dao.get(id);
    }
    public Either<Error, List<Order>> getAll(int id) {return dao.getAll(id);}
    public Either<Error, Integer> save(Order o) {
        return dao.save(o);
    }
    public Either<Error, Integer> update(Order o) {
        return dao.update(o);
    }
    public Either<Error, Integer> delete(Order o, boolean conf) {
        return dao.delete(o,conf);
    }
    public List<Order> getCustomerID(int custID){
        List<Order> listaClientIDs=dao.getAll().getOrNull();
        List<Order> listaClientIDespecifico=new ArrayList<>();
        for (Order order: listaClientIDs) {
            if (order.getCustomerID()==custID){
                    listaClientIDespecifico.add(order);
            }
        }
        return listaClientIDespecifico;
    }
    public List<Order> getDate(LocalDateTime localDateTime){
        List<Order> listaDateSpecific=dao.getAll().getOrNull();
        List<Order> listaClientDateespecifico=new ArrayList<>();
        for (Order order: listaDateSpecific) {
            if (order.getDateTime().toString().equalsIgnoreCase(localDateTime.toString())){
                listaClientDateespecifico.add(order);
            }
        }
        return listaClientDateespecifico;
    }
}

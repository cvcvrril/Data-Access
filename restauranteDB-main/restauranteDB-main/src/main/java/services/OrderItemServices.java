package services;

import common.Error;
import dao.implJDBC.DBConnection;
import dao.implJDBC.OrderItemDAOdb;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.OrderItem;

import java.util.List;

public class OrderItemServices {
    private final OrderItemDAOdb orderItemDAO;
    private final DBConnection db;
    @Inject
    public OrderItemServices(DBConnection db) {
        this.db = db;
        this.orderItemDAO = new OrderItemDAOdb(this.db);
    }
    public Either<Error,List<OrderItem>> getAll(){return orderItemDAO.getAll();}

    public Either<Error, List<OrderItem>> getAll(int orderId) {
        return orderItemDAO.getAll(orderId);
    }

    public Either<Error, OrderItem> get(int orderID) {
        return orderItemDAO.get(orderID);
    }
    public Either<Error,Integer> deleteByOrder(int orderid){return orderItemDAO.deleteByOrder(orderid);}
    public Either<Error,Integer> insertByOrder(List<OrderItem> orderItemList, int orderID){return orderItemDAO.insertByOrder(orderItemList, orderID);}

    public Either<Error, Integer> update(OrderItem orderItem) {
        return orderItemDAO.update(orderItem);
    }
}

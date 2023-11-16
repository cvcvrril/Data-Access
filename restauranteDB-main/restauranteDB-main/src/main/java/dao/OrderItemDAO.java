package dao;

import common.Error;
import io.vavr.control.Either;
import model.OrderItem;

import java.util.List;

public interface OrderItemDAO {
    Either<Error,List<OrderItem>> getAll();
    Either<Error, List<OrderItem>> getAll(int orderID);
    Either<Error, OrderItem> get(int orderID);
    Either<Error,Integer> deleteByOrder(int orderid);
    Either<Error,Integer> insertByOrder(List<OrderItem> orderItemList, int orderID);
    Either<Error, Integer> update(OrderItem orderItem);
}

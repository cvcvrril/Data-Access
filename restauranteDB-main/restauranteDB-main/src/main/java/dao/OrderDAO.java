package dao;

import io.vavr.control.Either;
import common.Error;
import model.Order;
import model.OrderItem;

import java.util.List;

public interface OrderDAO {
    Either<Error, List<Order>> getAll();
    Either<Error, List<Order>> getAll(int id);

    Either<Error, Order> get(int id);

    Either<Error, Integer> save(Order c);

    Either<Error, Integer> update(Order c);

    Either<Error, Integer> delete(Order c, boolean conf);
}

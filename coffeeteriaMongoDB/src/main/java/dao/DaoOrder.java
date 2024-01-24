package dao;

import model.Order;
import io.vavr.control.Either;
import model.errors.ErrorCOrder;

import java.util.List;

public interface DaoOrder {

    Either<ErrorCOrder, List<Order>> getAll();

    Either<ErrorCOrder, Order> get(int id);

    Either<ErrorCOrder, Integer> add(Order t);

    Either<ErrorCOrder, Integer> update(Order t);

    Either<ErrorCOrder, Integer> delete(Order t);


}

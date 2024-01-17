package services;

import dao.db.DaoOrderItemDb;
import dao.spring.DaoOrderItemSpring;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.OrderItem;
import model.errors.ErrorCOrderItem;

import java.util.List;

public class ServiceOrderItem {
    private final DaoOrderItemDb daOorderItemDB;
    private final DaoOrderItemSpring daOorderItemSpring;

    @Inject
    public ServiceOrderItem(DaoOrderItemDb daOorderItemDB, DaoOrderItemSpring daOorderItemSpring) {
        this.daOorderItemDB = daOorderItemDB;
        this.daOorderItemSpring = daOorderItemSpring;
    }

    public Either<ErrorCOrderItem, List<OrderItem>> get(int i) {
        return daOorderItemSpring.get(i);
    }

    public Either<ErrorCOrderItem, List<OrderItem>> getAll() {
        return daOorderItemSpring.getAll();
    }

    public Either<ErrorCOrderItem, Integer> add(List<OrderItem> orderItemList, int id) {
        return daOorderItemDB.add(orderItemList, id);
    }

    public Either<ErrorCOrderItem, Integer> delete(int id) {
        return daOorderItemDB.delete(id);
    }

}

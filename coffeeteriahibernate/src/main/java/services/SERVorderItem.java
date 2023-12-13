package services;

import dao.db.DAOorderItemDB;
import dao.spring.DAOorderItemSpring;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.OrderItem;
import model.errors.ErrorCOrderItem;

import java.util.List;

public class SERVorderItem {
    private final DAOorderItemDB daOorderItemDB;
    private final DAOorderItemSpring daOorderItemSpring;

    @Inject
    public SERVorderItem(DAOorderItemDB daOorderItemDB, DAOorderItemSpring daOorderItemSpring) {
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

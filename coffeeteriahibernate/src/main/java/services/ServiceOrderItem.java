package services;

import dao.db.DaoOrderItemDb;
import dao.hibernate.DaoOrderItemHibernate;
import dao.spring.DaoOrderItemSpring;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.OrderItem;
import model.errors.ErrorCOrderItem;

import java.util.List;

public class ServiceOrderItem {
    private final DaoOrderItemDb daOorderItemDB;
    private final DaoOrderItemSpring daOorderItemSpring;
    private final DaoOrderItemHibernate daoOrderItemHibernate;

    @Inject
    public ServiceOrderItem(DaoOrderItemDb daOorderItemDB, DaoOrderItemSpring daOorderItemSpring, DaoOrderItemHibernate daoOrderItemHibernate) {
        this.daOorderItemDB = daOorderItemDB;
        this.daOorderItemSpring = daOorderItemSpring;
        this.daoOrderItemHibernate = daoOrderItemHibernate;
    }

    public Either<ErrorCOrderItem, List<OrderItem>> get(int i) {
        return daOorderItemSpring.get(i);
    }

    public Either<ErrorCOrderItem, List<OrderItem>> getAll() {
        //return daOorderItemSpring.getAll();
        return daoOrderItemHibernate.getAll();
    }

    public Either<ErrorCOrderItem, Integer> add(List<OrderItem> orderItemList, int id) {
        return daOorderItemDB.add(orderItemList, id);
    }

    public Either<ErrorCOrderItem, Integer> delete(int id) {
        return daOorderItemDB.delete(id);
    }

}

package dao.hibernate;


import dao.connection.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import model.OrderItem;
import model.errors.ErrorCOrderItem;

import java.util.List;

@Log4j2
public class DaoOrderItemHibernate {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoOrderItemHibernate(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<ErrorCOrderItem, List<OrderItem>> getAll(){
        Either<ErrorCOrderItem, List<OrderItem>> res;
        List<OrderItem> orderItemList;
        em = jpaUtil.getEntityManager();
        try {
            orderItemList = em.
                    createNamedQuery("HQL_GET_ALL_ORDER_ITEMS", OrderItem.class)
                    .getResultList();
            res = Either.right(orderItemList);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrderItem(e.getMessage(), 0));
        }finally {
            if (em != null) em.close();
        }
        return res;
    }

    public Either<ErrorCOrderItem, List<OrderItem>> get(int id){
        Either<ErrorCOrderItem, List<OrderItem>> res;
        List<OrderItem> orderItemList;
        em = jpaUtil.getEntityManager();
        try {
            orderItemList = em.createNamedQuery("SELECT_ORDERITEMS_ID", OrderItem.class)
                    .setParameter("id", id)
                    .getResultList();
            res = Either.right(orderItemList);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrderItem(e.getMessage(), 0));
        }finally {
            if (em != null) em.close();
        }
        return res;
    }

}

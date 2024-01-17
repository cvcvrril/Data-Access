package dao.hibernate;

import dao.connection.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import model.Order;
import model.errors.ErrorCOrder;

import java.util.List;

@Log4j2
public class DaoOrderHibernate {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoOrderHibernate(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<ErrorCOrder, List<Order>> getAll() {
        Either<ErrorCOrder, List<Order>> res;
        List<Order> orderList;
        em = jpaUtil.getEntityManager();
        try {
            orderList = em
                    .createNamedQuery("HQL_GET_ALL_ORDERS", Order.class)
                    .getResultList();
            res = Either.right(orderList);
        }catch (PersistenceException e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        }finally {
            if (em != null) em.close();
        }
        return res;
    }

    public Either<ErrorCOrder, Order> get(int id){
        Either<ErrorCOrder, Order> res;
        Order order;
        em = jpaUtil.getEntityManager();
        try {
            order = em.find(Order.class, id);
            res = Either.right(order);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        }finally {
            if (em != null) em.close();
        }
        return res;
    }
}

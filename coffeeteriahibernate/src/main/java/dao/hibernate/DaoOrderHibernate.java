package dao.hibernate;

import dao.connection.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import model.Order;
import model.errors.ErrorCOrder;

import java.util.Collections;
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
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        } finally {
            if (em != null) em.close();
        }
        return res;
    }

    public Either<ErrorCOrder, Order> get(int id) {
        Either<ErrorCOrder, Order> res;
        Order order;
        em = jpaUtil.getEntityManager();
        try {
            order = em.find(Order.class, id);
            res = Either.right(order);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        } finally {
            if (em != null) em.close();
        }
        return res;
    }

    //TODO: ARREGLAR ESTO -> Salta al Either.left en vez de continuar por el right (y no salta excepción)

    public Either<ErrorCOrder, List<Order>> getListOrdersById(int id) {
        Either<ErrorCOrder, List<Order>> res;
        List<Order> orderList;
        em = jpaUtil.getEntityManager();
        try {
            orderList = Collections.singletonList(em.find(Order.class, id));
            res = Either.right(orderList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        } finally {
            if (em != null) em.close();
        }
        return res;
    }

    //TODO: Meter bucle para añadir los orderItems dentro de Order

    public Either<ErrorCOrder, Integer> add(Order newOrder){
        Either<ErrorCOrder, Integer> res;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.persist(newOrder);
            tx.commit();
            res = Either.right(1);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        }finally {
            if (em != null) em.close();
        }
        return res;
    }

    //TODO -> REVISAR ERROR QUE LANZA HIBERNATE SOBRE UN TOSTRING EN ORDER (EN ORDERITEM LO EXCLUYO, ASÍ QUE NI IDEA) [ARREGLAR]

    public Either<ErrorCOrder, Integer> delete(Order order) {
        Either<ErrorCOrder, Integer> res;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            //Order orderToDelete = em.find(Order.class);
                em.remove(em.merge(order));
                tx.commit();
                res = Either.right(1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (tx.isActive()) tx.rollback();
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        } finally {
            if (em != null) em.close();
        }
        return res;
    }

    public Either<ErrorCOrder, Integer> update(Order updatedOrder){
        Either<ErrorCOrder, Integer> res;
        int conf;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.merge(updatedOrder);
            tx.commit();
            conf = 1;
            res = Either.right(conf);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        }finally {
            if (em != null) em.close();
        }
        return res;

    }



}

package dao.hibernate;

import dao.connection.JPAUtil;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import model.Customer;
import model.errors.ErrorCCustomer;

import java.util.List;

@Log4j2
public class DaoCustomerHibernate {

    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoCustomerHibernate(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Either<ErrorCCustomer, List<Customer>> getAll() {
        Either<ErrorCCustomer, List<Customer>> res;
        List<Customer> customerList;
        em = jpaUtil.getEntityManager();
        try {
            customerList = em
                    .createNamedQuery("HQL_GET_ALL_CUSTOMERS", Customer.class)
                    .getResultList();
            res = Either.right(customerList);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCCustomer(e.getMessage(), 0));
        } finally {
            if (em != null) em.close();
        }
        return res;
    }

    public Either<ErrorCCustomer, Customer> get(int id) {
        Either<ErrorCCustomer, Customer> res;
        Customer customer;
        em = jpaUtil.getEntityManager();
        try {
            customer = em.find(Customer.class, id);
            res = Either.right(customer);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCCustomer(e.getMessage(), 0));
        } finally {
            if (em != null) em.close();
        }
        return res;
    }

    public Either<ErrorCCustomer, Integer> add(Customer newCustomer){
        Either<ErrorCCustomer, Integer> res = null;
        return res;
    }

    public Either<ErrorCCustomer, Integer> delete(int id){
        Either<ErrorCCustomer, Integer> res;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Customer customerToDelete = em.find(Customer.class, id);
            if (customerToDelete!= null){
                em.remove(em.merge(customerToDelete));
                tx.commit();
                res = Either.right(1);
            }else {
                res = Either.left(new ErrorCCustomer("No se encontró el customer", 0));
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCCustomer(e.getMessage(), 0));
        }
        finally {
            if (em != null)  em.close();
        }
        return res;
    }

    public Either<ErrorCCustomer, Integer> update(Customer updatedCustomer){
        Either<ErrorCCustomer, Integer> res;
        int conf;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.merge(updatedCustomer);
            tx.commit();
            conf = 1;
            res = Either.right(conf);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCCustomer(e.getMessage(), 0));
        }finally {
            if (em != null)  em.close();
        }
        return res;
    }

}

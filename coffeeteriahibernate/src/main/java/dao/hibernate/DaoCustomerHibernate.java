package dao.hibernate;

import dao.connection.JPAUtil;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
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

    public Customer get(int id) {
        Customer customer = null;
        em = jpaUtil.getEntityManager();
        try {
            customer = em.find(Customer.class, id);

            // Hibernate session uses get instead of find
            //Session session= em.unwrap(Session.class);
            //s = session.get(BasicSupplier.class,id);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (em != null) em.close();
        }
        return customer;
    }

}

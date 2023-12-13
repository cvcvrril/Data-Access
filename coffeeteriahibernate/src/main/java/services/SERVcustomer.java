package services;

import dao.db.DAOcustomerDB;
import dao.hibernate.DaoCustomerHibernate;
import dao.imp.DAOorderXML;
import dao.spring.DAOcustomerSpring;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Credential;
import model.Customer;
import model.errors.ErrorCCustomer;

import java.util.List;

public class SERVcustomer {

    private final DAOcustomerDB daOcustomerDB;
    private final DAOcustomerSpring daOcustomerSpring;
    private final DAOorderXML daOorderXML;
    private final SERVorder serVorder;
    private final DaoCustomerHibernate daoCustomerHibernate;

    @Inject
    public SERVcustomer(DAOcustomerDB daOcustomerDB, DAOcustomerSpring daOcustomerSpring, DAOorderXML daOorderXML, SERVorder serVorder, DaoCustomerHibernate daoCustomerHibernate) {
        this.daOcustomerDB = daOcustomerDB;
        this.daOcustomerSpring = daOcustomerSpring;
        this.daOorderXML = daOorderXML;
        this.serVorder = serVorder;
        this.daoCustomerHibernate = daoCustomerHibernate;
    }

    public Either<ErrorCCustomer, List<Customer>> getAll() {
         //return daOcustomerSpring.getAll();
        return daoCustomerHibernate.getAll();
    }

    public Either<ErrorCCustomer, Customer> get(int id) {
        //return daOcustomerSpring.get(id);
        return daoCustomerHibernate.get(id);
    }

    public Either<ErrorCCustomer, Integer> delete(int i, boolean conf) {
        Either<ErrorCCustomer, Customer> res = daOcustomerDB.get(i);
        if (res.isRight()) {
            return daOcustomerSpring.delete(i, conf);
        } else {
            return Either.left(res.getLeft());
        }
    }

    public Either<ErrorCCustomer, Integer> update(Customer customer) {
        return daOcustomerSpring.update(customer);
    }

    public Either<ErrorCCustomer, Integer> add(Customer customer, Credential credential) {
        return daOcustomerSpring.add(customer, credential);
    }
}

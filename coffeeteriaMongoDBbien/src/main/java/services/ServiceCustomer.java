package services;

import dao.db.DaoCustomerDb;
import dao.hibernate.DaoCustomerHibernate;
import dao.imp.DaoOrderXML;
import dao.mongo.DaoMongoCustomer;
import dao.spring.DaoCustomerSpring;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Credential;
import model.Customer;
import model.errors.ErrorCCustomer;

import java.util.List;

public class ServiceCustomer {

    private final DaoCustomerDb daOcustomerDB;
    private final DaoCustomerSpring daOcustomerSpring;
    private final DaoOrderXML daOorderXML;
    private final ServiceOrder serviceOrder;
    private final DaoCustomerHibernate daoCustomerHibernate;
    private final DaoMongoCustomer daoMongoCustomer;

    @Inject
    public ServiceCustomer(DaoCustomerDb daOcustomerDB, DaoCustomerSpring daOcustomerSpring, DaoOrderXML daOorderXML, ServiceOrder serviceOrder, DaoCustomerHibernate daoCustomerHibernate, DaoMongoCustomer daoMongoCustomer) {
        this.daOcustomerDB = daOcustomerDB;
        this.daOcustomerSpring = daOcustomerSpring;
        this.daOorderXML = daOorderXML;
        this.serviceOrder = serviceOrder;
        this.daoCustomerHibernate = daoCustomerHibernate;
        this.daoMongoCustomer = daoMongoCustomer;
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
        Either<ErrorCCustomer, Customer> res = daoCustomerHibernate.get(i);
        if (res.isRight()) {
            //return daOcustomerSpring.delete(i, conf);
            return daoCustomerHibernate.delete(i, conf);
        } else {
            return Either.left(res.getLeft());
        }
    }

    public Either<ErrorCCustomer, Integer> update(Customer customer) {
        //return daOcustomerSpring.update(customer);
        return daoCustomerHibernate.update(customer);
    }

    public Either<ErrorCCustomer, Integer> add(Customer customer, Credential credential) {
        //return daOcustomerSpring.add(customer, credential);
        return daoCustomerHibernate.add(customer,credential);
    }
}

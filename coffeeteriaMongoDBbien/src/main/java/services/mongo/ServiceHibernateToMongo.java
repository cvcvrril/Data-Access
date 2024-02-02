package services.mongo;

import dao.hibernate.DaoCredentialHibernate;
import dao.hibernate.DaoCustomerHibernate;
import dao.hibernate.DaoOrderHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Credential;
import model.Customer;
import model.errors.ErrorCObject;

import java.util.List;

@Log4j2
public class ServiceHibernateToMongo {

    //TODO: Montar métodos de conversión

    private final DaoOrderHibernate daoOrderHibernate;
    private final DaoCredentialHibernate daoCredentialHibernate;
    private final DaoCustomerHibernate daoCustomerHibernate;

    @Inject
    public ServiceHibernateToMongo(DaoOrderHibernate daoOrderHibernate, DaoCredentialHibernate daoCredentialHibernate, DaoCustomerHibernate daoCustomerHibernate) {
        this.daoOrderHibernate = daoOrderHibernate;
        this.daoCredentialHibernate = daoCredentialHibernate;
        this.daoCustomerHibernate = daoCustomerHibernate;
    }

    public Either<ErrorCObject, Integer> transferHibernateToMongo(){
        Either<ErrorCObject, Integer> res;
        List<Credential> credentialListToConvert;
        List<Customer> customerListToConvert;
        try {

        }catch (Exception e){


        }

        credentialListToConvert = daoCredentialHibernate.getAll().get();
        return null;
    }

}

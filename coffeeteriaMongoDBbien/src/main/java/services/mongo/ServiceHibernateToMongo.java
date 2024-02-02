package services.mongo;

import dao.converters.CredentialConverter;
import dao.hibernate.DaoCredentialHibernate;
import dao.hibernate.DaoCustomerHibernate;
import dao.hibernate.DaoOrderHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.*;
import model.errors.ErrorCObject;

import java.util.List;

@Log4j2
public class ServiceHibernateToMongo {

    //TODO: Montar métodos de conversión

    private final DaoOrderHibernate daoOrderHibernate;
    private final DaoCredentialHibernate daoCredentialHibernate;
    private final DaoCustomerHibernate daoCustomerHibernate;

    private final CredentialConverter credentialConverter;
    @Inject
    public ServiceHibernateToMongo(DaoOrderHibernate daoOrderHibernate, DaoCredentialHibernate daoCredentialHibernate, DaoCustomerHibernate daoCustomerHibernate, CredentialConverter credentialConverter) {
        this.daoOrderHibernate = daoOrderHibernate;
        this.daoCredentialHibernate = daoCredentialHibernate;
        this.daoCustomerHibernate = daoCustomerHibernate;
        this.credentialConverter = credentialConverter;
    }

    public Either<ErrorCObject, Integer> transferAllHibernateToMongo(){
        Either<ErrorCObject, Integer> res;
        List<Credential> credentialListToConvert;
        List<Customer> customerListToConvert;
        List<Order> orderListToConvert;
        List<OrderItem> orderItemListToConvert;
        List<MenuItem> menuItemListToConvert;
        try {
            credentialListToConvert = daoCredentialHibernate.getAll().get();


        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return null;
    }


    public Either<ErrorCObject, Integer> transferCredentialToMongo(){
        Either<ErrorCObject, Integer> res;
        List<Credential> credentialListToConvert;
        try {
            credentialListToConvert = daoCredentialHibernate.getAll().get();
            if (credentialConverter.fromHibernateToMongoCredential(credentialListToConvert).isRight()){

            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return null;
    }

}

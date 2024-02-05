package services.mongo;

import dao.converters.CredentialConverter;
import dao.converters.CustomerConverter;
import dao.converters.MenuItemConverter;
import dao.hibernate.*;
import dao.mongo.DaoMongoCredential;
import dao.mongo.DaoMongoCustomer;
import dao.mongo.DaoMongoMenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.*;
import model.errors.ErrorCObject;
import model.mongo.CredentialMongo;
import model.mongo.CustomerMongo;

import java.util.List;

@Log4j2
public class ServiceHibernateToMongo {

    //TODO: Revisar cómo monto los métodos (tengo que mirar para dónde tiro los ErrorCObject)

    private final DaoOrderHibernate daoOrderHibernate;
    private final DaoOrderItemHibernate daoOrderItemHibernate;
    private final DaoCredentialHibernate daoCredentialHibernate;
    private final DaoCustomerHibernate daoCustomerHibernate;
    private final DaoMenuItemHibernate daoMenuItemHibernate;

    private final CredentialConverter credentialConverter;
    private final MenuItemConverter menuItemConverter;
    private final CustomerConverter customerConverter;

    private final DaoMongoCredential daoMongoCredential;
    private final DaoMongoCustomer daoMongoCustomer;
    private final DaoMongoMenuItem daoMongoMenuItem;

    @Inject
    public ServiceHibernateToMongo(DaoOrderHibernate daoOrderHibernate, DaoOrderItemHibernate daoOrderItemHibernate, DaoCredentialHibernate daoCredentialHibernate, DaoCustomerHibernate daoCustomerHibernate, DaoMenuItemHibernate daoMenuItemHibernate, CredentialConverter credentialConverter, MenuItemConverter menuItemConverter, CustomerConverter customerConverter, DaoMongoCredential daoMongoCredential, DaoMongoCustomer daoMongoCustomer, DaoMongoMenuItem daoMongoMenuItem) {
        this.daoOrderHibernate = daoOrderHibernate;
        this.daoOrderItemHibernate = daoOrderItemHibernate;
        this.daoCredentialHibernate = daoCredentialHibernate;
        this.daoCustomerHibernate = daoCustomerHibernate;
        this.daoMenuItemHibernate = daoMenuItemHibernate;
        this.credentialConverter = credentialConverter;
        this.menuItemConverter = menuItemConverter;
        this.customerConverter = customerConverter;
        this.daoMongoCredential = daoMongoCredential;
        this.daoMongoCustomer = daoMongoCustomer;
        this.daoMongoMenuItem = daoMongoMenuItem;
    }

    public Either<ErrorCObject, Integer> transferAllHibernateToMongo() {
        Either<ErrorCObject, Integer> res = null;
        try {
            if (transferCredentialToMongo().isLeft()) {
                res = Either.left(new ErrorCObject("No se pudo convertir la lista de Credenciales a Mongo", 0));
            }
            if (transferMenuItemToMongo().isLeft()) {
                res = Either.left(new ErrorCObject("No se pudo convertir la lista de MenuItems a Mongo", 0));
            }
            if (transferCustomerToMongo().isLeft()) {
                res = Either.left(new ErrorCObject("No se pudo convertir la lista de Customers a Mongo", 0));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }


    public Either<ErrorCObject, Integer> transferCredentialToMongo() {
        Either<ErrorCObject, Integer> res;
        List<Credential> credentialListToConvert;
        try {
            credentialListToConvert = daoCredentialHibernate.getAll().get();
            List<CredentialMongo> credentialMongoList = credentialConverter.fromHibernateToMongoCredential(credentialListToConvert).get();
            if (credentialMongoList != null) {
                if (daoMongoCredential.save(credentialMongoList).isRight()) {
                    res = Either.right(1);
                } else {
                    res = Either.left(new ErrorCObject("Hubo un problema al pasar los objetos al documento", 0));
                }
            } else {
                res = Either.left(new ErrorCObject("Hubo un problema al convertir los objetos", 0));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, Integer> transferCustomerToMongo() {
        Either<ErrorCObject, Integer> res;
        List<Customer> customerListToConvert;
        List<Order> orderListToConvert;
        //List<OrderItem> orderItemListToConvert;
        try {
            customerListToConvert = daoCustomerHibernate.getAll().get();
            orderListToConvert = daoOrderHibernate.getAll().get();
            //orderItemListToConvert = daoOrderItemHibernate.getAll().get();
            List<CustomerMongo> customerMongoList= customerConverter.fromHibernateToMongoCustomer(customerListToConvert, orderListToConvert).get();
            if (customerMongoList != null) {
                if (daoMongoCustomer.save(customerMongoList).isRight()){
                    res = Either.right(1);
                } else {
                    res = Either.left(new ErrorCObject("Hubo un problema al pasar los objetos al documento", 0));
                }
            }else {
                res = Either.left(new ErrorCObject("Hubo un problema al convertir los objetos", 0));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }

        return res;
    }


    public Either<ErrorCObject, Integer> transferMenuItemToMongo() {
        Either<ErrorCObject, Integer> res;
        List<MenuItem> menuItemListToConvert;
        try {
            menuItemListToConvert = daoMenuItemHibernate.getAll().get();
            if (menuItemConverter.fromHibernateToMongoMenuItem(menuItemListToConvert).isRight()) {
                res = Either.right(1);
            } else {
                res = Either.left(new ErrorCObject("Hubo un problema al convertir los objetos", 0));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }



}

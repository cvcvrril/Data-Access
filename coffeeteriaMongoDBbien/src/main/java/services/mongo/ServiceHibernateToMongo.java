package services.mongo;

import dao.converters.CredentialConverter;
import dao.converters.MenuItemConverter;
import dao.hibernate.DaoCredentialHibernate;
import dao.hibernate.DaoCustomerHibernate;
import dao.hibernate.DaoMenuItemHibernate;
import dao.hibernate.DaoOrderHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.*;
import model.errors.ErrorCObject;

import java.util.List;

@Log4j2
public class ServiceHibernateToMongo {

    //TODO: Revisar cómo monto los métodos (tengo que mirar para dónde tiro los ErrorCObject)

    private final DaoOrderHibernate daoOrderHibernate;
    private final DaoCredentialHibernate daoCredentialHibernate;
    private final DaoCustomerHibernate daoCustomerHibernate;
    private final DaoMenuItemHibernate daoMenuItemHibernate;

    private final CredentialConverter credentialConverter;
    private final MenuItemConverter menuItemConverter;

    @Inject
    public ServiceHibernateToMongo(DaoOrderHibernate daoOrderHibernate, DaoCredentialHibernate daoCredentialHibernate, DaoCustomerHibernate daoCustomerHibernate, DaoMenuItemHibernate daoMenuItemHibernate, CredentialConverter credentialConverter, MenuItemConverter menuItemConverter) {
        this.daoOrderHibernate = daoOrderHibernate;
        this.daoCredentialHibernate = daoCredentialHibernate;
        this.daoCustomerHibernate = daoCustomerHibernate;
        this.daoMenuItemHibernate = daoMenuItemHibernate;
        this.credentialConverter = credentialConverter;
        this.menuItemConverter = menuItemConverter;
    }

    public Either<ErrorCObject, Integer> transferAllHibernateToMongo() {
        Either<ErrorCObject, Integer> res;
        try {
            if (transferCredentialToMongo().isLeft()) {
                res = Either.left(new ErrorCObject("No se pudo convertir la lista de Credenciales a Mongo", 0));
            }
            if (transferMenuItemToMongo().isLeft()){
                res = Either.left(new ErrorCObject("No se pudo convertir la lista de MenuItems a Mongo", 0));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return null;
    }


    public Either<ErrorCObject, Integer> transferCredentialToMongo() {
        Either<ErrorCObject, Integer> res;
        List<Credential> credentialListToConvert;
        try {
            credentialListToConvert = daoCredentialHibernate.getAll().get();
            if (credentialConverter.fromHibernateToMongoCredential(credentialListToConvert).isRight()) {
                res  = Either.right(1);
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
            if (menuItemConverter.fromHibernateToMongoMenuItem(menuItemListToConvert).isRight()){
                res  = Either.right(1);
            } else {
                res = Either.left(new ErrorCObject("Hubo un problema al convertir los objetos", 0));
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, Integer> transferCustomerToMongo() {
        Either<ErrorCObject, Integer> res;
        List<Customer> customerListToConvert;
        List<Order> orderListToConvert;
        List<OrderItem> orderItemListToConvert;
        return null;
    }

}

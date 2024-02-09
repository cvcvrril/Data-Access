package services;

import dao.hibernate.DaoCustomerHibernate;
import dao.mongo.DaoMongoCustomer;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.errors.ErrorCObject;
import model.mongo.CredentialMongo;
import model.mongo.CustomerMongo;
import model.mongo.OrderMongo;
import org.bson.types.ObjectId;

import java.util.List;

public class ServiceCustomer {
    private final DaoMongoCustomer daoMongoCustomer;

    @Inject
    public ServiceCustomer(DaoMongoCustomer daoMongoCustomer) {
        this.daoMongoCustomer = daoMongoCustomer;
    }

    public Either<ErrorCObject, List<CustomerMongo>> getAll() {
        return daoMongoCustomer.getAllCustomers();
    }

    public Either<ErrorCObject, List<OrderMongo>> getAllOrders() {
        return daoMongoCustomer.getAllOrders();
    }

    public Either<ErrorCObject, CustomerMongo> get(String first_name, String second_name) {
        return daoMongoCustomer.getCustomer(first_name, second_name);
    }

    public Either<ErrorCObject, Integer> deleteCustomer(CustomerMongo customerMongo, boolean conf) {
        Either<ErrorCObject, CustomerMongo> res = daoMongoCustomer.getCustomer(customerMongo.getFirst_name(), customerMongo.getSecond_name());
        if (res.isRight()) {
            return daoMongoCustomer.deleteCustomer(customerMongo, conf);
        } else {
            return Either.left(res.getLeft());
        }
    }

    public Either<ErrorCObject, Integer>deleteOrder(OrderMongo orderMongo){
        return daoMongoCustomer.deleteOrder(orderMongo);
    }

    public Either<ErrorCObject, Integer> update(CustomerMongo customer) {
        return daoMongoCustomer.updateCustomers(customer);
    }

    public Either<ErrorCObject, Integer> updateOrder(OrderMongo orderMongo){
        return daoMongoCustomer.updateOrder(orderMongo);
    }

    public Either<ErrorCObject, Integer> add(CustomerMongo customer, CredentialMongo credential) {
        ObjectId objectId = new ObjectId();
        credential.set_id(objectId);
        return daoMongoCustomer.addCustomers(customer, credential);
    }

    public Either<ErrorCObject, Integer> addOrder(OrderMongo orderMongo) {
        return daoMongoCustomer.addOrder(orderMongo);
    }
}

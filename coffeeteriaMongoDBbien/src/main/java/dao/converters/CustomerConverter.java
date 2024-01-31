package dao.converters;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.Customer;
import model.Order;
import model.OrderItem;
import model.errors.ErrorCObject;
import model.mongo.CustomerMongo;
import model.mongo.OrderItemMongo;
import model.mongo.OrderMongo;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CustomerConverter {

    public Either<ErrorCObject, CustomerMongo> fromHibernateToMongoCustomer(Customer customer) {
        Either<ErrorCObject, CustomerMongo> res;
        List<OrderMongo> orderMongoList;
        try {
            CustomerMongo customerMongoConverted = new CustomerMongo(
                    null,
                    customer.getFirstName(),
                    customer.getSecondName(),
                    customer.getEmailCus(),
                    customer.getPhoneNumber(),
                    customer.getDateBirth(),
                    new ArrayList<>()
            );

            res = Either.right(customerMongoConverted);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, OrderMongo> orderConverter(Order order) {
        Either<ErrorCObject, OrderMongo> res;
        try {
            OrderMongo orderMongo = new OrderMongo(order.getOrDate(), order.getIdTable(),orderItemConverter(order.getOrderItems()).get());
            res = Either.right(orderMongo);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, List<OrderItemMongo>> orderItemConverter(List<OrderItem> orderItemList) {
        Either<ErrorCObject, OrderItemMongo> res;
        try {
            OrderItemMongo orderItemMongoConverted;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return null;
    }

}

package dao.converters;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.Customer;
import model.Order;
import model.OrderItem;
import model.errors.ErrorCObject;

@Log4j2
public class CustomerConverter {

    public Either<ErrorCObject, Customer> fromHibernateToMongoCustomer(Customer customer){
        return null;
    }

    public Either<ErrorCObject, Order> orderConverter (){
        return null;
    }

    public Either<ErrorCObject, OrderItem> orderItemConverter(){
        return null;
    }

}

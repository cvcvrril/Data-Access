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

    public Either<ErrorCObject, CustomerMongo> fromHibernateToMongoCustomer(Customer customer, List<Order> orderList) {
        Either<ErrorCObject, CustomerMongo> res;
        List<OrderMongo> orderMongoList = orderConverter(orderList).get();
        try {
            CustomerMongo customerMongoConverted = new CustomerMongo(
                    null,
                    customer.getFirstName(),
                    customer.getSecondName(),
                    customer.getEmailCus(),
                    customer.getPhoneNumber(),
                    customer.getDateBirth(),
                    orderMongoList
            );

            res = Either.right(customerMongoConverted);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, List<OrderMongo>> orderConverter(List<Order> orderList) {
        Either<ErrorCObject,  List<OrderMongo>> res;
        List<OrderMongo> orderMongoList = new ArrayList<>();
        try {
            for (Order order: orderList){
                OrderMongo orderMongo = new OrderMongo(order.getOrDate(), order.getIdTable(),orderItemConverter(order.getOrderItems()).get());
                orderMongoList.add(orderMongo);
            }
            res = Either.right(orderMongoList);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, List<OrderItemMongo>> orderItemConverter(List<OrderItem> orderItemList) {
        Either<ErrorCObject, List<OrderItemMongo>> res;
        List<OrderItemMongo> orderItemMongoList = new ArrayList<>();
        try {
            for (OrderItem orderItem: orderItemList){
                OrderItemMongo orderItemMongo = new OrderItemMongo(orderItem.getMenuItemObject().getIdMItem(), orderItem.getQuantity());
                orderItemMongoList.add(orderItemMongo);
            }
            res = Either.right(orderItemMongoList);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

}

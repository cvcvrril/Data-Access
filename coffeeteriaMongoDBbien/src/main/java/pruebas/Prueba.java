package pruebas;

import dao.connection.JPAUtil;
import dao.hibernate.DaoCustomerHibernate;
import dao.mongo.DaoMongoCustomer;
import model.mongo.OrderMongo;
import services.ServiceCustomer;

import java.util.List;

public class Prueba {

    public static void main(String[] args) {
        JPAUtil jpaUtil = new JPAUtil();
        DaoMongoCustomer daoMongoCustomer = new DaoMongoCustomer();
        DaoCustomerHibernate daoOrderHibernate = new DaoCustomerHibernate(jpaUtil);
        ServiceCustomer serviceCustomer = new ServiceCustomer(daoMongoCustomer);

        List<OrderMongo> orderMongoList = serviceCustomer.getAllOrders().get();
        System.out.println(orderMongoList);
    }

}

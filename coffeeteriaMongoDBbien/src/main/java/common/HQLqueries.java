package common;

public class HQLqueries {

public static final String DELETE_ORDERITEMS_CONF = "delete from OrderItem oi where oi.order.id in (select id from Order o where o.idCo = :id)";
public static final String DELETE_ORDERS_CONF = "delete from Order o where o.idCo = :id";

    public static final String SELECT_ORDERS_ID = "from Order where id = :id";
}

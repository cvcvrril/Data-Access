package model;

import common.HQLqueries;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@NamedQueries({ @NamedQuery(name = "HQL_GET_ALL_ORDERS",
        query = "from Order "), @NamedQuery(name = "DELETE_ORDERS_CONF", query = HQLqueries.DELETE_ORDERS_CONF), @NamedQuery(name = "SELECT_ORDERS_ID", query = HQLqueries.SELECT_ORDERS_ID)  })
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id")
    private Integer idOrd;
    @Column(name = "order_date")
    private LocalDateTime orDate;
    @Column(name = "customer_id")
    private int idCo;
    @Column(name = "table_id")
    private int idTable;
    @OneToMany(cascade = CascadeType.REMOVE,  mappedBy = "order")
    private List<OrderItem> orderItems;



    //TODO: probar lo siguiente -> SELECT NAME FROM ORDERS O WHERE O.ORDERITEM.MENUITEM.NAME = "SALMON" [
    // 1. select idOrd from Order o inner join OrderItem oi where oi.orderId = o.id
    // 2. select nameMItem from Order o inner join OrderItem oi inner join MenuItem mi where oi.orderId= o.id and oi.menuItemObject.nameMItem = mi.nameMItem
    //TODO: probar lo siguiente -> SELECT NAME FROM ORDERS O WHERE O.ORDERITEM.MENUITEM.NAME = "SALMON" ]

}

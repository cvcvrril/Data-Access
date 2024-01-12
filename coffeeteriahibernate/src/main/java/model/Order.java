package model;

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
        query = "from Order ") })
public class Order {

    @Id
    @Column(name = "order_id")
    private Integer idOrd;
    @Column(name = "order_date")
    private LocalDateTime orDate;
    @Column(name = "customer_id")
    private int idCo;
    @Column(name = "table_id")
    private int idTable;

    @OneToMany(mappedBy = "orderId")
    private List<OrderItem> orderItems;



    //TODO: SELECT NAME FROM ORDERS O WHERE O.ORDERITEM.MENUITEM.NAME = "SALMON"
}

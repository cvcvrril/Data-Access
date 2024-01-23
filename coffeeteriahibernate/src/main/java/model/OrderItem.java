package model;

import common.HQLqueries;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
@ToString(exclude = {"order"})

@NamedQueries({ @NamedQuery(name = "HQL_GET_ALL_ORDER_ITEMS",
        query = "from OrderItem "), @NamedQuery(name = "DELETE_ORDERITEMS_CONF", query = HQLqueries.DELETE_ORDERITEMS_CONF) })

public class OrderItem {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_item_id")
    private int id;             //order_item_id
    //private int orderId;        //order_id
    //@Column(name = "menu_item_id")
    //private int menuItem;       //menu_item_id
    private int quantity;       //quantity
    @OneToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItemObject;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}

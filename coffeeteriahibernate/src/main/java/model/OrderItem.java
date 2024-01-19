package model;

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
@NamedQueries({ @NamedQuery(name = "HQL_GET_ALL_ORDER_ITEMS",
        query = "from OrderItem ") })
public class OrderItem {

    @Id
    @Column(name = "order_item_id")
    private int id;             //order_item_id
    private int orderId;        //order_id
    private int menuItem;       //menu_item_id
    private int quantity;       //quantity
    @OneToOne
    private MenuItem menuItemObject;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}

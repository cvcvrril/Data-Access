package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_items")
@NamedQueries({ @NamedQuery(name = "HQL_GET_ALL_MENU_ITEMS",
        query = "from MenuItem ") })
public class MenuItem {

    @Id
    @Column(name = "menu_item_id")
    private int idMItem;
    @Column(name = "name")
    private  String nameMItem;
    @Column(name = "description")
    private String descriptionMItem;
    @Column(name = "price")
    private double priceMItem;
}

package model.mongo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class OrderItemMongo {

    private MenuItemMongo menu_item;

}

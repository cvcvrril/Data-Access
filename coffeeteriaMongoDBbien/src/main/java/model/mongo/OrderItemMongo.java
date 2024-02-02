package model.mongo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class OrderItemMongo {

    private int menu_item_id;
    private int quantity;

}

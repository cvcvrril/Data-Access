package model.mongo;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class OrderMongo {

    private LocalDateTime order_date;
    private int table_id;
    private List<OrderItemMongo> order_items;

}

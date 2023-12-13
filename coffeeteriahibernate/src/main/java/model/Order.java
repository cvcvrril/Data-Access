package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {


    private Integer idOrd;
    private LocalDateTime orDate;
    private int idCo;
    private int idTable;
    private List<OrderItem> orderItems;
}

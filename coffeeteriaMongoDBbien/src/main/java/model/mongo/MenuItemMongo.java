package model.mongo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MenuItemMongo {

    private int _id;
    private String name;
    private double price;

}

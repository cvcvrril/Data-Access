package model.mongo;

import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerMongo {
    private ObjectId _id;
    private String first_name;
    private String second_name;
    private String email;
    private int phone;
    private LocalDate date_of_birth;
    private List<OrderMongo> orders;

}

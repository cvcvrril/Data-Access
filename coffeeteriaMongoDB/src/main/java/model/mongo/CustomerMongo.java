package model.mongo;

import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

public class CustomerMongo {
    private ObjectId _id;
    private String firstName;
    private String secondName;
    private String emailCus;
    private int phoneNumber;
    private LocalDate dateBirth;
    private List<OrderMongo> orderList;

}

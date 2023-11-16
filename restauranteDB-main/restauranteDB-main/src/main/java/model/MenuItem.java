package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuItem {
    private int id;
    private String name;
    private String description;
    private float price;

    public String priceToString(){
        return String.valueOf(price);
    }
}

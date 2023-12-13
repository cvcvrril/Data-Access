package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuItem {

    private int idMItem;
    private  String nameMItem;
    private String descriptionMItem;
    private double priceMItem;
}

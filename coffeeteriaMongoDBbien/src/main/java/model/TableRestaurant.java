package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TableRestaurant {

    private int idTable;
    private int numSeats;
}

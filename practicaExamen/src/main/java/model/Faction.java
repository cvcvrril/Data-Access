package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faction {
    private String name;
    private String contact;
    private String planet;
    private int numberControlledSystems;
    private LocalDate datePurchase;
}

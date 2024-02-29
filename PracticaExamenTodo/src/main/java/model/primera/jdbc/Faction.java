package model.primera.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faction {
    private String fname;
    private String contact;
    private String planet;
    private int number_controlled_systems;
    private LocalDate date_last_purchase;
    private List<Weapon> weaponList;
}

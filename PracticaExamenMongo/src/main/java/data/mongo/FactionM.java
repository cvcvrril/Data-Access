package data.mongo;


import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Cuando haya una relación 1-N, los objetos N van al objeto 1.
 * Ejemplo; facción (1) tiene muchas armas (N).
 * **/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FactionM {

    private String fname;
    private String contact;
    private String planet;
    private int number_controlled_systems;
    private LocalDate date_last_purchase;
    private List<WeaponFactionM> weapons;

}

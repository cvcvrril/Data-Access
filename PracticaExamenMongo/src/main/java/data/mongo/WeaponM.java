package data.mongo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WeaponM {

    private int id;
    private String wname;
    private double wprice;

}

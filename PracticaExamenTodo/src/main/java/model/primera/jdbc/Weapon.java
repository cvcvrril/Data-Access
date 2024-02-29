package model.primera.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weapon {
    private int id;
    private String wname;
    private double wprice;

    /**
     * Para objetos donde se autogenera el id, hay que montar un constructor donde no haya id
     * Si se intenta introducir un id 0, el objeto en la base de datos tendrá id 0
     * **/

    public Weapon(String wname, double wprice) {
        this.wname = wname;
        this.wprice = wprice;
    }
}

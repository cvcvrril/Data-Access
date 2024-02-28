package ui.primera;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.primera.jdbc.Weapon;
import service.primera.jdbc.ServiceWeaponJ;

public class Exercise3 {

    /**
     * (JDBC) Update the price of a weapon
     * **/

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiceWeaponJ service = container.select(ServiceWeaponJ.class).get();
        Weapon actualizadoWeapon = new Weapon(2, "Prueba", 50.40);
        Integer res = service.update(actualizadoWeapon).get();
        System.out.println(res);
    }

}

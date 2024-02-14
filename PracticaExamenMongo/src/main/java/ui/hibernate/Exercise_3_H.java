package ui.hibernate;

import data.hibernate.Weapon;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.hibernate.ServiceWeaponH;

public class Exercise_3_H {

    public static void main(String[] args) {
        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceWeaponH servicio = container.select(ServiceWeaponH.class).get();
        Weapon updatedWeapon = new Weapon(1, "Prueba", 6001);
        servicio.update(updatedWeapon);
    }
}

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
        System.out.println("3. Update the price of a weapon");
        Weapon updatedWeapon = new Weapon(1, "Prueba", 6002);
        if (servicio.update(updatedWeapon).isRight()) System.out.println("Weapon updated");
    }
}

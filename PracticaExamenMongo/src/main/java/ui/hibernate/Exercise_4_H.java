package ui.hibernate;

import data.hibernate.Weapon;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.hibernate.ServiceWeaponH;

import java.util.List;

public class Exercise_4_H {

    public static void main(String[] args) {
        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceWeaponH servicio = container.select(ServiceWeaponH.class).get();
        System.out.println("4. Select all factions, and all weapons");
        List<Weapon> weapons = servicio.getAll().get();
        System.out.println("Weapons: " + weapons);
        System.out.println("Factions: " + weapons);
    }

}

package ui.hibernate;

import data.hibernate.Weapon;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.hibernate.ServiceWeaponH;

import java.util.List;

public class Exercise_5_H {

    public static void main(String[] args) {
        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceWeaponH servicioWeapon = container.select(ServiceWeaponH.class).get();
        System.out.println("5. Select the name and the price of all the weapons of a faction.");
        List<Weapon> weapons = servicioWeapon.getByName("Empire").get();
        if (weapons.isEmpty()){
            System.out.println("La lista está vacía.");
        }else {
            System.out.println("Weapons: " +  weapons);
        }
    }
}

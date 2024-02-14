package ui.hibernate;

import data.hibernate.Faction;
import data.hibernate.Weapon;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.hibernate.ServiceFactionH;
import services.hibernate.ServiceWeaponH;

import java.util.List;

public class Exercise_4_H {

    public static void main(String[] args) {
        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceWeaponH servicioWeapon = container.select(ServiceWeaponH.class).get();
        ServiceFactionH servicioFaction = container.select(ServiceFactionH.class).get();
        System.out.println("4. Select all factions, and all weapons");
        List<Weapon> weapons = servicioWeapon.getAll().get();
        List<Faction> factions = servicioFaction.getAll().get();
        System.out.println("Weapons: " + weapons);
        System.out.println("Factions: " + factions);
    }

}

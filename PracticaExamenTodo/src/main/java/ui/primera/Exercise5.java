package ui.primera;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.primera.jdbc.Weapon;
import service.primera.jdbc.ServiceWeaponJ;

import java.util.List;

public class Exercise5 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiceWeaponJ service = container.select(ServiceWeaponJ.class).get();
        List<Weapon> weapons = service.getAllByNameFaction("Empire").getOrElseThrow(() -> new RuntimeException());
        System.out.println(weapons);
    }
}

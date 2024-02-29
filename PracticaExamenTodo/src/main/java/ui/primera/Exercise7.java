package ui.primera;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import service.primera.sping.ServiceWeaponS;

import java.time.LocalDate;

public class Exercise7 {

    /**
     * (Spring) Select the data of all weapons purchased by a faction from a given date.
     * **/

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiceWeaponS service = container.select(ServiceWeaponS.class).get();
        System.out.println(service.getAll(LocalDate.parse("1525-03-02")));
    }

}

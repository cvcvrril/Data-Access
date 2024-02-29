package ui.primera;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.primera.jdbc.Faction;
import service.primera.sping.ServiceFactionS;

public class Exercise9 {

    /**
     * (Spring) Update the name of a faction, a frequent task when the boss is killed and another takes
     * control.
     * **/

    public static void main(String[] args) {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiceFactionS service = container.select(ServiceFactionS.class).get();
        Faction selectedFaction = service.getByName("Prueba").get();
        selectedFaction.setContact("Mr. Reborn");
        System.out.println(service.update(selectedFaction));

    }

}

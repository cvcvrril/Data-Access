package ui.primera;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.primera.jdbc.Faction;
import service.primera.jdbc.ServiceFactionJ;

import java.util.List;

public class Exercise4 {

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiceFactionJ service = container.select(ServiceFactionJ.class).get();
        List<Faction> factions = service.getAll().getOrElseThrow(() -> new RuntimeException());
        System.out.println(factions);
    }

}

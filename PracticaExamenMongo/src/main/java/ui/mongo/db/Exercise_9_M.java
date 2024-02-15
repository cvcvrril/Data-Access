package ui.mongo.db;

import data.mongo.FactionM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.mongo.db.ServiceFactionM;

public class Exercise_9_M {

    public static void main(String[] args) {

        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceFactionM serviceFactionM = container.select(ServiceFactionM.class).get();
        FactionM factionM = serviceFactionM.get("New order").get();
        if (factionM != null){
            factionM.setFname("Pruebaaaaaaa");
            if (serviceFactionM.update(factionM).isRight()){
                System.out.println("Updated successfully");
            } else {
                System.out.println("There was a problem with the update");
            }
        }else {
            System.out.println("No se ha podido encontrar");
        }
    }

}

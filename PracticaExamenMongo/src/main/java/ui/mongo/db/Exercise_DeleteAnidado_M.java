package ui.mongo.db;

import data.mongo.FactionM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.mongo.db.ServiceFactionM;

public class Exercise_DeleteAnidado_M {

    public static void main(String[] args) {

        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceFactionM serviceFactionM = container.select(ServiceFactionM.class).get();
        FactionM factionM = serviceFactionM.get("Rebels").get();
        if (factionM != null){
            if (serviceFactionM.deleteWeaponFaction(factionM).isRight()){
                System.out.println("WeaponFaction deleted successfully");
            } else {
                System.out.println("There was a problem with the delete");
            }
        }else {
            System.out.println("No se ha podido encontrar");
        }


    }


}

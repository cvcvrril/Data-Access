package ui.mongo.db;

import data.mongo.FactionM;
import data.mongo.WeaponFactionM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.mongo.db.ServiceFactionM;

import java.util.List;

public class Exercise_AddAnidado_M {

    public static void main(String[] args) {

        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceFactionM serviceFactionM = container.select(ServiceFactionM.class).get();
        FactionM factionM = serviceFactionM.get("Rebels").get();
        if (factionM != null){
            List<WeaponFactionM> weaponFactionMList = factionM.getWeapons();
            WeaponFactionM newWeaponFactionM = new WeaponFactionM(3);
            weaponFactionMList.add(newWeaponFactionM);
            factionM.setWeapons(weaponFactionMList);
            if (serviceFactionM.addWeaponFaction(factionM).isRight()){
                System.out.println("WeaponFaction added successfully");
            } else {
                System.out.println("There was a problem with the delete");
            }
        }else {
            System.out.println("No se ha podido encontrar");
        }


    }


}

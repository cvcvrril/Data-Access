package ui.hibernate;

import data.hibernate.Spy;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.hibernate.ServiceSpyH;

public class Exercise_6_H {

    public static void main(String[] args) {

        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceSpyH serviceSpyH = container.select(ServiceSpyH.class).get();
        System.out.println("6. (0.75) Delete the data of a spy and his/her battles, so that if something happens to them, they can deny their existence.");
        Spy selectedSpy = serviceSpyH.get(1).get();
        if (serviceSpyH.delete(selectedSpy).isRight()){
            System.out.println("All the data of the spy has been erased.");
        } else {
            System.out.println("There was a problem.");
        }

    }

}

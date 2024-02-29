package ui.primera;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import service.primera.sping.ServiceBattleS;

public class Exercise8 {

    /**
     * (Spring) Select all battles reported by a spy, returning battle name and spy name.
     * **/

    public static void main(String[] args) {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiceBattleS service = container.select(ServiceBattleS.class).get();
        System.out.println(service.getAllByIdSpy(1));

    }

}

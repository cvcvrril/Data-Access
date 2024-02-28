package ui.primera;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.primera.jdbc.Spy;
import service.primera.jdbc.ServiceSpyJ;

public class Exercise6 {

    /**
     *  (JDBC) Delete the data of a spy and his/her battles, so that if something happens to them,
     * they can deny their existence.
     * **/

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiceSpyJ service = container.select(ServiceSpyJ.class).get();
        Spy selectedSpy = service.get(1).get();
        System.out.println(selectedSpy);
        System.out.println(service.delete(selectedSpy));

    }
}

package ui.primera;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import service.primera.xml.ServiceFactionXML;

public class Exercise1 {

    /**
     * (XML) Load initial data in the database from XML, a code that was used in the time of the Great
     * Creator, when there was only one Light. You can find the XML file within the exam documents.
     * **/

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiceFactionXML service = container.select(ServiceFactionXML.class).get();
        System.out.println(service.read());

    }

}

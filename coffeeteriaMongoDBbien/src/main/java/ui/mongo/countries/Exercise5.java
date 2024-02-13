package ui.mongo.countries;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.mongo.aggregations.ServiceCountries;

public class Exercise5 {

    public static void main(String[] args) {
        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceCountries serviceCountries =  container.select(ServiceCountries.class).get();

        System.out.println("5. Identify regions with the highest number of landlocked countries");
        System.out.println("");

        System.out.println(serviceCountries.exercise5());


    }
}

package ui.mongo.countries;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.mongo.aggregations.ServiceCountries;

public class Exercise4 {

    public static void main(String[] args) {
        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceCountries serviceCountries =  container.select(ServiceCountries.class).get();

        System.out.println("4. Identify countries with the most common bordering countries. Show the first 5 ones:");
        System.out.println("");

        System.out.println(serviceCountries.exercise4());


    }

}

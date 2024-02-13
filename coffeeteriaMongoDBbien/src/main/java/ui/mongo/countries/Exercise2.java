package ui.mongo.countries;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.mongo.aggregations.ServiceCountries;

public class Exercise2 {

    public static void main(String[] args) {
        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceCountries serviceCountries =  container.select(ServiceCountries.class).get();

        System.out.println("2. Find the currency of the first 4 countries with an area greater than 10000");
        System.out.println("");

        System.out.println(serviceCountries.exercise2());


    }
}

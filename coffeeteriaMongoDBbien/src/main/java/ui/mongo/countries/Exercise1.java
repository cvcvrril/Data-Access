package ui.mongo.countries;

import dao.aggregations.DaoAggregationsCountries;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.mongo.aggregations.ServiceCountries;
import services.mongo.aggregations.ServiceRestaurant;

public class Exercise1 {

    public static void main(String[] args) {
        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceCountries serviceCountries =  container.select(ServiceCountries.class).get();

        System.out.println("1. Find the translation to Spanish of the bigest country (area) which region is Europe, currency is EUR :");
        System.out.println("");

        System.out.println(serviceCountries.exercise1());
    }
}

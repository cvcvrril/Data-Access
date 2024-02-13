package ui.mongo.restaurant;

import dao.aggregations.DaoAggregationsRestaurant;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.mongo.aggregations.ServiceRestaurant;

public class Exercise_g {

    public static void main(String[] args) {
        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceRestaurant serviceRestaurant =  container.select(ServiceRestaurant.class).get();

        System.out.println("g. Show a list with the number of items of each type requested by a selected customer");
        System.out.println("");

        System.out.println(serviceRestaurant.exerciseG());

    }
}

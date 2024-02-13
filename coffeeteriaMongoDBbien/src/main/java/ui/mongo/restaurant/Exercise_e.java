package ui.mongo.restaurant;

import dao.aggregations.DaoAggregationsRestaurant;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.mongo.aggregations.ServiceRestaurant;

public class Exercise_e {

    public static void main(String[] args) {
        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceRestaurant serviceRestaurant =  container.select(ServiceRestaurant.class).get();

        System.out.println("e. Get the average number of items per order");
        System.out.println("");

        System.out.println(serviceRestaurant.exerciseE());

    }
}

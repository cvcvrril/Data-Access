package ui.mongo.restaurant;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.mongo.aggregations.ServiceRestaurant;

public class Exercise_l {

    public static void main(String[] args) {
        SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
        final SeContainer container = seContainerInitializer.initialize();
        ServiceRestaurant serviceRestaurant =  container.select(ServiceRestaurant.class).get();

        System.out.println("l. Get the name of the customer that has spent more money in the restaurant");
        System.out.println("");

        System.out.println(serviceRestaurant.exerciseL());

    }
}

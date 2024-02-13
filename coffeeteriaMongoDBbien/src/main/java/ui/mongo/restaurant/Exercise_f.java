package ui.mongo.restaurant;

import dao.aggregations.DaoAggregationsRestaurant;
import services.mongo.aggregations.ServiceRestaurant;

public class Exercise_f {

    public static void main(String[] args) {

        DaoAggregationsRestaurant daoAggregationsRestaurant = new DaoAggregationsRestaurant();
        ServiceRestaurant serviceRestaurant =  new ServiceRestaurant(daoAggregationsRestaurant);

        System.out.println("f. Get the item most requested");
        System.out.println("");

        System.out.println(serviceRestaurant.exerciseF());

    }
}

package ui.mongo.restaurant;

import dao.aggregations.DaoAggregationsRestaurant;
import services.mongo.aggregations.ServiceRestaurant;

public class Exercise_c {

    public static void main(String[] args) {

        DaoAggregationsRestaurant daoAggregationsRestaurant = new DaoAggregationsRestaurant();
        ServiceRestaurant serviceRestaurant =  new ServiceRestaurant(daoAggregationsRestaurant);

        System.out.println("c. Get the number of items of each order");
        System.out.println("");

        System.out.println(serviceRestaurant.exerciseC());

    }


}

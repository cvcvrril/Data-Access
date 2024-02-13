package ui.mongo.restaurant;

import dao.aggregations.DaoAggregationsRestaurant;
import services.mongo.aggregations.ServiceRestaurant;

public class Exercise_i {

    public static void main(String[] args) {

        DaoAggregationsRestaurant daoAggregationsRestaurant = new DaoAggregationsRestaurant();
        ServiceRestaurant serviceRestaurant =  new ServiceRestaurant(daoAggregationsRestaurant);

        System.out.println("i. Get the most requested table per customer");
        System.out.println("");

        System.out.println(serviceRestaurant.exerciseI());

    }
}

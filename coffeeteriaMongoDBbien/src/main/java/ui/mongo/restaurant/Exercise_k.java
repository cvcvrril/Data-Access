package ui.mongo.restaurant;

import dao.aggregations.DaoAggregationsRestaurant;
import services.mongo.aggregations.ServiceRestaurant;

public class Exercise_k {

    public static void main(String[] args) {

        DaoAggregationsRestaurant daoAggregationsRestaurant = new DaoAggregationsRestaurant();
        ServiceRestaurant serviceRestaurant =  new ServiceRestaurant(daoAggregationsRestaurant);

        System.out.println("k. Price paid for each order (Use $lookup)");
        System.out.println("");

        System.out.println(serviceRestaurant.exerciseK());

    }
}

package ui.mongo.restaurant;

import dao.aggregations.DaoAggregationsRestaurant;
import services.mongo.aggregations.ServiceRestaurant;

public class Exercise_d {

    public static void main(String[] args) {

        DaoAggregationsRestaurant daoAggregationsRestaurant = new DaoAggregationsRestaurant();
        ServiceRestaurant serviceRestaurant =  new ServiceRestaurant(daoAggregationsRestaurant);

        System.out.println("d. Get the name of the customers who have ordered steak");
        System.out.println("");

        System.out.println(serviceRestaurant.exerciseD());

    }

}

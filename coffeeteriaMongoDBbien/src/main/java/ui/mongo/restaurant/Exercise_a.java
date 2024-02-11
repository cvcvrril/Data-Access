package ui.mongo.restaurant;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.eq;

import dao.aggregations.DaoAggregationsRestaurant;
import org.bson.Document;
import services.mongo.aggregations.ServiceRestaurant;

import java.util.ArrayList;
import java.util.Arrays;

public class Exercise_a {

    public static void main(String[] args) {

        DaoAggregationsRestaurant daoAggregationsRestaurant = new DaoAggregationsRestaurant();
        ServiceRestaurant serviceRestaurant =  new ServiceRestaurant(daoAggregationsRestaurant);

        System.out.println("a. Get the name of the most expensive item");
        System.out.println("");

        System.out.println(serviceRestaurant.exerciseA());

    }

}

package ui.mongo.restaurant;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

public class Exercise_a {

    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
        MongoCollection<Document> collection = db.getCollection("menu_items");

        System.out.println("a. Get the name of the most expensive item");
        System.out.println("");

        collection.aggregate(Arrays.asList(
                sort(descending("price")),
                limit(1),
                project(fields(
                        excludeId(),
                        include("name")
                ))
        )).into(new ArrayList<>()).forEach(System.out::println);


    }

}

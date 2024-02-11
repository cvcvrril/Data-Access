package ui.mongo.countries;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

public class Exercise5 {

    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        MongoDatabase db = mongo.getDatabase("inesmartinez_countries");
        MongoCollection<Document> collection = db.getCollection("countries");

        System.out.println("5. Identify regions with the highest number of landlocked countries");
        System.out.println("");

        collection.aggregate(Arrays.asList(
                match(eq("landlocked", true)),
                group("$region", sum("number_countries", 1)),
                sort(descending("number_countries"))
        )).into(new ArrayList<>()).forEach(System.out::println);



    }
}

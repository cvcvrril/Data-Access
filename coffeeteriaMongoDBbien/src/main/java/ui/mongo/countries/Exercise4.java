package ui.mongo.countries;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Sorts.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

public class Exercise4 {

    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        MongoDatabase db = mongo.getDatabase("inesmartinez_countries");
        MongoCollection<Document> collection = db.getCollection("countries");

        System.out.println("4. Identify countries with the most common bordering countries. Show the first 5 ones:");
        System.out.println("");

        collection.aggregate(Arrays.asList(
                unwind("$borders"),
                group("$name.common", addToSet("common_borders", "$borders")),
                unwind("$common_borders"),
                group("$_id", sum("count", 1)),
                sort(descending("count")),
                limit(5)
        )).into(new ArrayList<>()).forEach(System.out::println);
    }
}

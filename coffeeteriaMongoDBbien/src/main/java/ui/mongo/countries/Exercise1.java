package ui.mongo.countries;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;

public class Exercise1 {

    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        MongoDatabase db = mongo.getDatabase("inesmartinez_countries");
        MongoCollection<Document> collection = db.getCollection("countries");

        System.out.println("1. Find the translation to Spanish of the bigest country (area) which region is Europe, currency is EUR :");
        System.out.println("");

        collection.aggregate(Arrays.asList(
                        match(eq("region", "Europe")),
                        match(eq("currency", "EUR")),
                        sort(descending("area")),
                        limit(1),
                        project(fields(excludeId(),
                                include("name.common"),
                                include("translations.spa.common")))
                ))
                .into(new ArrayList<>()).forEach(System.out::println);
    }

}

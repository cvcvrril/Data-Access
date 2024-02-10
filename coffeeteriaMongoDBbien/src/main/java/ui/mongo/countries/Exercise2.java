package ui.mongo.countries;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

public class Exercise2 {

    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        MongoDatabase db = mongo.getDatabase("inesmartinez_countries");
        MongoCollection<Document> collection = db.getCollection("countries");

        System.out.println("2. Find the currency of the first 4 countries with an area greater than 10000");
        System.out.println("");

        collection.aggregate(Arrays.asList(
                
                ))
                .into(new ArrayList<>()).forEach(System.out::println);

    }

}

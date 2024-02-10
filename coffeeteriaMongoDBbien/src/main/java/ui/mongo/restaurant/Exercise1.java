package ui.mongo.restaurant;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Exercise1 {

    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
        MongoCollection<Document> collection = db.getCollection("countries");

    }

}

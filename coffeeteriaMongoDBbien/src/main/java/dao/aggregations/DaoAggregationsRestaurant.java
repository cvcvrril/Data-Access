package dao.aggregations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.vavr.control.Either;
import model.errors.ErrorCObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.descending;

public class DaoAggregationsRestaurant {

    public Either<ErrorCObject, String> exerciseA(){
        Either<ErrorCObject, String> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> collection = db.getCollection("menu_items");

            List<Document> documents = collection.aggregate(Arrays.asList(
                    sort(descending("price")),
                    limit(1),
                    project(fields(
                            excludeId(),
                            include("name")
                    ))
            )).into(new ArrayList<>());

            List<String> jsonList = new ArrayList<>();
            for (Document doc : documents) {
                jsonList.add(doc.toJson());
            }
            String json = String.join(",", jsonList);
            res = Either.right("[" + json + "]");

        }catch (Exception e){
            res = Either.left(new ErrorCObject("There was an error", 0));
        }
        return res;
    }


}

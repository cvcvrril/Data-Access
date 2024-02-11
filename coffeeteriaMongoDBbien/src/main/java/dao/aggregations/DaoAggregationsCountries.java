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

public class DaoAggregationsCountries {

    public Either<ErrorCObject, String> exercise1(){
        Either<ErrorCObject, String> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_countries");
            MongoCollection<Document> collection = db.getCollection("countries");

            List<Document> documents = collection.aggregate(Arrays.asList(
                            match(eq("region", "Europe")),
                            match(eq("currency", "EUR")),
                            sort(descending("area")),
                            limit(1),
                            project(fields(excludeId(),
                                    include("name.common"),
                                    include("translations.spa.common")))
                    ))
                    .into(new ArrayList<>());
            String json = documents.get(0).toJson();
            res = Either.right(json);

        }catch (Exception e){
            res = Either.left(new ErrorCObject("There was an error", 0));
        }
        return res;
    }


    public Either<ErrorCObject, String> exercise2() {
        return null;
    }

    public Either<ErrorCObject, String> exercise3() {
        return null;
    }

    public Either<ErrorCObject, String> exercise4() {
        return null;
    }

    public Either<ErrorCObject, String> exercise5() {
        return null;
    }
}

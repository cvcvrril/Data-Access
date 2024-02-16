package dao.mongo.aggregations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import data.error.ErrorObject;
import io.vavr.control.Either;
import org.bson.Document;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DaoAggregations {

    /**[
     {
     $match: {
     date_last_purchase: "2023-12-01"
     }
     },
     {
     $unwind: {
     path: "$weapons"
     }
     },
     {
     $lookup: {
     from: "weapons",
     localField: "weapons.id_weapon",
     foreignField: "id",
     as: "weapons"
     }
     },
     {
     $project: {
     _id: 0,
     weapons: 1
     }
     },
     {
     $project: {
     orderId: "$weapons.id",
     name: "$weapons.wname"
     }
     }
     ]**/

    public Either<ErrorObject,String> exercise7(){
        Either<ErrorObject, String> res;
        try (MongoClient mongo = MongoClients.create("mongodb://root:root@localhost:27017")) {
            MongoDatabase db = mongo.getDatabase("practica_mongo");
            MongoCollection<Document> collection = db.getCollection("factions");

            List<Document> documents = collection.aggregate(Arrays.asList(
                    match(eq("date_last_purchase", "2023-12-01")),
                    unwind("$weapons"),
                    lookup("weapons", "weapons.id_weapon", "id", "weapons"),
                    project(fields(excludeId(), include("weapons")))

            )).into(new ArrayList<>());
            List<String> jsonList = new ArrayList<>();
            for (Document doc : documents) {
                jsonList.add(doc.toJson());
            }
            String json = String.join(",", jsonList);
            res = Either.right("[" + json + "]");
        }catch (Exception e){
            res = Either.left(new ErrorObject(e.getMessage(), 0, LocalDateTime.now()));
        }
        return res;
    }

}

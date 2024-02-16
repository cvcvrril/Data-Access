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

    /**
     * [
     *   {
     *     $match: {
     *       date_last_purchase: "1591-01-19",
     *     },
     *   },
     *   {
     *     $unwind: {
     *       path: "$weapons",
     *     },
     *   },
     *   {
     *     $lookup: {
     *       from: "weapons",
     *       localField: "weapons.id_weapon",
     *       foreignField: "id",
     *       as: "weapons",
     *     },
     *   },
     *   {
     *     $project: {
     *       _id: 0,
     *       orderId: {
     *         $first: "$weapons.id",
     *       },
     *       name: {
     *         $first: "$weapons.wname",
     *       },
     *     },
     *   },
     * ]
     **/

    /**
     * Para usar el first, preferiblemente en los groups: no sé cómo usarlo en los project.
     * **/

    /**
     * En caso de que haya una query que no pueda o no sepa hacr en java, exportar la query a Java y dejarlo comentado.
     * **/


    public Either<ErrorObject, List<Document>> exercise7() {
        Either<ErrorObject, List<Document>> res;
        try (MongoClient mongo = MongoClients.create("mongodb://root:root@localhost:27017")) {
            MongoDatabase db = mongo.getDatabase("practica_mongo");
            MongoCollection<Document> collection = db.getCollection("factions");

            List<Document> documents = collection.aggregate(Arrays.asList(
                    match(eq("date_last_purchase", "1591-01-19")),
                    unwind("$weapons"),
                    lookup("weapons", "weapons.id_weapon", "id", "weapons"),
                    project(fields(excludeId(), include("weapons")))
                    //group(excludeId(), first("name", "$weapons.wname"))
            )).into(new ArrayList<>());

            res = Either.right(documents);
        } catch (Exception e) {
            res = Either.left(new ErrorObject(e.getMessage(), 0, LocalDateTime.now()));
        }
        return res;
    }

}

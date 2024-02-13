package dao.aggregations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.vavr.control.Either;
import model.errors.ErrorCObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.and;
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

    public Either<ErrorCObject, String> exerciseB(){
        Either<ErrorCObject, String> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> collection = db.getCollection("customers");

            List<Document> documents = collection.aggregate(Arrays.asList(
                    match(eq("first_name", "Lucia")),
                    project(fields(
                            excludeId(),
                            include("first_name"),
                            include("second_name"),
                            include("orders")
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

    public Either<ErrorCObject, String> exerciseC(){
        Either<ErrorCObject, String> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> collection = db.getCollection("customers");

            List<Document> documents = collection.aggregate(Arrays.asList(
                    unwind("$orders"),
                    project(fields(
                            include("orders"),
                            new Document("num_items", new Document("$size", "$orders.order_items"))
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

    public Either<ErrorCObject, String> exerciseD(){
        Either<ErrorCObject, String> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> collection = db.getCollection("customers");

            List<Document> documents = collection.aggregate(Arrays.asList(
                    unwind("$orders"),
                    unwind("$orders.order_items"),
                    lookup("menu_items", "orders.order_items.menu_item_id", "_id", "menu_item"),
                    unwind("$menu_item"),
                    match(Filters.eq("menu_item.name", "New York Strip Steak")),
                    group(and(eq("first_name", "$first_name"), eq("second_name", "$second_name")))
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

    public Either<ErrorCObject, String> exerciseE(){
        Either<ErrorCObject, String> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> collection = db.getCollection("customers");

            List<Document> documents = collection.aggregate(Arrays.asList(
                    unwind("$orders"),
                    group("$_id",
                            avg("avg_items_per_order", "$orders.order_items")),
                    group(null,
                            avg("overall_avg_items_per_order", "$avg_items_per_order")),
                    project(fields(excludeId(),
                            include("overall_avg_items_per_order")))
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

    public Either<ErrorCObject, String> exerciseF(){
        Either<ErrorCObject, String> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> collection = db.getCollection("customers");

            List<Document> documents = collection.aggregate(Arrays.asList(
                    unwind("$orders"),
                    unwind("$orders.order_items"),
                    group("$orders.order_items.menu_item_id",
                            sum("count", "$orders.order_items.quantity")),
                    sort(descending("count")),
                    limit(1),
                    lookup("menu_items", "orders.order_items.menu_item_id", "_id", "menu_item"),
                    project(fields(excludeId(),
                            include("menu_items.name", "count")))
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

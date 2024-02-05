package dao.mongo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import common.LocalDateTimeTypeAdapter;
import common.LocalDateTypeAdapter;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.errors.ErrorCObject;
import model.mongo.CustomerMongo;
import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class DaoMongoCustomer {

    public Either<ErrorCObject, Integer> save(List<CustomerMongo> customerMongoList) {
        Either<ErrorCObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("customers");
            for (CustomerMongo customerMongo : customerMongoList) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                        .create();

                String customerMongoJson = gson.toJson(customerMongo);
                Document document = Document.parse(customerMongoJson);
                est.insertOne(document);
            }
            res = Either.right(1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, Integer> delete(CustomerMongo customerMongo){
        Either<ErrorCObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("customers");
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                    .create();
            String customerMongoJson = gson.toJson(customerMongo);
            Document document = Document.parse(customerMongoJson);
            est.deleteOne(document);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return null;
    }

}

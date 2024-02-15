package dao.mongo.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import config.adapters.LocalDateTimeTypeAdapter;
import config.adapters.LocalDateTypeAdapter;
import config.adapters.ObjectIdTypeAdapter;
import data.error.ErrorObject;
import data.mongo.FactionM;
import data.mongo.WeaponM;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DaoFactionM {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(ObjectId.class, new ObjectIdTypeAdapter())
            .create();


    @Inject
    public DaoFactionM() {
    }

    public Either<ErrorObject, Integer> save(FactionM factionMongo) {
        Either<ErrorObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://root:root@localhost:27017")) {
            MongoDatabase db = mongo.getDatabase("practica_mongo");
            MongoCollection<Document> collection = db.getCollection("factions");

            Document doc = Document.parse(gson.toJson(factionMongo));
            collection.insertOne(doc);

            res = Either.right(1);
        } catch (Exception e) {
            res = Either.left(new ErrorObject("There was an error obtaining the objects", 0, LocalDateTime.now()));
        }
        return res;
    }

}

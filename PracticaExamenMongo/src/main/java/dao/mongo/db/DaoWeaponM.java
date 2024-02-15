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
import data.hibernate.Weapon;
import io.vavr.control.Either;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DaoWeaponM {



    /**
     * Se crea un objeto Gson general en cada uno de los Daos de Mongo para no repetir código.
     ***/


    /**
     * Como me da pereza montar un método para el try, copiar pegar en todos los métodos.
     * **/

    private Gson gson;

    public DaoWeaponM(Gson gson) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(ObjectId.class, new ObjectIdTypeAdapter())
                .create();
    }

    public Either<ErrorObject, Integer> save(Weapon weapon){
        Either<ErrorObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> collection = db.getCollection("customers");



            res = Either.right(1);
        }catch (Exception e){
            res = Either.left(new ErrorObject("There was an error obtaining the objects", 0, LocalDateTime.now()));
        }
        return res;
    }

}

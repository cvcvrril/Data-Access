package dao.mongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.errors.ErrorCObject;
import model.mongo.CredentialMongo;
import model.mongo.MenuItemMongo;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoMongoMenuItem {

    public Either<ErrorCObject, Integer> save(List<MenuItemMongo> menuItemMongoList){
        Either<ErrorCObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("menu_items");
            for (MenuItemMongo menuItemMongo: menuItemMongoList){
                String credentialMongoJson = new Gson().toJson(menuItemMongo);
                Document document = Document.parse(credentialMongoJson);
                est.insertOne(document);
            }
            res = Either.right(1);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, List<MenuItemMongo>> getAll(){
        Either<ErrorCObject, List<MenuItemMongo>> res;
        List<MenuItemMongo> menuItemMongoList = new ArrayList<>();
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("menu_items");
            List<Document> documents = est.find().into(new ArrayList<>());
            for (Document document : documents){
                menuItemMongoList.add(new Gson().fromJson(document.toJson(), MenuItemMongo.class));
            }
            res = Either.right(menuItemMongoList);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }


}

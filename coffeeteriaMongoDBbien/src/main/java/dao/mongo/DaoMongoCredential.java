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
import org.bson.Document;

import java.util.List;

@Log4j2
public class DaoMongoCredential {

    public Either<ErrorCObject, Integer> read(){
        Either<ErrorCObject, Integer> res;
        return null;
    }

    public Either<ErrorCObject, Integer> save(List<CredentialMongo> credentialMongoList){
        Either<ErrorCObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("credentials");
            for (CredentialMongo credentialMongo: credentialMongoList){
                String credentialMongoJson = new Gson().toJson(credentialMongo);
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

    //INFO: Esto no se usa, pero útil para tenerlo de referencia para customers

    public Either<ErrorCObject, Integer> delete(CredentialMongo credentialMongo){
        Either<ErrorCObject, Integer> res;
        try(MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("credentials");
            String credentialMongoJson = new Gson().toJson(credentialMongo);
            Document document = Document.parse(credentialMongoJson);
            est.deleteOne(document);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return null;
    }

    public Either<ErrorCObject, Integer> update(CredentialMongo credentialMongo){
        Either<ErrorCObject, Integer> res;
        try(MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("credentials");

        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return null;
    }

}

package dao.mongo.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import config.adapters.LocalDateTimeTypeAdapter;
import config.adapters.LocalDateTypeAdapter;
import config.adapters.ObjectIdTypeAdapter;
import data.error.ErrorObject;
import data.mongo.FactionM;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.pull;

public class DaoFactionM {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(ObjectId.class, new ObjectIdTypeAdapter())
            .create();


    @Inject
    public DaoFactionM() {
    }

    public Either<ErrorObject, FactionM> get(String name) {
        Either<ErrorObject, FactionM> res;
        try (MongoClient mongo = MongoClients.create("mongodb://root:root@localhost:27017")) {
            MongoDatabase db = mongo.getDatabase("practica_mongo");
            MongoCollection<Document> collection = db.getCollection("factions");

            Document result = collection.find(Filters.eq("fname", name)).first();

            if (result != null) {
                FactionM faction = gson.fromJson(result.toJson(), FactionM.class);
                res = Either.right(faction);
            } else {
                res = Either.left(new ErrorObject("Not found", 0, LocalDateTime.now()));
            }
        } catch (Exception e) {
            res = Either.left(new ErrorObject("Not found", 0, LocalDateTime.now()));
        }
        return res;
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

    /**
     * Vale, el update funciona si no uso la fecha.
     * Creo que es porque está mal formateada. Pero no debería.
     * En cualquier caso, se podría usar otro atributo único, pero en este caso he tirado del nombre del objeto.
     * **/


    public Either<ErrorObject, Integer> update(FactionM modifiedFactionM){
        Either<ErrorObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://root:root@localhost:27017")) {
            MongoDatabase db = mongo.getDatabase("practica_mongo");
            MongoCollection<Document> collection = db.getCollection("factions");

            String factionToJson = gson.toJson(modifiedFactionM);
            Document factionToDocument = Document.parse(factionToJson);

            Document filter = new Document("fname", modifiedFactionM.getFname());
            UpdateResult updateResult = collection.replaceOne(filter, factionToDocument);

            if (updateResult.getModifiedCount() > 0){
                res = Either.right(1);
            }else {
                res = Either.left(new ErrorObject("There was an error updating the object", 0, LocalDateTime.now()));
            }
        }catch (Exception e){
            res = Either.left(new ErrorObject(e.getMessage(), 0, LocalDateTime.now()));
        }
        return res;
    }


    /**
     * Lo que hay que hacer en esta operación anidada es tirar de la colección donde se encuentra el objeto que queremos editar.
     * Sacamos el documento al completo, y luego filtramos por algún atributo (en este caso filtramos por el nombre del objeto).
     * Sacamos un atributo del objeto que queremos eliminar (el id, porque el objeto no tiene nada más).
     * Fijarse también en el ejemplo de Fran.
     * **/

    public Either<ErrorObject, Integer> deleteWeaponFaction(FactionM deleteWeaponsFactionM){
        Either<ErrorObject, Integer> res;

        try (MongoClient mongo = MongoClients.create("mongodb://root:root@localhost:27017")) {
            MongoDatabase db = mongo.getDatabase("practica_mongo");
            MongoCollection<Document> collection = db.getCollection("factions");

            int idWeapon = deleteWeaponsFactionM.getWeapons().get(1).getId_weapon();

            Long rows = collection.updateOne(eq("fname", deleteWeaponsFactionM.getFname()), pull("weapons", eq("id_weapon", idWeapon))).getModifiedCount();

            res = Either.right(rows.intValue());
        }catch (Exception e){
            res = Either.left(new ErrorObject(e.getMessage(), 0, LocalDateTime.now()));
        }

        return res;
    }

    /**
     * Me añade de nuevo el objeto...?
     * A saber por qué.
     * Pero funciona a pachas.
     * **/

    public Either<ErrorObject, Integer> addWeaponFaction(FactionM addWeaponsFactionsM){
        Either<ErrorObject, Integer> res;

        try (MongoClient mongo = MongoClients.create("mongodb://root:root@localhost:27017")) {
            MongoDatabase db = mongo.getDatabase("practica_mongo");
            MongoCollection<Document> collection = db.getCollection("factions");

            Document query = new Document("fname", addWeaponsFactionsM.getFname());
            Document factionMDocument = collection.find(query).first();

            if (factionMDocument  != null){

                List<Document> weaponsFactionsDoc = (List<Document>) factionMDocument.get("weapons");

                String factionJson = gson.toJson(addWeaponsFactionsM);

                Document newWeaponFactionDocument = Document.parse(factionJson);

                weaponsFactionsDoc.add(newWeaponFactionDocument);

                collection.updateOne(query, new Document("$set", new Document("weapons", weaponsFactionsDoc)));

                res = Either.right(1);
            }else {
                res = Either.left(new ErrorObject("No se pudo encontrar la facción", 0, LocalDateTime.now()));
            }
        }catch (Exception e){
            res = Either.left(new ErrorObject(e.getMessage(), 0, LocalDateTime.now()));
        }
        return res;
    }

}

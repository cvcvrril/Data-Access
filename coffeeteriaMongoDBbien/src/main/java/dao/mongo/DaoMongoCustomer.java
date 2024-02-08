package dao.mongo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import common.LocalDateTimeTypeAdapter;
import common.LocalDateTypeAdapter;
import common.ObjectIdTypeAdapter;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.errors.ErrorCObject;
import model.mongo.CredentialMongo;
import model.mongo.CustomerMongo;
import model.mongo.OrderMongo;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Projections.*;

@Log4j2
public class DaoMongoCustomer {

    private Gson gson;

    public DaoMongoCustomer() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(ObjectId.class, new ObjectIdTypeAdapter())
                .create();
    }

    public Either<ErrorCObject, Integer> saveAll(List<CustomerMongo> customerMongoList) {
        Either<ErrorCObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("customers");
            for (CustomerMongo customerMongo : customerMongoList) {
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

    public Either<ErrorCObject, List<CustomerMongo>> getAllCustomers() {
        Either<ErrorCObject, List<CustomerMongo>> res;
        List<CustomerMongo> customerMongoList = new ArrayList<>();
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("customers");
            List<Document> documents = est.find().into(new ArrayList<>());
            for (Document document : documents) {
                customerMongoList.add(gson.fromJson(document.toJson(), CustomerMongo.class));
            }
            res = Either.right(customerMongoList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, List<OrderMongo>> getAllOrders() {
        Either<ErrorCObject, List<OrderMongo>> res;
        List<OrderMongo> orderMongoList = new ArrayList<>();
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("customers");
            List<Document> filtro = est.find()
                    .projection(fields(excludeId(), include("orders")))
                    .into(new ArrayList<>());
            for (Document document : filtro) {
                List<Document> documents = (List<Document>) document.get("orders");
                for (Document order : documents) {
                    orderMongoList.add(gson.fromJson(order.toJson(), OrderMongo.class));
                }
            }
            res = Either.right(orderMongoList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, CustomerMongo> getCustomer(String first_name, String second_name) {
        Either<ErrorCObject, CustomerMongo> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("customers");
            Document filtro = new Document("first_name", first_name).append("second_name", second_name);
            Document document = est.find(filtro).first();
            CustomerMongo customerMongo = gson.fromJson(document.toJson(), CustomerMongo.class);
            if (customerMongo != null) {
                res = Either.right(customerMongo);
            } else {
                res = Either.left(new ErrorCObject("No se pudo encontrar el objeto", 0));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, OrderMongo> getOrders() {
        Either<ErrorCObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("customers");
            List<Document> filtro = est.find()
                    .projection(fields(excludeId(), include("orders")))
                    .into(new ArrayList<>());

        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return null;
    }

    public Either<ErrorCObject, CustomerMongo> getCustomersByDate(OrderMongo orderMongo) {
        Either<ErrorCObject, CustomerMongo> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("customers");
            Document filtro = new Document("order_date", orderMongo.getOrder_date());
            Document document = est.find(filtro).first();
            CustomerMongo customerMongo = gson.fromJson(document.toJson(), CustomerMongo.class);
            if (customerMongo != null) {
                res = Either.right(customerMongo);
            } else {
                res = Either.left(new ErrorCObject("No se pudo encontrar el objeto", 0));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, Integer> addCustomers(CustomerMongo customerMongo, CredentialMongo credentialMongo) {
        Either<ErrorCObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> estCustomers = db.getCollection("customers");
            MongoCollection<Document> estCredentials = db.getCollection("credentials");
            String credentialMongoJson = gson.toJson(credentialMongo);
            Document documentCredential = Document.parse(credentialMongoJson);
            estCredentials.insertOne(documentCredential);

            ObjectId idCred = credentialMongo.get_id();
            customerMongo.set_id(idCred);

            String customerMongoJson = gson.toJson(customerMongo);
            Document documentCustomer = Document.parse(customerMongoJson);
            estCustomers.insertOne(documentCustomer);
            res = Either.right(1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, Integer> addOrder(OrderMongo orderMongo) {
        Either<ErrorCObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("customers");
            List<Document> filtro = est.find()
                    .projection(fields(excludeId(), include("orders")))
                    .into(new ArrayList<>());
            String orderMongoJson = gson.toJson(orderMongo);
            Document document = Document.parse(orderMongoJson);
            est.insertOne(document);
            res = Either.right(1);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, Integer> updateCustomers(CustomerMongo customerMongo) {
        Either<ErrorCObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("customers");
            Bson updates = Updates.combine(
                    Updates.set("first_name", customerMongo.getFirst_name()),
                    Updates.set("second_name", customerMongo.getSecond_name()),
                    Updates.set("email", customerMongo.getEmail()),
                    Updates.set("phone", customerMongo.getPhone()),
                    Updates.set("date_of_birth", customerMongo.getDate_of_birth()),
                    Updates.set("orders", customerMongo.getOrders())
            );
            String customerMongoJson = gson.toJson(customerMongo);
            Document document = Document.parse(customerMongoJson);
            est.findOneAndUpdate(document, updates);
            res = Either.right(1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, Integer> updateOrder(OrderMongo orderMongo) {
        Either<ErrorCObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("customers");
            Bson updates = Updates.combine(
                    Updates.set("order_date", orderMongo.getOrder_date()),
                    Updates.set("table_id", orderMongo.getTable_id()),
                    Updates.set("order_items", orderMongo.getOrder_items())
            );
            String customerMongoJson = gson.toJson(orderMongo);
            Document document = Document.parse(customerMongoJson);
            est.findOneAndUpdate(document, updates);
            res = Either.right(1);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, Integer> deleteCustomer(CustomerMongo customerMongo) {
        Either<ErrorCObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> estCustomers = db.getCollection("customers");
            MongoCollection<Document> estCredentials = db.getCollection("credentials");
            Document filtroCustomers = new Document("first_name", customerMongo.getFirst_name()).append("second_name", customerMongo.getSecond_name());
            Document filtroCredentials = new Document("_id", customerMongo.get_id());
            Document documentCustomers = estCustomers.find(filtroCustomers).first();
            Document documentCredentials = estCustomers.find(filtroCredentials).first();
            if (documentCustomers != null){
                CredentialMongo credentialMongoDelete = gson.fromJson(documentCustomers.toJson(), CredentialMongo.class);
                if (documentCredentials != null){
                    CustomerMongo customerMongoDelete = gson.fromJson(documentCustomers.toJson(), CustomerMongo.class);
                    if (credentialMongoDelete != null){
                        estCredentials.deleteOne(documentCredentials);
                        if (customerMongoDelete != null) {
                            estCustomers.deleteOne(documentCustomers);
                            res = Either.right(1);
                        } else {
                            res = Either.left(new ErrorCObject("No se pudo encontrar el objeto", 0));
                        }
                    }else {
                        res = Either.left(new ErrorCObject("No se pudo encontrar el objeto", 0));
                    }
                }else {
                    res = Either.left(new ErrorCObject("No se pudo encontrar el objeto", 0));
                }
            }else{
                res = Either.left(new ErrorCObject("No se pudo encontrar el objeto", 0));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCObject, Integer> deleteOrder(OrderMongo orderMongo) {
        Either<ErrorCObject, Integer> res;
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("inesmartinez_restaurant");
            MongoCollection<Document> est = db.getCollection("customers");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formDate = orderMongo.getOrder_date().format(dateTimeFormatter);
            Bson bsonfilter = Filters.elemMatch("orders", Filters.regex("order_date", formDate));
            Document order = est.find()
                    .projection(fields(excludeId(), include("orders")))
                    .filter(bsonfilter)
                    .first();
            if (order != null) {
                est.deleteOne(order);
                res = Either.right(1);
            } else {
                res = Either.left(new ErrorCObject("No se pudo encontrar el objeto", 0));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

}

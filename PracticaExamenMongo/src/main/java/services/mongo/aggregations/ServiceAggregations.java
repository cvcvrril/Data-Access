package services.mongo.aggregations;

import dao.mongo.aggregations.DaoAggregations;
import data.error.ErrorObject;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceAggregations {

    private final DaoAggregations daoAggregations;

    @Inject
    public ServiceAggregations(DaoAggregations daoAggregations) {
        this.daoAggregations = daoAggregations;
    }

    public Either<ErrorObject,List<String>> exercise7(){
        Either<ErrorObject,List<String>> res;
        try {
            List<String> s = new ArrayList<>();
            Either<ErrorObject,List<Document>> list = daoAggregations.exercise7();
            if (list.isRight()){
                list.get().stream()
                        .map(document -> "weapons: " + document.get("weapons").toString()).forEach(s::add);
                res = Either.right(s);
            }else {
                res = Either.left(new ErrorObject("Hubo un problema", 0, LocalDateTime.now()));
            }
        }catch (Exception e){
          res = Either.left(new ErrorObject(e.getMessage(), 0, LocalDateTime.now()));
        }
        return res;
    }

}

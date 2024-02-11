package services.mongo.aggregations;

import dao.aggregations.DaoAggregationsCountries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.errors.ErrorCObject;
import org.bson.Document;

public class ServiceCountries {

    private final DaoAggregationsCountries daoAggregationsCountries;

    @Inject
    public ServiceCountries(DaoAggregationsCountries daoAggregationsCountries) {
        this.daoAggregationsCountries = daoAggregationsCountries;
    }

    public Either<ErrorCObject, String> exercise1(){
        return daoAggregationsCountries.exercise1();
    }

    public Either<ErrorCObject, String> exercise2(){
        return daoAggregationsCountries.exercise2();
    }

    public Either<ErrorCObject, String> exercise3(){
        return daoAggregationsCountries.exercise3();
    }

    public Either<ErrorCObject, String> exercise4(){
        return daoAggregationsCountries.exercise4();
    }

    public Either<ErrorCObject, String> exercise5(){
        return daoAggregationsCountries.exercise5();
    }

}

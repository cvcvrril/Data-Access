package services.mongo.aggregations;

import dao.aggregations.DaoAggregationsRestaurant;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.errors.ErrorCObject;

public class ServiceRestaurant {

    private final DaoAggregationsRestaurant daoAggregationsRestaurant;

    @Inject
    public ServiceRestaurant(DaoAggregationsRestaurant daoAggregationsRestaurant) {
        this.daoAggregationsRestaurant = daoAggregationsRestaurant;
    }

    public Either<ErrorCObject, String> exerciseA(){
        return daoAggregationsRestaurant.exerciseA();
    }

    public Either<ErrorCObject, String> exerciseB(){
        return daoAggregationsRestaurant.exerciseB();
    }

    public Either<ErrorCObject, String> exerciseC(){
        return daoAggregationsRestaurant.exerciseC();
    }

    public Either<ErrorCObject, String> exerciseD(){
        return daoAggregationsRestaurant.exerciseA();
    }

    public Either<ErrorCObject, String> exerciseE(){
        return daoAggregationsRestaurant.exerciseA();
    }

    public Either<ErrorCObject, String> exerciseF(){
        return daoAggregationsRestaurant.exerciseA();
    }

    public Either<ErrorCObject, String> exerciseG(){
        return daoAggregationsRestaurant.exerciseA();
    }

    public Either<ErrorCObject, String> exerciseH(){
        return daoAggregationsRestaurant.exerciseA();
    }

    public Either<ErrorCObject, String> exerciseI(){
        return daoAggregationsRestaurant.exerciseA();
    }

    public Either<ErrorCObject, String> exerciseJ(){
        return daoAggregationsRestaurant.exerciseA();
    }

    public Either<ErrorCObject, String> exerciseK(){
        return daoAggregationsRestaurant.exerciseA();
    }

    public Either<ErrorCObject, String> exerciseL(){
        return daoAggregationsRestaurant.exerciseA();
    }

    public Either<ErrorCObject, String> exerciseM(){
        return daoAggregationsRestaurant.exerciseA();
    }
}

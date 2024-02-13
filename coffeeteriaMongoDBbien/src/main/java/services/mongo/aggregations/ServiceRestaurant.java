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
        return daoAggregationsRestaurant.exerciseD();
    }

    public Either<ErrorCObject, String> exerciseE(){
        return daoAggregationsRestaurant.exerciseE();
    }

    public Either<ErrorCObject, String> exerciseF(){
        return daoAggregationsRestaurant.exerciseF();
    }

    public Either<ErrorCObject, String> exerciseG(){
        return daoAggregationsRestaurant.exerciseG();
    }

    public Either<ErrorCObject, String> exerciseH(){
        return daoAggregationsRestaurant.exerciseH();
    }

    public Either<ErrorCObject, String> exerciseI(){
        return daoAggregationsRestaurant.exerciseI();
    }

    public Either<ErrorCObject, String> exerciseJ(){
        return daoAggregationsRestaurant.exerciseJ();
    }

    public Either<ErrorCObject, String> exerciseK(){
        return daoAggregationsRestaurant.exerciseK();
    }

    public Either<ErrorCObject, String> exerciseL(){
        return daoAggregationsRestaurant.exerciseL();
    }

    public Either<ErrorCObject, String> exerciseM(){
        return daoAggregationsRestaurant.exerciseM();
    }
}

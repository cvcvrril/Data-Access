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


}

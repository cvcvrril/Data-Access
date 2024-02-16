package services.mongo.aggregations;

import dao.mongo.aggregations.DaoAggregations;
import data.error.ErrorObject;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServiceAggregations {

    private final DaoAggregations daoAggregations;

    @Inject
    public ServiceAggregations(DaoAggregations daoAggregations) {
        this.daoAggregations = daoAggregations;
    }

    public Either<ErrorObject,String> exercise7(){
        return daoAggregations.exercise7();
    }

}

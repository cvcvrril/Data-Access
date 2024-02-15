package services.hibernate;

import dao.hibernate.DaoSpyH;
import data.error.ErrorObject;
import data.hibernate.Spy;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class ServiceSpyH {

    private final DaoSpyH daoSpyH;

    @Inject
    public ServiceSpyH(DaoSpyH daoSpyH) {
        this.daoSpyH = daoSpyH;
    }

    public Either<ErrorObject, Integer> delete(Spy spyDelete){
        return daoSpyH.delete(spyDelete);
    }

    public Either<ErrorObject, Spy> get(int id){
        return daoSpyH.get(id);
    }

}

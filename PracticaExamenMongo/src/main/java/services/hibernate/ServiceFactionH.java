package services.hibernate;

import dao.hibernate.DaoFactionH;
import data.error.ErrorObject;
import data.hibernate.Faction;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServiceFactionH {

    private final DaoFactionH daoFactionH;

    @Inject
    public ServiceFactionH(DaoFactionH daoFactionH) {
        this.daoFactionH = daoFactionH;
    }


    public Either<ErrorObject, List<Faction>> getAll() {
        return daoFactionH.getAll();
    }
}

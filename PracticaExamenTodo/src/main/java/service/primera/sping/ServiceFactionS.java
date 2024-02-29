package service.primera.sping;

import dao.primera.spring.DaoFactionS;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.error.ExamError;
import model.primera.jdbc.Faction;

public class ServiceFactionS {

    private final DaoFactionS daoFactionS;

    @Inject
    public ServiceFactionS(DaoFactionS daoFactionS) {
        this.daoFactionS = daoFactionS;
    }

    public Either<ExamError, Faction> getByName(String fname) {
        return daoFactionS.getByName(fname);
    }

    public Either<ExamError, Integer> update(Faction faction) {
        return daoFactionS.update(faction);
    }

}

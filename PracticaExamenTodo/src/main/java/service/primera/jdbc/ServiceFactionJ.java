package service.primera.jdbc;

import dao.primera.jdbc.DaoFactionJ;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.error.ExamError;
import model.primera.jdbc.Faction;

import java.util.List;

public class ServiceFactionJ {

    private final DaoFactionJ daoFactionJ;

    @Inject
    public ServiceFactionJ(DaoFactionJ daoFactionJ) {
        this.daoFactionJ = daoFactionJ;
    }

    public Either<ExamError, List<Faction>> getAll(){
        return daoFactionJ.getAll();
    }

}

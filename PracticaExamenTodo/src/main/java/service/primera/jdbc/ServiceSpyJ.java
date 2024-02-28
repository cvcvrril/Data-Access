package service.primera.jdbc;

import dao.primera.jdbc.DaoSpyJ;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.error.ExamError;
import model.primera.jdbc.Spy;

public class ServiceSpyJ {

    private final DaoSpyJ daoSpyJ;

    @Inject
    public ServiceSpyJ(DaoSpyJ daoSpyJ) {
        this.daoSpyJ = daoSpyJ;
    }

    public Either<ExamError, Spy> get(int id) {
        return daoSpyJ.get(id);
    }

    public Either<ExamError, Integer> delete(Spy deleteSpy) {
        return daoSpyJ.delete(deleteSpy);
    }

}

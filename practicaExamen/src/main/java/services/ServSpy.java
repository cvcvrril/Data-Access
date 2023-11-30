package services;

import domain.DaoSpy;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Spy;
import model.error.ErrorDb;
import java.util.List;
public class ServSpy {
    private final DaoSpy daoSpy;
    @Inject
    public ServSpy(DaoSpy daoSpy) {
        this.daoSpy = daoSpy;
    }
    public Either<ErrorDb, List<Spy>> getAll() {
        return daoSpy.getAll();
    }
}

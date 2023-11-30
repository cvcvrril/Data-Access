package domain;

import io.vavr.control.Either;
import model.Spy;
import model.error.ErrorDb;

import java.util.List;

public interface DaoSpy {

    Either<ErrorDb, List<Spy>> getAll();

}

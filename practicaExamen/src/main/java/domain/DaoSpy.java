package domain;

import io.vavr.control.Either;
import model.Spy;
import model.Weapon;
import model.error.ErrorDb;

import java.util.List;

public interface DaoSpy {

    Either<ErrorDb, List<Spy>> getAll();
    Either<ErrorDb, Spy> get(int id);
    Either<ErrorDb, Integer> add(Spy spy);
    Either<ErrorDb, Integer> update(Spy spy);
    Either<ErrorDb, Integer> delete(int id);

}

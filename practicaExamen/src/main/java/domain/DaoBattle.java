package domain;

import io.vavr.control.Either;
import model.Battle;
import model.Sale;
import model.error.ErrorDb;

import java.util.List;

public interface DaoBattle {

    Either<ErrorDb, List<Battle>> getAll();
    Either<ErrorDb, Sale>get(int id);
    Either<ErrorDb, Integer> add(Sale sale);
    Either<ErrorDb, Integer> update(Sale sale);
    Either<ErrorDb, Integer> delete(int id);

}

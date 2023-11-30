package domain;

import io.vavr.control.Either;
import model.Sale;
import model.Weapon;
import model.error.ErrorDb;

import java.util.List;

public interface DaoSale {

    Either<ErrorDb, List<Sale>> getAll();
    Either<ErrorDb, Sale>get(int id);
    Either<ErrorDb, Integer> add(Sale sale);
    Either<ErrorDb, Integer> update(Sale sale);
    Either<ErrorDb, Integer> delete(int id);

}

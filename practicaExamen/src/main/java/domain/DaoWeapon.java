package domain;

import io.vavr.control.Either;
import model.Weapon;
import model.error.ErrorDb;

import java.util.List;

public interface DaoWeapon {

    Either<ErrorDb, List<Weapon>>getAll();
    Either<ErrorDb, Weapon>get(int id);
    Either<ErrorDb, List<Weapon>> getByName(String name);
    Either<ErrorDb, Integer> add(Weapon newWeapon);
    Either<ErrorDb, Integer> update(Weapon updatedWeapon);
    Either<ErrorDb, Integer> delete(int id);

}

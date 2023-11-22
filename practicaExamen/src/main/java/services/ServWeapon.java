package services;

import io.vavr.control.Either;
import model.Weapon;
import model.error.ErrorDb;

import java.util.List;

public interface ServWeapon {

    Either<ErrorDb, List<Weapon>> getAll();
    Either<ErrorDb, Weapon> get(int id);


}

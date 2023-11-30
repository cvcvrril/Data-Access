package services;

import domain.DaoWeapon;
import io.vavr.control.Either;
import model.Weapon;
import model.error.ErrorDb;

import java.util.List;

public class ServWeapon{

    private final DaoWeapon daoWeapon;

    public ServWeapon(DaoWeapon daoWeapon) {
        this.daoWeapon = daoWeapon;
    }

    public Either<ErrorDb, List<Weapon>> getAll() {
        return daoWeapon.getAll();
    }

    public Either<ErrorDb, Weapon> get(int id) {
        return daoWeapon.get(id);
    }
}

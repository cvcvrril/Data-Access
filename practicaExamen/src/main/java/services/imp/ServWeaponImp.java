package services.imp;

import domain.DaoWeapon;
import io.vavr.control.Either;
import model.Weapon;
import model.error.ErrorDb;
import services.ServWeapon;

import java.util.List;

public class ServWeaponImp implements ServWeapon {

    private final DaoWeapon daoWeapon;

    public ServWeaponImp(DaoWeapon daoWeapon) {
        this.daoWeapon = daoWeapon;
    }

    @Override
    public Either<ErrorDb, List<Weapon>> getAll() {
        return daoWeapon.getAll();
    }

    @Override
    public Either<ErrorDb, Weapon> get(int id) {
        return daoWeapon.get(id);
    }
}

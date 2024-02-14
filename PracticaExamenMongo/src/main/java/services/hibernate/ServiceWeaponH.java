package services.hibernate;

import dao.hibernate.DaoWeaponH;
import data.error.ErrorObject;
import data.hibernate.Weapon;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class ServiceWeaponH {

    private final DaoWeaponH daoWeaponH;

    @Inject
    public ServiceWeaponH(DaoWeaponH daoWeaponH) {
        this.daoWeaponH = daoWeaponH;
    }

    public Either<ErrorObject, Integer> update(Weapon weaponUpdate){
        return daoWeaponH.update(weaponUpdate);
    }

}

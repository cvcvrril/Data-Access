package services.hibernate;

import dao.hibernate.DaoWeaponH;
import data.error.ErrorObject;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class ServiceWeaponH {

    private final DaoWeaponH daoWeaponH;

    @Inject
    public ServiceWeaponH(DaoWeaponH daoWeaponH) {
        this.daoWeaponH = daoWeaponH;
    }

    public Either<ErrorObject, Integer> update(){
        return daoWeaponH.update();
    }

}

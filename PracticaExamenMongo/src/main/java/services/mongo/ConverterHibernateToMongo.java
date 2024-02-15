package services.mongo;

import dao.hibernate.DaoWeaponH;
import data.error.ErrorObject;
import data.hibernate.Weapon;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

public class ConverterHibernateToMongo {


    private final DaoWeaponH daoWeaponH;

    @Inject
    public ConverterHibernateToMongo(DaoWeaponH daoWeaponH) {
        this.daoWeaponH = daoWeaponH;
    }

    public Either<ErrorObject, Boolean> convert(){
        Either<ErrorObject, Boolean> res;


        Either<ErrorObject, List<Weapon>> weapons = daoWeaponH.getAll();

        if (weapons.isRight()){

            List<Weapon> weaponList = weapons.get();

            for (Weapon weapon : weaponList){

            }

        }else {
            res = Either.left(new ErrorObject("There was a problem", 0, LocalDateTime.now()));
        }
        return null;
    }

}

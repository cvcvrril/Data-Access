package services.mongo;

import dao.hibernate.DaoWeaponH;
import dao.mongo.db.DaoWeaponM;
import data.error.ErrorObject;
import data.hibernate.Weapon;
import data.mongo.WeaponM;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

public class ConverterHibernateToMongo {


    private final DaoWeaponH daoWeaponH;
    private final DaoWeaponM daoWeaponM;

    @Inject
    public ConverterHibernateToMongo(DaoWeaponH daoWeaponH, DaoWeaponM daoWeaponM) {
        this.daoWeaponH = daoWeaponH;
        this.daoWeaponM = daoWeaponM;
    }

    public Either<ErrorObject, Boolean> convert(){
        Either<ErrorObject, Boolean> res;

        Either<ErrorObject, List<Weapon>> weapons = daoWeaponH.getAll();

        if (weapons.isRight()){

            List<Weapon> weaponList = weapons.get();

            for (Weapon weapon : weaponList){
                daoWeaponM.save(new WeaponM(weapon.getId(), weapon.getName(), weapon.getPrice()));
            }
            res = Either.right(true);
        }else {
            res = Either.left(new ErrorObject("There was a problem", 0, LocalDateTime.now()));
        }
        return res;
    }

}

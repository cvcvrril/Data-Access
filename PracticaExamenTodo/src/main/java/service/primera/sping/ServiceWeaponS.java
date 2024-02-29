package service.primera.sping;

import dao.primera.spring.DaoWeaponS;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.error.ExamError;
import model.primera.jdbc.Weapon;

import java.time.LocalDate;
import java.util.List;

public class ServiceWeaponS {

    private final DaoWeaponS daoWeaponS;

    @Inject
    public ServiceWeaponS(DaoWeaponS daoWeaponS) {
        this.daoWeaponS = daoWeaponS;
    }

    public Either<ExamError, List<Weapon>> getAll(LocalDate lastPurchase){
        return daoWeaponS.getAll(lastPurchase);
    }

}

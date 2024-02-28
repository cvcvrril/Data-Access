package service.primera.jdbc;

import dao.primera.jdbc.DaoWeaponJ;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.error.ExamError;
import model.primera.jdbc.Weapon;

import java.util.List;

public class ServiceWeaponJ {

    private final DaoWeaponJ daoWeaponJ;

    @Inject
    public ServiceWeaponJ(DaoWeaponJ daoWeaponJ) {
        this.daoWeaponJ = daoWeaponJ;
    }

    public Either<ExamError, List<Weapon>> getAllByNameFaction(String nameFaction){
        return daoWeaponJ.getAllByNameFaction(nameFaction);
    }


}

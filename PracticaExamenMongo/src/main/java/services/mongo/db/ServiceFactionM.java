package services.mongo.db;

import dao.mongo.db.DaoFactionM;
import data.error.ErrorObject;
import data.mongo.FactionM;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class ServiceFactionM {

    private final DaoFactionM daoFactionM;

    @Inject
    public ServiceFactionM(DaoFactionM daoFactionM) {
        this.daoFactionM = daoFactionM;
    }

    public Either<ErrorObject, Integer> update(FactionM modifiedfactionM){
        return daoFactionM.update(modifiedfactionM);
    }

    public Either<ErrorObject, FactionM> get(String name){
        return daoFactionM.get(name);
    }

    public Either<ErrorObject, Integer> deleteWeaponFaction(FactionM deleteFactionWeaponsM){
        return daoFactionM.deleteWeaponFaction(deleteFactionWeaponsM);
    }

    public Either<ErrorObject, Integer> addWeaponFaction(FactionM addWeaponsFactionsM){
        return daoFactionM.addWeaponFaction(addWeaponsFactionsM);
    }

}

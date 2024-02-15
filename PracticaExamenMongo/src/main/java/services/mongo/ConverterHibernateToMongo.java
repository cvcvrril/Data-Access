package services.mongo;

import dao.hibernate.DaoFactionH;
import dao.hibernate.DaoWeaponH;
import dao.hibernate.DaoWeaponsFactionsH;
import dao.mongo.db.DaoFactionM;
import dao.mongo.db.DaoWeaponM;
import data.error.ErrorObject;
import data.hibernate.Faction;
import data.hibernate.Weapon;
import data.hibernate.gen.WeaponsFactionsEntity;
import data.mongo.FactionM;
import data.mongo.WeaponFactionM;
import data.mongo.WeaponM;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConverterHibernateToMongo {


    private final DaoWeaponH daoWeaponH;
    private final DaoFactionH daoFactionH;
    private final DaoWeaponsFactionsH daoWeaponsFactionsH;
    private final DaoWeaponM daoWeaponM;
    private final DaoFactionM daoFactionM;


    @Inject
    public ConverterHibernateToMongo(DaoWeaponH daoWeaponH, DaoFactionH daoFactionH, DaoWeaponsFactionsH daoWeaponsFactionsH, DaoWeaponM daoWeaponM, DaoFactionM daoFactionM) {
        this.daoWeaponH = daoWeaponH;
        this.daoFactionH = daoFactionH;
        this.daoWeaponsFactionsH = daoWeaponsFactionsH;
        this.daoWeaponM = daoWeaponM;
        this.daoFactionM = daoFactionM;
    }

    public Either<ErrorObject, Boolean> convert(){
        Either<ErrorObject, Boolean> res;

        Either<ErrorObject, List<Weapon>> weapons = daoWeaponH.getAll();
        Either<ErrorObject, List<Faction>> factions = daoFactionH.getAll();

        if (weapons.isRight()){

            List<Weapon> weaponList = weapons.get();
            List<Faction> factionList = factions.get();

            for (Faction faction : factionList){

                List<WeaponFactionM> weaponFactionMList = new ArrayList<>();
                FactionM factionToMongo = new FactionM(faction.getName(), faction.getContact(), faction.getPlanet(), faction.getSystems(), faction.getDateLastPurchase(),weaponFactionMList);

                List<WeaponsFactionsEntity> weaponsFactionsEntityList = daoWeaponsFactionsH.getAllByFactionName(faction.getName()).get();

                if (!weaponsFactionsEntityList.isEmpty()){
                    for (WeaponsFactionsEntity weaponsFactionsEntity : weaponsFactionsEntityList){
                        weaponFactionMList.add(new WeaponFactionM(weaponsFactionsEntity.getIdWeapon()));
                    }
                }

                daoFactionM.save(factionToMongo);

            }

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

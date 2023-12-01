package ui.exercises;

import common.Configuration;
import common.DBConnection;
import domain.DaoFaction;
import domain.DaoWeapon;
import domain.imp.DaoFactionImp;
import domain.imp.DaoWeaponImp;
import model.Faction;
import services.ServFaction;
import services.ServWeapon;

import java.util.List;

public class MainExercise4 {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(Configuration.getInstance());
        DaoWeapon daoWeapon = new DaoWeaponImp(Configuration.getInstance(), dbConnection);
        ServWeapon servWeapon = new ServWeapon(daoWeapon);
        DaoFaction daoFaction = new DaoFactionImp(Configuration.getInstance(), dbConnection, servWeapon);
        ServFaction servFaction = new ServFaction(daoFaction);
        List<Faction> factionList = servFaction.getAll().get();
        System.out.println(factionList);
    }
}

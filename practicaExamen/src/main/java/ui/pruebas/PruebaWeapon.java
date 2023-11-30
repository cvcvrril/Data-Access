package ui.pruebas;

import common.Configuration;
import common.DBConnection;
import domain.DaoWeapon;
import domain.imp.DaoWeaponImp;
import model.Weapon;
import services.ServWeapon;

import java.util.List;

public class PruebaWeapon {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(Configuration.getInstance());
        DaoWeapon daoWeapon = new DaoWeaponImp(Configuration.getInstance(),dbConnection);
        ServWeapon servWeapon = new ServWeapon(daoWeapon);
        List<Weapon> weaponList = servWeapon.getAll().get();
        System.out.println(weaponList);
    }
}

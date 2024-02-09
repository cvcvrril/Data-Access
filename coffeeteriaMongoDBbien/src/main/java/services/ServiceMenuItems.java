package services;

import dao.db.DaoMenuItemDb;
import dao.hibernate.DaoMenuItemHibernate;
import dao.mongo.DaoMongoMenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.MenuItem;
import model.errors.ErrorCMenuItem;
import model.errors.ErrorCObject;
import model.mongo.MenuItemMongo;

import java.util.Collections;
import java.util.List;

public class ServiceMenuItems {

    private final DaoMenuItemDb daoMenuItemDBd;
    private final DaoMenuItemHibernate daoMenuItemHibernate;
    private final DaoMongoMenuItem daoMongoMenuItem;

    @Inject
    public ServiceMenuItems(DaoMenuItemDb daoMenuItemDBd, DaoMenuItemHibernate daoMenuItemHibernate, DaoMongoMenuItem daoMongoMenuItem) {
        this.daoMenuItemDBd = daoMenuItemDBd;
        this.daoMenuItemHibernate = daoMenuItemHibernate;
        this.daoMongoMenuItem = daoMongoMenuItem;
    }

    public Either<ErrorCObject, List<MenuItemMongo>> getAll(){
        //return daoMenuItemHibernate.getAll();
        return daoMongoMenuItem.getAll();
    }

    public Either<ErrorCMenuItem, MenuItem> get(int id){
        //return daoMenuItemDBd.get(id);
        return daoMenuItemHibernate.get(id);
    }

    public Either<ErrorCMenuItem, String> getMenuItemName(int id){
        Either<ErrorCMenuItem, String> res;
        Either<ErrorCMenuItem, MenuItem> menuItemResult = daoMenuItemDBd.get(id);

        if(menuItemResult.isRight()) {
            res = Either.right(menuItemResult.get().getNameMItem());
        } else {
            res = Either.left(menuItemResult.getLeft());
        }
        return res;
    }

    public Either<ErrorCMenuItem, Double> getMenuItemPrice(int id){
        Either<ErrorCMenuItem, Double> res;
        Either<ErrorCMenuItem, MenuItem> menouItemResult = daoMenuItemDBd.get(id);
        if (menouItemResult.isRight()){
            res = Either.right(menouItemResult.get().getPriceMItem());
        } else {
            res = Either.left(menouItemResult.getLeft());
        }
        return res;
    }

    public Either<ErrorCObject, MenuItemMongo> getMenuItemByName(String name) {
        List<MenuItemMongo> menuItemMongoList = getAll().getOrElse(Collections.emptyList());

        for (MenuItemMongo menuItemMongo : menuItemMongoList) {
            if (menuItemMongo.getName().equals(name)) {
                return Either.right(menuItemMongo);
            }
        }

        return Either.left(new ErrorCObject("MenuItem not found", 0));
    }

}

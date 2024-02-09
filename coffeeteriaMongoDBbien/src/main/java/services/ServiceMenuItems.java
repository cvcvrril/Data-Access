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
    private final DaoMongoMenuItem daoMongoMenuItem;

    @Inject
    public ServiceMenuItems(DaoMenuItemDb daoMenuItemDBd, DaoMongoMenuItem daoMongoMenuItem) {
        this.daoMenuItemDBd = daoMenuItemDBd;
        this.daoMongoMenuItem = daoMongoMenuItem;
    }

    public Either<ErrorCObject, List<MenuItemMongo>> getAll(){
        return daoMongoMenuItem.getAll();
    }

    public Either<ErrorCObject, MenuItemMongo> get(int id){
        return daoMongoMenuItem.get(id);
    }

    public Either<ErrorCObject, String> getMenuItemName(int id){
        Either<ErrorCObject, String> res;
        Either<ErrorCObject, MenuItemMongo> menuItemResult = daoMongoMenuItem.get(id);

        if(menuItemResult.isRight()) {
            res = Either.right(menuItemResult.get().getName());
        } else {
            res = Either.left(menuItemResult.getLeft());
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

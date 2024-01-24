package services;

import dao.db.DaoMenuItemDb;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.MenuItem;
import model.errors.ErrorCMenuItem;

import java.util.Collections;
import java.util.List;

public class ServiceMenuItems {

    private final DaoMenuItemDb daoMenuItemDBd;

    @Inject
    public ServiceMenuItems(DaoMenuItemDb daoMenuItemDBd) {
        this.daoMenuItemDBd = daoMenuItemDBd;
    }

    public Either<ErrorCMenuItem, List<MenuItem>> getAll(){
        return daoMenuItemDBd.getAll();
    }

    public Either<ErrorCMenuItem, MenuItem> get(int id){
        return daoMenuItemDBd.get(id);
    }

    public Either<ErrorCMenuItem, List<MenuItem>> getListMenuItems(int id){
        return daoMenuItemDBd.getByMenuItemId(id);
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

    public Either<ErrorCMenuItem, MenuItem> getMenuItemByName(String name) {
        List<MenuItem> menuItems = getAll().getOrElse(Collections.emptyList());

        for (MenuItem menuItem : menuItems) {
            if (menuItem.getNameMItem().equals(name)) {
                return Either.right(menuItem);
            }
        }

        return Either.left(new ErrorCMenuItem("MenuItem not found", 0));
    }

}

package dao.converters;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.MenuItem;
import model.errors.ErrorCObject;
import model.mongo.MenuItemMongo;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class MenuItemConverter {
    public Either<ErrorCObject, MenuItem> fromMongoToHibernateMenuItem(MenuItemMongo menuItemMongo){
        Either<ErrorCObject, MenuItem> res;
        try {
            MenuItem menuItemConverted = new MenuItem(menuItemMongo.get_id(), menuItemMongo.getName(), null, 0);
            res = Either.right(menuItemConverted);
        }catch (Exception e){
         log.error(e.getMessage(), e);
         res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

    //TODO: usar esto

    public Either<ErrorCObject, List<MenuItemMongo>> fromHibernateToMongoMenuItem(List<MenuItem> menuItemList){
        Either<ErrorCObject, List<MenuItemMongo>> res;
        List<MenuItemMongo> menuItemMongoList = new ArrayList<>();
        try {
            for (MenuItem menuItem: menuItemList){
                MenuItemMongo menuItemMongoConverted = new MenuItemMongo(menuItem.getIdMItem(), menuItem.getNameMItem());
                menuItemMongoList.add(menuItemMongoConverted);
            }
            res = Either.right(menuItemMongoList);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }
}

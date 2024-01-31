package dao.converters;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.MenuItem;
import model.errors.ErrorCObject;
import model.mongo.MenuItemMongo;

@Log4j2
public class MenuItemConverter {

    public Either<ErrorCObject, MenuItem> fromHibernateToMongoMenuItem(MenuItemMongo menuItemMongo){
        Either<ErrorCObject, MenuItem> res;
        try {
            MenuItem menuItemConverted = new MenuItem(menuItemMongo.get_id(), menuItemMongo.getName(), null,menuItemMongo.getPrice());
            res = Either.right(menuItemConverted);
        }catch (Exception e){
         log.error(e.getMessage(), e);
         res = Either.left(new ErrorCObject(e.getMessage(), 0));
        }
        return res;
    }

}

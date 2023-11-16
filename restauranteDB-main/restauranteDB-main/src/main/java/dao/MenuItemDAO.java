package dao;

import common.Error;
import io.vavr.control.Either;
import model.MenuItem;
import java.util.List;

public interface MenuItemDAO {
    Either<Error,List<MenuItem>> getAll();
    Either<Error, MenuItem> get(int id);
}

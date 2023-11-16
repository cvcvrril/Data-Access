package services;

import common.Error;
import dao.implJDBC.DBConnection;
import dao.implJDBC.MenuItemDAOdb;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.MenuItem;

import java.util.List;

public class MenuItemServices {
    private final MenuItemDAOdb miDAO;
    private final DBConnection db;
    @Inject
    public MenuItemServices(DBConnection db) {
        this.db = db;
        this.miDAO = new MenuItemDAOdb(this.db);
    }

    public Either<Error,List<MenuItem>> getAll() {
        return miDAO.getAll();
    }

    public Either<Error, MenuItem> get(int id) {
        return miDAO.get(id);
    }
}

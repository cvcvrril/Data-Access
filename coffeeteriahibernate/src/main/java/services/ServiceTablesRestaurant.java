package services;

import dao.db.DaoTablesDb;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.TableRestaurant;
import model.errors.ErrorCTables;

import java.util.List;

public class ServiceTablesRestaurant {

    private final DaoTablesDb daOtablesDB;

    @Inject
    public ServiceTablesRestaurant(DaoTablesDb daOtablesDB) {
        this.daOtablesDB = daOtablesDB;
    }

    public Either<ErrorCTables, List<TableRestaurant>> getAll(){
        return daOtablesDB.getAll();
    }

    public Either<ErrorCTables, TableRestaurant> get (int id){
        return daOtablesDB.get(id);
    }

}

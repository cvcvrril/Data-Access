package domain.imp;

import domain.DaoSale;
import io.vavr.control.Either;
import model.Sale;
import model.Spy;
import model.error.ErrorDb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoSaleImp implements DaoSale {
    @Override
    public Either<ErrorDb, List<Sale>> getAll() {
        return null;
    }

    @Override
    public Either<ErrorDb, Sale> get(int id) {
        return null;
    }

    @Override
    public Either<ErrorDb, Integer> add(Sale sale) {
        return null;
    }

    @Override
    public Either<ErrorDb, Integer> update(Sale sale) {
        return null;
    }

    @Override
    public Either<ErrorDb, Integer> delete(int id) {
        return null;
    }

    private List<Sale> readRS(ResultSet rs) throws SQLException {
        List<Sale> weaponList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            int units = rs.getInt("units");
            int idWeaponsFaction = rs.getInt("id_weapons_faction");
            LocalDate date = rs.getDate("sldate").toLocalDate();
            weaponList.add(new Sale(id, units, idWeaponsFaction, date));
        }
        return weaponList;
    }

}

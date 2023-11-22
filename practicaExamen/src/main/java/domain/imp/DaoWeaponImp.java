package domain.imp;

import common.Configuration;
import common.DBConnection;
import common.SqlQueries;
import domain.DaoWeapon;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Weapon;
import model.error.ErrorDb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoWeaponImp implements DaoWeapon {
    private final Configuration config;
    private final DBConnection db;

    @Inject
    public DaoWeaponImp(Configuration config, DBConnection db) {
        this.config = config;
        this.db = db;
    }


    @Override
    public Either<ErrorDb, List<Weapon>> getAll() {
        List<Weapon> weaponList;
        Either<ErrorDb, List<Weapon>> res;
        try (Connection connection = db.getConnection()){
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(SqlQueries.SELECT_ALL_FROM_WEAPONS);
            weaponList = readRS(rs);
            res = Either.right(weaponList);
        } catch (SQLException e) {
            log.error(e.getMessage(),e);
            res = Either.left(new ErrorDb("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<ErrorDb, Weapon> get(int id) {
        return null;
    }

    @Override
    public Either<ErrorDb, Integer> add(Weapon newWeapon) {
        return null;
    }

    @Override
    public Either<ErrorDb, Integer> update(Weapon updatedWeapon) {
        return null;
    }

    @Override
    public Either<ErrorDb, Integer> delete(int id) {
        return null;
    }

    private List<Weapon> readRS(ResultSet rs) throws SQLException {
        List<Weapon> weaponList = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("wname");
            double price = rs.getDouble("wprice");
            weaponList.add(new Weapon(id, name, price));
        }
        return weaponList;
    }
}

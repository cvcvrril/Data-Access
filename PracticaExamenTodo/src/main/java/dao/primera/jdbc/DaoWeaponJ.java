package dao.primera.jdbc;

import dao.primera.common.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.error.ExamError;
import model.primera.jdbc.Weapon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoWeaponJ {

    /**
     * Importamos el DB Connection, muy importante hacer el inject
     **/

    private final DBConnectionPool db;

    @Inject
    public DaoWeaponJ(DBConnectionPool db) {
        this.db = db;
    }

    /**
     * Los métodos de JDBC se hacen a partir de queries en SQL
     * Es muy sencillito; se consigue a partir de joins y demás
     * Siempre van a haber tablas intermedias o relaciones con las que ir sacando cosas
     * **/

    public Either<ExamError, List<Weapon>> getAllByNameFaction(String nameFaction) {
        Either<ExamError, List<Weapon>> res;
        List<Weapon> weapons;
        try (Connection connection = db.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement("select * from weapons w join weapons_factions wf on w.id = wf.id_weapon join faction f on wf.name_faction = f.fname where f.fname =?");
            pstmt.setString(1, nameFaction);
            ResultSet rs = pstmt.executeQuery();
            weapons = readRS(rs);
            if (weapons.isEmpty()) {
                res = Either.left(new ExamError(0, "The list of weapons by that name is empty"));
            } else {
                res = Either.right(weapons);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ExamError(0, "There was an unexpected error"));
        }
        return res;
    }

    /**
     * Para los update, hay que mandar el objeto al completo
     * Y hay que hacer un update a partir de una query
     * Como hay que usar una query, posteriormente habrá que hacer Sets de los atributos del objeto
     * **/

    public Either<ExamError, Integer> update(Weapon updatedWeapon){
        Either<ExamError, Integer> res;
        int rowsAffected;
        try (Connection connection = db.getConnection()){
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement("update weapons set wname=?, wprice=? where id=?");
            pstmt.setString(1, updatedWeapon.getName());
            pstmt.setDouble(2, updatedWeapon.getPrice());
            pstmt.setInt(3, updatedWeapon.getId());
            rowsAffected = pstmt.executeUpdate();
            connection.commit();
            res = Either.right(rowsAffected);
        }catch (SQLException e){
            log.error(e.getMessage(), e);
            res = Either.left(new ExamError(0, "There was an unexpected error"));
        }
        return res;
    }


    /**
     * Hay que montar el ReadRS
     * Los getString() se hacen con el nombre de la columna en la base de datos
     **/

    private List<Weapon> readRS(ResultSet rs) throws SQLException {
        List<Weapon> weaponList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("wname");
            double price = rs.getDouble("wprice");
            weaponList.add(new Weapon(id, name, price));
        }
        return weaponList;
    }

}

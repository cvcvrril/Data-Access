package dao.primera.jdbc;

import dao.primera.common.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.error.ExamError;
import model.primera.jdbc.Spy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoSpyJ {

    /**
     * Importamos el DB Connection, muy importante hacer el inject
     **/

    private final DBConnectionPool db;

    @Inject
    public DaoSpyJ(DBConnectionPool db) {
        this.db = db;
    }

    public Either<ExamError, Spy> get(int id) {
        List<Spy> spyList;
        Either<ExamError, Spy> res;
        try (Connection connection = db.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement("select * from spies where id =?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            spyList = readRS(rs);
            if (!spyList.isEmpty()) {
                res = Either.right(spyList.get(0));
            } else {
                res = Either.left(new ExamError(0, "There's no spy with that id"));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ExamError(0, e.getMessage()));
        }
        return res;
    }

    public Either<ExamError, Integer> delete(Spy deleteSpy) {
        Either<ExamError, Integer> res;
        int rowsAffected;
        try (Connection connection = db.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement("delete from battles where id_spy=?");
            pstmt.setInt(1, deleteSpy.getId());
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected !=1){
                res = Either.left(new ExamError(0,"There was an error deleting the spy"));
            } else {
                PreparedStatement pstmt2 = connection.prepareStatement("delete from spies where id =?");
                pstmt2.setInt(1, deleteSpy.getId());
                rowsAffected = pstmt2.executeUpdate();
                res = Either.right(rowsAffected);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ExamError(0, e.getMessage()));
        }
        return res;
    }


    /**
     * Hay que montar el ReadRS
     * Los getString() se hacen con el nombre de la columna en la base de datos
     **/

    private List<Spy> readRS(ResultSet rs) throws SQLException {
        List<Spy> weaponList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("sname");
            String race = rs.getString("srace");
            weaponList.add(new Spy(id, name, race));
        }
        return weaponList;
    }


}

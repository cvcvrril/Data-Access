package dao.primera.jdbc;

import dao.primera.common.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.PUT;
import lombok.extern.log4j.Log4j2;
import model.error.ExamError;
import model.primera.jdbc.Battle;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoBattleJ {

    /**
     * Importamos el DB Connection, muy importante hacer el inject
     **/

    private final DBConnectionPool db;
    private final DaoFactionJ daoFactionJ;

    @Inject
    public DaoBattleJ(DBConnectionPool db, DaoFactionJ daoFactionJ) {
        this.db = db;
        this.daoFactionJ = daoFactionJ;
    }

    /**
     * Esto es un ejemplo de un add complejo, es decir, un add donde se requiere una transacción.
     * En este caso, se tiene que hacer add de una batalla.
     * Y si una de las facciones no existen, se tiene que añadir también una facción.
     * **/


    /**
     * Primero comprobamos que la facción que ha introducido el usuario existe
     * Si no existe, se crea uno nuevo
     * Si existe, se continúa con el add normal y corriente
     **/


    public Either<ExamError, Integer> add(Battle newBatte) {
        Either<ExamError, Integer> res;
        if (!daoFactionJ.getByName(newBatte.getFirstFaction()).isRight() || !daoFactionJ.getByName(newBatte.getSecondFaction()).isRight()) {
            res = complexAdd(newBatte);
        } else {
            res = simpleAdd(newBatte);
        }
        return res;
    }

    /**
     * Y esto es un simple add, donde sólo se añade la batalla (porque ambas facciones ya existen)
     * **/


    public Either<ExamError, Integer> simpleAdd(Battle newBattle) {
        Either<ExamError, Integer> res;
        int rowsAffected;
        try (Connection connection = db.getConnection()) {
            try (PreparedStatement pstmt = connection.prepareStatement("insert into battles (bname, faction_one, faction_two, bplace, bdate, id_spy) values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){
                connection.setAutoCommit(false);

                pstmt.setString(1, newBattle.getBName());
                pstmt.setString(2, newBattle.getFirstFaction());
                pstmt.setString(3, newBattle.getSecondFaction());
                pstmt.setString(4, newBattle.getPlace());
                pstmt.setDate(5, Date.valueOf(newBattle.getDate()));
                pstmt.setInt(6, newBattle.getIdSpy());

                int result = pstmt.executeUpdate();

                if (result > 0){
                    ResultSet rs = pstmt.getGeneratedKeys();
                    res = Either.right(result);
                    if (rs.next()){
                        int autoId = rs.getInt(1);
                        newBattle.setId(autoId);

                    }
                }else {
                    res = Either.left(new ExamError(0, "Ha habido un error"));
                }
            }catch (SQLException e) {
                res = Either.left(new ExamError(0, e.getMessage()));
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    res = Either.left(new ExamError(0, e.getMessage()));
                }
            } finally {
                connection.setAutoCommit(true);
            }
        }catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ExamError(0, "There was an unexpected error"));
        }
        return res;
    }

    public Either<ExamError, Integer> complexAdd(Battle newBattle) {
        Either<ExamError, Integer> res;
        int rowsAffected;
        try (Connection connection = db.getConnection()) {
            try (PreparedStatement pstmt = connection.prepareStatement("insert into battles (id, bname, faction_one, faction_two, bplace, bdate, id_spy) values (?, ?, ?, ?, ?, ?, ?)"); //CAMBIAR QUERY
                 PreparedStatement pstmtFacction = connection.prepareStatement("insert into faction (fname, contact, planet, number_controlled_systems, date_last_purchase) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);

                /**
                 * Aquí creamos la facción que no existe
                 * **/

                pstmtFacction.setString(1, newBattle.getFirstFaction());
                pstmtFacction.setString(2, "Mr. Anon");
                pstmtFacction.setString(3, "Hoth");
                pstmtFacction.setInt(4, 0);
                pstmtFacction.setDate(5, Date.valueOf(LocalDate.now()));


                /**
                 * Se toma ahora en cuenta cuantos cambios se han realizado (es decir, si la transacción se ha hecho bien).
                 * Si los cambios son mayores a 0, entonces se genera el id de la batalla.
                 * **/

                int result = pstmtFacction.executeUpdate();
                ResultSet rs = pstmtFacction.getGeneratedKeys();
                if (rs.next()) {
                    int autoId = rs.getInt(1);
                    newBattle.setId(autoId);
                }

                /**
                 * Y ahora se crea la batalla
                 * **/
                pstmt.setInt(1, newBattle.getId());
                pstmt.setString(2, newBattle.getBName());
                pstmt.setString(3, newBattle.getFirstFaction());
                pstmt.setString(4, newBattle.getSecondFaction());
                pstmt.setString(5, newBattle.getPlace());
                pstmt.setDate(6, Date.valueOf(newBattle.getDate()));
                pstmt.setInt(7, newBattle.getIdSpy());

                rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    res = Either.right(rowsAffected);
                } else {
                    res = Either.left(new ExamError(0, "Ha habido un error"));
                }
            } catch (SQLException e) {
                res = Either.left(new ExamError(0, e.getMessage()));
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    res = Either.left(new ExamError(0, e.getMessage()));
                }
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ExamError(0, "There was an unexpected error"));
        }
        return res;
    }


    private List<Battle> readRS(ResultSet rs) throws SQLException {
        List<Battle> battleList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String bname = rs.getString("bname");
            String factionOne = rs.getString("faction_one");
            String factionTwo = rs.getString("faction_two");
            String bplace = rs.getString("bplace");
            LocalDate bdate = rs.getDate("bdate").toLocalDate();
            int idSpy = rs.getInt("id_spy");
            battleList.add(new Battle(id, bname, factionOne, factionTwo, bplace, bdate, idSpy));
        }
        return battleList;
    }

}

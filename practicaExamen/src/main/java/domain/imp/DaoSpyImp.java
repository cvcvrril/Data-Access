package domain.imp;

import common.Configuration;
import common.DBConnection;
import domain.DaoSpy;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Spy;
import model.error.ErrorDb;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoSpyImp implements DaoSpy {
    private final Configuration config;
    private final DBConnection db;

    @Inject
    public DaoSpyImp(Configuration config, DBConnection db) {
        this.config = config;
        this.db = db;
    }

    @Override
    public Either<ErrorDb, List<Spy>> getAll() {
        List<Spy> spyList;
        Either<ErrorDb, List<Spy>> res;
        try (Connection connection = db.getConnection()) {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select * from spies");
            spyList = readRS(rs);
            res = Either.right(spyList);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorDb("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<ErrorDb, Spy> get(int id) {
        List<Spy> spyList;
        Either<ErrorDb, Spy> res;
        try (Connection connection = db.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement("select * from spies where id =?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            spyList = readRS(rs);
            if (!spyList.isEmpty()) {
                res = Either.right(spyList.get(0));
            } else {
                res = Either.left(new ErrorDb("There's no spies with that id", 0, LocalDateTime.now()));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorDb("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<ErrorDb, Integer> add(Spy spy) {
        int rowsAffected;
        Either<ErrorDb, Integer> res;
        try (Connection connection = db.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement("insert into spies (sname, srace) values (?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, spy.getName());
            pstmt.setString(2, spy.getRace());
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected != 1) {
                connection.rollback();
                return Either.left(new ErrorDb("There was an error adding the new spy", 0, LocalDateTime.now()));
            }
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int generatedSpyId;
            if (generatedKeys.next()) {
                generatedSpyId = generatedKeys.getInt(1);
            } else {
                connection.rollback();
                return Either.left(new ErrorDb("There was an error obtaining the id", 0, LocalDateTime.now()));
            }
            pstmt.setInt(1, generatedSpyId);
            connection.commit();
            res = Either.right(rowsAffected);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorDb("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<ErrorDb, Integer> update(Spy spy) {
        int rowsAffected;
        Either<ErrorDb, Integer> res;
        try (Connection connection = db.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement("update spies set sname=?, srace=? where id=?");
            pstmt.setString(1, spy.getName());
            pstmt.setString(2, spy.getRace());
            pstmt.setInt(3, spy.getId());
            rowsAffected = pstmt.executeUpdate();
            connection.commit();
            res = Either.right(rowsAffected);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorDb("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<ErrorDb, Integer> delete(int id) {
        int rowsAffected;
        Either<ErrorDb, Integer> res;
        try (Connection connection = db.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement("delete from spies where id =?");
            pstmt.setInt(1, id);
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected !=1){
                res = Either.left(new ErrorDb("There was an error deleting the spy", 0, LocalDateTime.now()));
            } else {
                res = Either.right(rowsAffected);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorDb("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

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

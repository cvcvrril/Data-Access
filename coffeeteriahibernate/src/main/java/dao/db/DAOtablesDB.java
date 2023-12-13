package dao.db;

import common.Configuration;
import common.SQLqueries;
import dao.ConstantsDAO;
import dao.connection.DBConnection;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.TableRestaurant;
import model.errors.ErrorCTables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DAOtablesDB {

    private final Configuration configuration;
    private final DBConnection db;

    @Inject
    public DAOtablesDB(Configuration configuration, DBConnection db) {
        this.configuration = configuration;
        this.db = db;
    }

    public Either<ErrorCTables, List<TableRestaurant>> getAll(){
        List<TableRestaurant> tableRestaurantList = new ArrayList<>();
        Either <ErrorCTables, List<TableRestaurant>> res;
        try (Connection myconnection = db.getConnection()){
            Statement stmt = myconnection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLqueries.SELECT_FROM_RESTAURANT_TABLES);
            tableRestaurantList = readRS(rs);
            res = Either.right(tableRestaurantList);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCTables(e.getMessage(),  0));
        }
        return res;
    }

    public Either<ErrorCTables, TableRestaurant> get(int i){
        Either<ErrorCTables, TableRestaurant> res;
        try (Connection myconnection = db.getConnection()){
            PreparedStatement pstmt = myconnection.prepareStatement(SQLqueries.SELECT_NUMBER_ID);
            pstmt.setInt(1, i);
            ResultSet rs = pstmt.executeQuery();
            List<TableRestaurant> tableRestaurantList = readRS(rs);
            if (!tableRestaurantList.isEmpty()){
                res = Either.right(tableRestaurantList.get(0));
            }else {
                res = Either.left(new ErrorCTables(ConstantsDAO.ERROR_READING_DATABASE, 0));
            }
            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCTables(e.getMessage(), 0));
        }
        return res;
    }

    private List<TableRestaurant> readRS(ResultSet rs) throws SQLException {
        List<TableRestaurant> tableRestaurantList = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt(ConstantsDAO.TABLE_NUMBER_ID);
            int seats = rs.getInt(ConstantsDAO.NUMBER_OF_SEATS);
            tableRestaurantList.add(new TableRestaurant(id, seats));
        }
        return tableRestaurantList;
    }
}

package dao.db;

import common.Configuration;
import common.SQLqueries;
import dao.ConstantsDao;
import dao.connection.DbConnection;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.MenuItem;
import model.errors.ErrorCMenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoMenuItemDb {

    private final DbConnection db;

    @Inject
    public DaoMenuItemDb(Configuration configuration, DbConnection db) {
        this.db = db;
    }


    public Either<ErrorCMenuItem, List<MenuItem>> getAll(){
        List<MenuItem> menuItemList = new ArrayList<>();
        Either<ErrorCMenuItem, List<MenuItem>> res;
        try (Connection myConnection = db.getConnection()){
            Statement stmt = myConnection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLqueries.SELECT_FROM_MENU_ITEMS);
            menuItemList = readRS(rs);
            res = Either.right(menuItemList);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCMenuItem(e.getMessage(), 0));
        }
        return  res;
    }

    public Either<ErrorCMenuItem, MenuItem> get(int id){
        Either<ErrorCMenuItem, MenuItem> res;
        try (Connection myConnection = db.getConnection()){
            PreparedStatement pstmt = myConnection.prepareStatement(SQLqueries.SELECT_FROM_MENU_ITEMS_ID);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            List<MenuItem> menuItemList = readRS(rs);
            if (!menuItemList.isEmpty()){
                res = Either.right(menuItemList.get(0));
            } else {
                res = Either.left(new ErrorCMenuItem(ConstantsDao.ERROR_READING_DATABASE, 0));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCMenuItem(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCMenuItem, List<MenuItem>> getByMenuItemId(int orderId){
        List<MenuItem> orderItemList = new ArrayList<>();
        Either<ErrorCMenuItem, List<MenuItem>> res;
        try(Connection myconnection = db.getConnection()){
            PreparedStatement pstmt = myconnection.prepareStatement(SQLqueries.SELECT_FROM_MENU_ITEMS_ID);
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            orderItemList = readRS(rs);
            res = Either.right(orderItemList);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCMenuItem(e.getMessage(), 0));
        }
        return res;
    }

    private List<MenuItem> readRS(ResultSet rs) throws SQLException {
        List<MenuItem> menuItemList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("menu_item_id");
            String nameM = rs.getString("name");
            String desM = rs.getString("description");
            float priceM = rs.getFloat("price");
            menuItemList.add(new MenuItem(id, nameM, desM, priceM));
        }
        return menuItemList;
    }

}

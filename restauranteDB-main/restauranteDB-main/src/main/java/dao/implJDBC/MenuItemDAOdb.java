package dao.implJDBC;

import common.Error;
import common.Queries;
import io.vavr.control.Either;
import model.MenuItem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAOdb implements dao.MenuItemDAO {
    private final DBConnection db;

    public MenuItemDAOdb(DBConnection db) {
        this.db = db;
    }

    @Override
    public Either<Error,List<MenuItem>> getAll() {
        Either<Error,List<MenuItem>> either;
        try (Connection myConnection = db.getConnection();
             Statement statement = myConnection.createStatement()){
            ResultSet resultSet = statement.executeQuery(Queries.GETALL_menuItems_QUERY);
            either= Either.right(readRS(resultSet));
        } catch (SQLException e) {
            either= Either.left(new Error("no fufa",1, LocalDate.now()));
        }
        return either;
    }
    @Override
    public Either<Error, MenuItem> get(int id) {
        Either<Error,MenuItem> either;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Queries.SELECT_menuItem_QUERY)) {
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet rs = preparedStatement.executeQuery();
            either=Either.right(readRS(rs).stream().filter(item1 -> item1.getId() == id).findFirst().orElse(null));
        } catch (SQLException e) {
            either= Either.left(new Error("no fufa",1, LocalDate.now()));
        }
        return either;
    }

    private List<MenuItem> readRS(ResultSet resultSet) throws SQLException {
        List<MenuItem> menuItemList;
        menuItemList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("menu_item_id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            float price = resultSet.getFloat("price");
            menuItemList.add(new MenuItem(id, name, description, price));
        }
        return menuItemList;
    }
}

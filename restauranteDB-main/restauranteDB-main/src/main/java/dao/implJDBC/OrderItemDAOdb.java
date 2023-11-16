package dao.implJDBC;

import common.Error;
import common.Queries;
import io.vavr.control.Either;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import model.OrderItem;
import services.MenuItemServices;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Data
public class OrderItemDAOdb implements dao.OrderItemDAO {
    private final DBConnection db;

    public OrderItemDAOdb(DBConnection db) {

        this.db = db;
    }


    @Override
    public Either<Error, List<OrderItem>> getAll() {
        Either<Error, List<OrderItem>> either;
        List<OrderItem> orderItemList;
        try (Connection myConnection = db.getConnection();
             Statement statement = myConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(Queries.GETALLALL_orderItems_QUERY);
            orderItemList = readRS(resultSet);
            either = Either.right(orderItemList);
        } catch (SQLException e) {
            either = Either.left(new Error("Error getAll de orders", 1, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<Error, List<OrderItem>> getAll(int orderID) {
        Either<Error,List<OrderItem>> either;
        try (Connection myConnection = db.getConnection();
             PreparedStatement preparedStatement = myConnection.prepareStatement(Queries.GETALL_orderItems_QUERY)) {
            preparedStatement.setInt(1, orderID);
            ResultSet resultSet = preparedStatement.executeQuery();
            either= Either.right(readRS(resultSet));
        } catch (SQLException e) {
            either= Either.left(new Error("no fufa",1, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<Error, OrderItem> get(int id) {
        Either<Error,OrderItem> either;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Queries.SELECT_orderIems_QUERY)) {
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet rs = preparedStatement.executeQuery();
            either= Either.right(readRS(rs).stream().filter(orderItem -> orderItem.getId() == id).findFirst().orElse(null));
        } catch (SQLException e) {
            either= Either.left(new Error("no fufa",1, LocalDate.now()));
        }
        return either;
    }
    @Override
    public Either<Error, Integer> update(OrderItem orderItem) {
        Either<Error,Integer> either = null;
        int rowsAffected;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Queries.UPDATE_orderItem_QUERY)) {
            preparedStatement.setInt(1, orderItem.getMenuItem().getId());
            preparedStatement.setInt(2, orderItem.getQuantity());
            preparedStatement.setInt(3, orderItem.getId());
            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected>0){
                either=Either.right(rowsAffected);
            }else{
                either=Either.left( new Error("watafak", 69, LocalDate.now()));
            }
        } catch (SQLException e) {
            new Error("watafak", 69, LocalDate.now());
        }
        return either;
    }
    public Either<Error,Integer> deleteByOrder(int orderid){
        Either<Error,Integer> either = null;
        int rowsAffected;
        try (Connection con = db.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(Queries.DELETEBYORDER_orderItem_QUERY)) {
            preparedStatement.setInt(1, orderid);
            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected>0){
                either=Either.right(rowsAffected);
            }else{
                either=Either.left( new Error("Error deleting orderItems", 1, LocalDate.now()));
            }
        } catch (SQLException e) {
            new Error("Error deleting orderItems", 2, LocalDate.now());
        }
        return either;
    }

    public Either<Error,Integer> insertByOrder(List<OrderItem> orderItemList, int orderID){
        Either<Error,Integer> either = null;
        int rowsAffected;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Queries.SAVE_orderItem_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            for (OrderItem orderItem : orderItemList) {
                preparedStatement.setInt(1, orderItem.getId());
                preparedStatement.setInt(2, orderID);
                preparedStatement.setInt(3, orderItem.getMenuItem().getId());
                preparedStatement.setInt(4, orderItem.getQuantity());
                rowsAffected = preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    rs.getInt(1);
                }
                if (rowsAffected>0){
                    either=Either.right(rowsAffected);
                }else{
                    either=Either.left( new Error("Error saving orderItems", 1, LocalDate.now()));
                }
            }
        } catch (SQLException e) {
            new Error("Error saving orderItems", 2, LocalDate.now());
        }
        return either;
    }
    public List<OrderItem> readRS(ResultSet resultSet) throws SQLException {
        List<OrderItem> orderItemList= new ArrayList<>();
        MenuItemServices mis= new MenuItemServices(db);
        while (resultSet.next()) {
            int id = resultSet.getInt("order_item_id");
            int orderId = resultSet.getInt("order_id");
            int menuItemId = resultSet.getInt("menu_item_id");
            int quantity = resultSet.getInt("quantity");
            orderItemList.add(new OrderItem(id, orderId, mis.get(menuItemId).getOrNull(), quantity));
        }
        return orderItemList;
    }
}
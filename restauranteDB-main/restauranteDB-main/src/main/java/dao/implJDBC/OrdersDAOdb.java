package dao.implJDBC;

import common.Queries;
import dao.OrderDAO;
import io.vavr.control.Either;
import lombok.Data;
import common.Error;
import lombok.extern.log4j.Log4j2;
import model.Order;
import model.OrderItem;
import services.OrderItemServices;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Data
public class OrdersDAOdb implements OrderDAO {
    private final DBConnection db;

    public OrdersDAOdb(DBConnection db) {

        this.db = db;
    }

    @Override
    public Either<Error, List<Order>> getAll() {
        Either<Error, List<Order>> either;
        List<Order> orderList;
        try (Connection myConnection = db.getConnection();
             Statement statement = myConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(Queries.GETALL_orders_QUERY);
            orderList = readRS(resultSet);
            either = Either.right(orderList);
        } catch (SQLException e) {
            either = Either.left(new Error("Error getAll de orders", 1, LocalDate.now()));
        }
        return either;
    }
    @Override
    public Either<Error, List<Order>> getAll(int id) {
        Either<Error, List<Order>> either;
        List<Order> orderList;
        try (Connection myConnection = db.getConnection();
             PreparedStatement statement = myConnection.prepareStatement(Queries.GETALL_oneCustomers_orders_QUERY)) {
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            orderList = readRS(resultSet);
            either = Either.right(orderList);
        } catch (SQLException e) {
            either = Either.left(new Error("Error getAll de orders", 1, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<Error, Order> get(int id) {
        Either<Error, Order> either;
        Order order;
        try (Connection myConnection = db.getConnection();
             PreparedStatement statement = myConnection.prepareStatement(Queries.SELECT_order_QUERY)) {
            statement.setString(1, String.valueOf(id));
            ResultSet rs = statement.executeQuery();
            order = readRS(rs).stream().filter(order1 -> order1.getId() == id).findFirst().orElse(null);
            either = Either.right(order);
        } catch (SQLException e) {
            either = Either.left(new Error("Error get(id) de orders", 1, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<Error, Integer> save(Order c) {
        Either<Error, Integer> either;
        int rowsAffected;
        try (Connection con = db.getConnection()) {
            try (
                    PreparedStatement addOrderStatement = con.prepareStatement(Queries.SAVE_order_QUERY, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement addOrderItemsStatement = con.prepareStatement(Queries.SAVE_orderItem_QUERY, Statement.RETURN_GENERATED_KEYS)
            ) {
                addOrderStatement.setTimestamp(2, Timestamp.valueOf(c.getDateTime()));
                addOrderStatement.setInt(3, c.getCustomerID());
                addOrderStatement.setInt(4, c.getTableNumber());
                con.setAutoCommit(false);
                rowsAffected = addOrderStatement.executeUpdate();
                ResultSet rs = addOrderStatement.getGeneratedKeys();
                if (rs.next()) {
                    c.setId(rs.getInt(0));
                }
                for (OrderItem orderItem : c.getOrderItems()) {
                    addOrderItemsStatement.setInt(1, orderItem.getOrderID());
                    addOrderItemsStatement.setInt(2, orderItem.getMenuItem().getId());
                    addOrderItemsStatement.setInt(3, orderItem.getQuantity());
                    addOrderItemsStatement.executeUpdate();
                    ResultSet rS = addOrderItemsStatement.getGeneratedKeys();
                    if (rS.next()) {
                        orderItem.setOrderID(rs.getInt(0));
                    }
                }
                con.commit();

                either = Either.right(rowsAffected);
            } catch (SQLException e) {
                either = Either.left(new Error("Error save de orders", 1, LocalDate.now()));
            } catch (Exception e) {
                either = Either.left(new Error("Error save de orders", 2, LocalDate.now()));
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    either = Either.left(new Error("Error save de orders", 3, LocalDate.now()));
                }
            }
        } catch (SQLException e) {
            either = Either.left(new Error("Error save de orders", 4, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<Error, Integer> update(Order c) {
        Either<Error, Integer> either;
        int rowsAffected;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Queries.UPDATE_order_QUERY)) {
            preparedStatement.setInt(4, c.getId());
            preparedStatement.setTimestamp(1, Timestamp.valueOf(c.getDateTime()));
            preparedStatement.setInt(2, c.getCustomerID());
            preparedStatement.setInt(3, c.getTableNumber());
            rowsAffected = preparedStatement.executeUpdate();
            either = Either.right(rowsAffected);
        } catch (SQLException e) {
            either = Either.left(new Error("Error update de orders", 1, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<Error, Integer> delete(Order c, boolean conf) {
        Either<Error, Integer> either = null;
        int rowsAffected = 0;
        if (conf) {
            try (Connection con = db.getConnection()) {
                con.setAutoCommit(false);
                try (PreparedStatement deleteOrderItems = con.prepareStatement(Queries.DELETE_orderItemOrder_QUERY);
                     PreparedStatement deleteOrders = con.prepareStatement(Queries.DELETE_orderOrder_QUERY)) {
                    deleteOrders.setInt(1, c.getId());
                    deleteOrderItems.setInt(1, c.getId());
                    deleteOrderItems.executeUpdate();
                    deleteOrders.executeUpdate();
                    rowsAffected = deleteOrders.executeUpdate();
                    con.commit();
                    either = Either.right(rowsAffected);
                } catch (SQLException e) {
                    try {
                        con.rollback();
                    } catch (SQLException ex) {
                        if (ex.getErrorCode() == 1451) {
                            either = Either.left(new Error("no se ha borrao por la forein ki DELETE ORDERS", 1451, LocalDate.now()));
                        } else {
                            either = Either.left(new Error("no se ha borrao por idk why DELETE ORDERS", 69, LocalDate.now()));
                        }
                    }
                } finally {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                either = Either.left(new Error("no se ha borrao por idk why DELETE ORDERS", 69, LocalDate.now()));
            }
        }
        return either;
    }

    private List<Order> readRS(ResultSet resultSet) throws SQLException {
        OrderItemServices orderItemServices=new OrderItemServices(db);
        List<Order> orderList;
        orderList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("order_id");
            LocalDateTime localDate = resultSet.getTimestamp("order_date").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            int customerID = resultSet.getInt("customer_id");
            int tableNumber = resultSet.getInt("table_id");
            Order order=new Order(id, localDate, customerID, tableNumber);
            order.setOrderItems(orderItemServices.getAll(id).getOrNull());
            orderList.add(order);
        }
        return orderList;
    }
}
package dao.db;

import common.Configuration;
import common.SQLqueries;
import dao.ConstantsDao;
import dao.connection.DbConnection;
import dao.connection.DbConnectionPool;
import dao.spring.DaoOrderItemSpring;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Order;
import model.OrderItem;
import model.errors.ErrorCOrder;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoOrderDb {

    private final Configuration config;
    private final DbConnection db;
    private final DbConnectionPool pool;
    private final DaoOrderItemSpring daOorderItemSpring;

    @Inject
    public DaoOrderDb(Configuration config, DbConnection db, DbConnectionPool pool, DaoOrderItemSpring daOorderItemSpring) {
        this.config = config;
        this.db = db;
        this.pool = pool;
        this.daOorderItemSpring = daOorderItemSpring;
    }

    public Either<ErrorCOrder, List<Order>> getAll() {
        List<Order> orderList = new ArrayList<>();
        Either<ErrorCOrder, List<Order>> res;
        try (Connection myConnection = pool.getConnection()) {
            Statement stmt = myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(SQLqueries.SELECT_FROM_ORDERS);
            orderList = readRS(rs);
            res = Either.right(orderList);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCOrder, Order> get(int id) {
        Either<ErrorCOrder, Order> res;
        try (Connection myConnection = pool.getConnection()) {
            PreparedStatement pstmt = myConnection.prepareStatement(SQLqueries.SELECT_ORDERS_ID);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            List<Order> orderList = readRS(rs);
            if (!orderList.isEmpty()) {
                res = Either.right(orderList.get(0));
            } else {
                res = Either.left(new ErrorCOrder(ConstantsDao.ERROR_READING_DATABASE, 0));
            }
            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCOrder, List<Order>> getOrders(int id) {
        Either<ErrorCOrder, List<Order>> res;
        List<Order> orderList;
        try (Connection myConnection = db.getConnection()) {
            PreparedStatement pstmt = myConnection.prepareStatement(SQLqueries.SELECT_FROM_ORDERS_WHERE_CUSTOMER_ID);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            orderList = readRS(rs);
            res = Either.right(orderList);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        }
        return res;
    }


    public Either<ErrorCOrder, Integer> update(Order order) {
        int rowsAffected;
        Either<ErrorCOrder, Integer> res;
        try (Connection myConnection = pool.getConnection()) {
            try (PreparedStatement pstmt = myConnection.prepareStatement(SQLqueries.UPDATE_ORDERS)) {
                myConnection.setAutoCommit(false);
                pstmt.setTimestamp(1, Timestamp.valueOf(order.getOrDate()));
                pstmt.setInt(2, order.getIdCo());
                pstmt.setInt(3, order.getIdTable());
                pstmt.setInt(4, order.getIdOrd());
                rowsAffected = pstmt.executeUpdate();
                myConnection.commit();
                res = Either.right(rowsAffected);
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                res = Either.left(new ErrorCOrder(e.getMessage(), 0));
                try {
                    myConnection.rollback();
                } catch (SQLException ex) {
                    res = Either.left(new ErrorCOrder(e.getMessage(), 0));
                }
            } finally {
                myConnection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCOrder, Integer> delete(int id) {
        Either<ErrorCOrder, Integer> res;
        try (Connection myConnection = db.getConnection()) {
            PreparedStatement pstmt1 = myConnection.prepareStatement(SQLqueries.DELETE_ORDER_ITEMS);
            pstmt1.setInt(1, id);
            pstmt1.executeUpdate();
            PreparedStatement pstmt2 = myConnection.prepareStatement(SQLqueries.DELETE_ORDERS_ID);
            pstmt2.setInt(1, id);
            int rowsAffected = pstmt2.executeUpdate();
            if (rowsAffected != 1) {
                res = Either.left(new ErrorCOrder(ConstantsDao.ERROR_DELETING_ORDER, 0));
            } else {
                res = Either.right(rowsAffected);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCOrder, Integer> add(Order order) {
        int rowsAffected;
        Either<ErrorCOrder, Integer> res;
        try (Connection myConnection = pool.getConnection()) {
            myConnection.setAutoCommit(false);

            //Lo del order
            try (PreparedStatement pstmtOrder = myConnection.prepareStatement(SQLqueries.INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
                pstmtOrder.setTimestamp(1, Timestamp.valueOf(order.getOrDate()));
                pstmtOrder.setInt(2, order.getIdCo());
                pstmtOrder.setInt(3, order.getIdTable());
                rowsAffected = pstmtOrder.executeUpdate();
                ResultSet rs = pstmtOrder.getGeneratedKeys();
                if (rs.next()) {
                    order.setIdOrd(rs.getInt(1));
                }

                if (rowsAffected != 1) {
                    myConnection.rollback();
                    res = Either.left(new ErrorCOrder(ConstantsDao.ERROR_ADDING_ORDER, 0));
                } else {
                    //Lo de los orderItems
                    for (OrderItem orderItem : order.getOrderItems()) {
                        try (PreparedStatement pstmtOrderItem = myConnection.prepareStatement(SQLqueries.INSERT_ORDER_ITEM)) {
                            //pstmtOrderItem.setInt(1, order.getOrderItems().get(0).getOrderId());
                            pstmtOrderItem.setInt(2, order.getIdOrd());
                            //pstmtOrderItem.setInt(3, orderItem.getMenuItem());
                            pstmtOrderItem.setInt(4, orderItem.getQuantity());
                            pstmtOrderItem.executeUpdate();
                        }
                    }
                    myConnection.commit();
                    res = Either.right(rowsAffected);
                }
            } catch (SQLException e) {
                myConnection.rollback();
                log.error(e.getMessage(), e);
                res = Either.left(new ErrorCOrder(e.getMessage(), 0));
            } finally {
                myConnection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCOrder(e.getMessage(), 0));
        }

        return res;
    }

    private List<Order> readRS(ResultSet rs) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(ConstantsDao.ORDER_ID);
            LocalDateTime dateTime = null;
            Timestamp timestamp = rs.getTimestamp(ConstantsDao.ORDER_DATE);
            if (timestamp != null) {
                dateTime = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
            int customerId = rs.getInt(ConstantsDao.CUSTOMER_ID);
            int tableId = rs.getInt(ConstantsDao.TABLE_ID);
            orderList.add(new Order(id, dateTime, customerId, tableId, daOorderItemSpring.get(id).get()));
        }
        return orderList;
    }

}

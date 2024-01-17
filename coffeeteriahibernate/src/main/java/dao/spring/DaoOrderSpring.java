package dao.spring;

import common.SQLqueries;
import dao.connection.DbConnectionPool;
import dao.mappers.OrderMapper;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Order;
import model.errors.ErrorCOrder;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Log4j2
public class DaoOrderSpring {

    private final DbConnectionPool pool;

    @Inject
    public DaoOrderSpring(DbConnectionPool pool) {
        this.pool = pool;
    }

    public Either<ErrorCOrder, List<Order>> getAll(){
        Either<ErrorCOrder, List<Order>> res;
        JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
        List<Order> orderList = jtm.query(SQLqueries.SELECT_FROM_ORDERS, new OrderMapper());
        if (orderList.isEmpty()){
            res = Either.left(new ErrorCOrder("There was an error accessing the database", 0));
        } else {
            res = Either.right(orderList);
        }
        return res;
    }

    public Either<ErrorCOrder, Order> get(int id){
        Either<ErrorCOrder, Order> res;
        JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
        List<Order> orderList = jtm.query(SQLqueries.SELECT_ORDERS_ID, new OrderMapper(), id);
        if (orderList.isEmpty()){
            res = Either.left(new ErrorCOrder("There was an error accessing the database", 0));
        } else {
            res = Either.right(orderList.get(0));
        }
        return res;
    }
}

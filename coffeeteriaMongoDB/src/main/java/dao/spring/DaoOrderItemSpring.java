package dao.spring;

import common.SQLqueries;
import dao.ConstantsDao;
import dao.connection.DbConnectionPool;
import dao.mappers.OrderItemMapper;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.OrderItem;
import model.errors.ErrorCOrderItem;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Log4j2
public class DaoOrderItemSpring {


    private final DbConnectionPool pool;

    @Inject
    public DaoOrderItemSpring(DbConnectionPool pool) {
        this.pool = pool;
    }

    public Either<ErrorCOrderItem, List<OrderItem>> getAll(){
        Either<ErrorCOrderItem, List<OrderItem>> res;
        JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
        List<OrderItem> orderItemList = jtm.query(SQLqueries.SELECT_FROM_ORDER_ITEMS_JOIN, new OrderItemMapper());
        if (orderItemList.isEmpty()){
            res = Either.left(new ErrorCOrderItem(ConstantsDao.ERROR_ACCESSING_DB, 0));
        }else {
            res = Either.right(orderItemList);
        }
        return res;
    }

    public Either<ErrorCOrderItem, List<OrderItem>> get (int id){
        Either<ErrorCOrderItem, List<OrderItem>> res;
        JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
        List<OrderItem> orderItemList = jtm.query(SQLqueries.SELECT_FROM_ORDER_ITEMS_ID_JOIN, new OrderItemMapper(), id);
        res = Either.right(orderItemList);
        return res;
    }

}

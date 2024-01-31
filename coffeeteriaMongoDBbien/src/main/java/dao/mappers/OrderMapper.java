package dao.mappers;

import model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setIdOrd(rs.getInt("order_id"));
        order.setOrDate(rs.getDate("order_date").toLocalDate().atStartOfDay());
        order.setIdCo(rs.getInt("customer_id"));
        order.setIdTable(rs.getInt("table_id"));
        return order;
    }
}

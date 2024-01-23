package dao.mappers;

import model.MenuItem;
import model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        MenuItem menuItem =new MenuItem(rs.getInt("menu_item_id"), rs.getString("name"), rs.getString("description"), rs.getFloat("price"));
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getInt("order_item_id"));
        //orderItem.setOrderId(rs.getInt("order_id"));
        //orderItem.setMenuItem(rs.getInt("menu_item_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setMenuItemObject(menuItem);
        return orderItem;
    }
}

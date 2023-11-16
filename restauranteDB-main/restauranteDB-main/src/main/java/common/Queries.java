package common;

public class Queries {
    //Customers
    public static final String GETALL_customers_QUERY="select*from customers";
    public static final String SELECT_customer_QUERY ="select * from customers where id= ?";
    public static final String UPDATE_customer_QUERY ="UPDATE customers SET first_name = ?, last_name = ?,email = ?,phone = ?,date_of_birth = ? WHERE id = ?";
    public static final String DELETE_customer_QUERY = "DELETE FROM customers where id= ?";
    public static final String DELETE_order_QUERY = "DELETE FROM orders where customer_id= ?";
    public static final String DELETE_credentials_QUERY="DELETE FROM credentials where customer_id=?";
    public static final String DELETE_orderItem_QUERY = "DELETE FROM order_items WHERE order_id IN (SELECT order_id FROM orders WHERE customer_id = ?);";
    public static final String SAVE_customer_QUERY= "INSERT INTO customers (id,first_name, last_name,date_of_birth, email, phone) VALUES (?,?,?,?,?,?)";

    //Oders
    public static final String GETALL_orders_QUERY="select*from orders";
    public static final String GETALL_oneCustomers_orders_QUERY="select*from orders where customer_id=?";
    public static final String SELECT_order_QUERY ="select * from orders where order_id= ?";
    public static final String UPDATE_order_QUERY ="UPDATE orders SET order_date = ?,customer_id = ?,table_id = ? WHERE order_id = ?";
    public static final String DELETE_orderOrder_QUERY = "DELETE FROM orders where order_id= ?";
    public static final String SAVE_order_QUERY= "INSERT INTO orders (order_date,customer_id, table_id) VALUES (?,?,?)";
    public static final String DELETE_orderItemOrder_QUERY = "DELETE FROM order_items WHERE order_id IN (SELECT order_id FROM orders WHERE order_id=?)";

    //MenuItems
    public static final String GETALL_menuItems_QUERY="select*from menu_items";
    public static final String SELECT_menuItem_QUERY="select * from menu_items where menu_item_id= ?";
    public static final String GETALLALL_orderItems_QUERY="select*from order_items";
    public static final String DELETEBYORDER_orderItem_QUERY="DELETE FROM order_items WHERE order_id= ?";
    public static final String GETALL_orderItems_QUERY="select*from order_items where order_id= ?";
    public static final String SELECT_orderIems_QUERY="select * from order_items where order_item_id= ?";
    public static final String UPDATE_orderItem_QUERY="UPDATE order_items SET menu_item_id = ?,quantity = ? WHERE order_item_id = ?";
    public static final String SAVE_orderItem_QUERY="INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (?,?,?)";
    public static final String SAVE_credentials_QUERY= "INSERT INTO credentials (customer_id,user_name, password) VALUES (?,?,?)";
    public static final String GETALL_credentials_QUERY="select * from credentials";
    public static final String SELECT_credentials_QUERY="select * from credentials where customer_id= ?";
}
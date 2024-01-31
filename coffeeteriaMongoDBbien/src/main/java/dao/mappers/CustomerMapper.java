package dao.mappers;

import model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer =new Customer();
        customer.setIdC(rs.getInt("id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setSecondName(rs.getString("last_name"));
        customer.setEmailCus(rs.getString("email"));
        customer.setPhoneNumber(rs.getInt("phone"));
        customer.setDateBirth(rs.getDate("date_of_birth").toLocalDate());
        return customer;
    }
}

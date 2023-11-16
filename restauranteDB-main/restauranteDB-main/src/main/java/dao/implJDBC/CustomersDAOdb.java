package dao.implJDBC;

import common.Error;
import common.Queries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import model.Customer;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Data
public class CustomersDAOdb implements dao.CustomersDAO {

    private final DBConnection db;
@Inject
    public CustomersDAOdb(DBConnection db) {
    this.db = db;
}

    @Override
    public Either<Error, List<Customer>> getAll() {
        Either<Error,List<Customer>> either;
        try (Connection myConnection = db.getConnection();
            Statement statement = myConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(Queries.GETALL_customers_QUERY);
            either= Either.right(readRS(resultSet));
        } catch (SQLException e) {
            either= Either.left(new Error("no fufa",1, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<Error, Customer> get(int id) {
        Either<Error,Customer> either;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Queries.SELECT_customer_QUERY)) {
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet rs = preparedStatement.executeQuery();
            either= Either.right(readRS(rs).stream().filter(customer -> customer.getId() == id).findFirst().orElse(null));
        } catch (SQLException e) {
            either= Either.left(new Error("no fufa",1, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<Error, Integer> update(Customer c) {
        Either<Error,Integer> either = null;
        int rowsAffected;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Queries.UPDATE_customer_QUERY)) {

            preparedStatement.setInt(6, c.getId());
            preparedStatement.setString(1, c.getFirstName());
            preparedStatement.setString(2, c.getLastName());
            preparedStatement.setDate(5, Date.valueOf(c.getDateOfBirth()));
            preparedStatement.setString(3, c.getEmail());
            preparedStatement.setInt(4, c.getPhoneNumber());
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

    @Override
    public Either<Error, Integer> delete(Customer c, boolean conf) {
        Either<Error,Integer> either = null;
        int rowsAffected;
        if (conf) {
            try (Connection con = db.getConnection()) {
                try(
                     PreparedStatement deleteCustomer = con.prepareStatement(Queries.DELETE_customer_QUERY);
                     PreparedStatement deleteOrderItems = con.prepareStatement(Queries.DELETE_orderItem_QUERY);
                     PreparedStatement deleteOrders = con.prepareStatement(Queries.DELETE_order_QUERY);
                     PreparedStatement deleteCredentials = con.prepareStatement(Queries.DELETE_credentials_QUERY)) {

                    deleteCustomer.setInt(1, c.getId());
                    deleteCredentials.setInt(1, c.getId());
                    deleteOrders.setInt(1, c.getId());
                    deleteOrderItems.setInt(1, c.getId());
                    con.setAutoCommit(false);
                    deleteOrderItems.executeUpdate();
                    deleteOrders.executeUpdate();
                    rowsAffected = deleteCustomer.executeUpdate();
                    deleteCredentials.executeUpdate();
                    con.commit();

                    if (rowsAffected>0){
                    either=Either.right(rowsAffected);
                    }else{
                    either=Either.left( new Error("no se ha borrao por la forein ki", 1451, LocalDate.now()));
                    }
                } catch (SQLException e) {
                    try {
                        con.rollback();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex.getMessage());
                    }
                    if (e.getErrorCode() == 1451) {
                        either=Either.left( new Error("no se ha borrao por la forein ki", 1451, LocalDate.now()));
                    } else {
                        either=Either.left(new Error("no se ha borrao por idk why", 69, LocalDate.now()));
                    }
                } finally {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return either;
    }
    @Override
    public Either<Error, Integer> save(Customer c) {
        Either<Error,Integer> either = null;
        int rowsAffected;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Queries.SAVE_customer_QUERY,
                     Statement.RETURN_GENERATED_KEYS);
             PreparedStatement credentialsStatement = con.prepareStatement(Queries.SAVE_credentials_QUERY)
             ) {
            credentialsStatement.setInt(1,c.getId());
            credentialsStatement.setString(2,c.getFirstName());
            credentialsStatement.setString(3,c.getFirstName().toLowerCase());
            credentialsStatement.executeUpdate();
            preparedStatement.setInt(1, c.getId());
            preparedStatement.setString(2, c.getFirstName());
            preparedStatement.setString(3, c.getLastName());
            preparedStatement.setDate(4, Date.valueOf(c.getDateOfBirth()));
            preparedStatement.setString(5, c.getEmail());
            preparedStatement.setInt(6, c.getPhoneNumber());
            rowsAffected = preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                rs.getInt(1);
            }
            if (rowsAffected>0){
                either=Either.right(rowsAffected);
            }else{
                either=Either.left( new Error("no se ha borrao", 69, LocalDate.now()));
            }
        } catch (SQLException e) {
                either=Either.left( new Error("no se ha borrao", 69, LocalDate.now()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return either;
    }

    private List<Customer> readRS(ResultSet resultSet) throws SQLException {
        List<Customer> customerList;
        customerList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            LocalDate dateOfBirth = resultSet.getDate("date_of_birth").toLocalDate();
            String email = resultSet.getString("email");
            int phoneNumber = resultSet.getInt("phone");
            customerList.add(new Customer(id, firstName, lastName, dateOfBirth, email, phoneNumber));
        }
        return customerList;
    }
}
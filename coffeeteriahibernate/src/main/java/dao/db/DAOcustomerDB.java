package dao.db;

import common.Configuration;
import common.SQLqueries;
import dao.ConstantsDAO;
import dao.connection.DBConnection;
import dao.connection.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Credential;
import model.Customer;
import model.errors.ErrorCCustomer;
import services.SERVcredential;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DAOcustomerDB {


    private final Configuration config;
    private final DBConnection db;
    private final DBConnectionPool pool;
    //private final SERVcredential serv;
    private final DAOcredentials daOcredentials;

    @Inject
    public DAOcustomerDB(Configuration config, DBConnection db, DBConnectionPool pool, SERVcredential serv, DAOcredentials daOcredentials) {
        this.config = config;
        this.db = db;
        this.pool = pool;
        //this.serv = serv;
        this.daOcredentials = daOcredentials;
    }

    public Either<ErrorCCustomer, List<Customer>> getAll() {
        List<Customer> customerList = new ArrayList<>();
        Either<ErrorCCustomer, List<Customer>> res;
        try (Connection connection = pool.getConnection()){
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(SQLqueries.SELECT_FROM_CUSTOMERS);
            customerList = readRS(rs);
            res = Either.right(customerList);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCCustomer(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCCustomer, Customer> get(int id) {
        Either<ErrorCCustomer, Customer> res;
        try (Connection myConnection = pool.getConnection();
             PreparedStatement stmt = myConnection.prepareStatement(SQLqueries.SELECT_CUSTOMERS_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            List<Customer> customerList = readRS(rs);
            if (!customerList.isEmpty()) {
                res = Either.right(customerList.get(0));
            } else {
                res = Either.left(new ErrorCCustomer(ConstantsDAO.ERROR_READING_DATABASE, 0));
            }
            rs.close();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCCustomer(e.getMessage(), 0));
        }
        return res;
    }


    public Either<ErrorCCustomer, Integer> update(Customer customer) {
        int rowsAffected;
        Either<ErrorCCustomer, Integer> res;
        try (Connection connection = pool.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement(SQLqueries.UPDATE_CUSTOMERS);
            connection.setAutoCommit(false);
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getSecondName());
            pstmt.setString(3, customer.getEmailCus());
            pstmt.setInt(4, customer.getPhoneNumber());
            pstmt.setDate(5, Date.valueOf(customer.getDateBirth()));
            pstmt.setInt(6, customer.getIdC());
            rowsAffected = pstmt.executeUpdate();
            connection.commit();
            res = Either.right(rowsAffected);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCCustomer(e.getMessage(), 0));
        }
        return res;
    }

    public Either<ErrorCCustomer, Integer> delConf(int id, boolean confirmed) {
        return delete(id, confirmed);
    }

    public Either<ErrorCCustomer, Integer> delete(int id, boolean confirmed) {
        Either<ErrorCCustomer, Integer> res;
        if (!confirmed) {
            try (Connection connection = db.getConnection()) {
                PreparedStatement pstmtOrders = connection.prepareStatement(SQLqueries.DELETE_ORDERS);
                pstmtOrders.setInt(1, id);
                pstmtOrders.executeUpdate();
                PreparedStatement pstmtCredentials = connection.prepareStatement(SQLqueries.DELETE_CREDENTIALS);
                pstmtCredentials.setInt(1, id);
                pstmtCredentials.executeUpdate();
                PreparedStatement pstmtCustomer = connection.prepareStatement(SQLqueries.DELETE_CUSTOMERS);
                pstmtCustomer.setInt(1, id);
                int rowsAffected = pstmtCustomer.executeUpdate();
                if (rowsAffected != 1) {
                    res = Either.left(new ErrorCCustomer(ConstantsDAO.ERROR_DELETING_CUSTOMER, 0));
                } else {
                    res = Either.right(rowsAffected);
                }
                res = Either.right(rowsAffected);
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                if (e.getErrorCode() == 1451) {
                    res = Either.left(new ErrorCCustomer(e.getMessage(), e.getErrorCode()));
                } else {
                    res = Either.left(new ErrorCCustomer(e.getMessage(), e.getErrorCode()));
                }
            }
        } else {
            res = Either.left(new ErrorCCustomer(ConstantsDAO.ERROR_DELETING_CUSTOMER, 0));
        }
        return res;
    }

    public Either<ErrorCCustomer, Integer> add(Customer customer, Credential credential) {
        int rowsAffected;
        Either<ErrorCCustomer, Integer> res;
        try (Connection myConnection = pool.getConnection()) {
            myConnection.setAutoCommit(false);

            PreparedStatement pstmtCustomer = myConnection.prepareStatement(SQLqueries.INSERT_CUSTOMER, Statement.RETURN_GENERATED_KEYS);
            pstmtCustomer.setString(1, customer.getFirstName());
            pstmtCustomer.setString(2, customer.getSecondName());
            pstmtCustomer.setString(3, customer.getEmailCus());
            pstmtCustomer.setInt(4, customer.getPhoneNumber());
            if (customer.getDateBirth() != null) {
                pstmtCustomer.setObject(5, customer.getDateBirth());
            } else {
                pstmtCustomer.setNull(5, Types.DATE);
            }
            rowsAffected = pstmtCustomer.executeUpdate();

            if (rowsAffected != 1) {
                myConnection.rollback();
                return Either.left(new ErrorCCustomer(ConstantsDAO.ERROR_ADDDING_CUSTOMER, 0));
            }

            ResultSet generatedKeys = pstmtCustomer.getGeneratedKeys();
            int generatedCustomerId;
            if (generatedKeys.next()) {
                generatedCustomerId = generatedKeys.getInt(1);
            } else {
                myConnection.rollback();
                return Either.left(new ErrorCCustomer(ConstantsDAO.ERROR_OBTAINING_ID, 0));
            }

            PreparedStatement pstmtCredential = myConnection.prepareStatement(SQLqueries.INSERT_CREDENTIAL, Statement.RETURN_GENERATED_KEYS);
            pstmtCredential.setString(1, credential.getUserName());
            pstmtCredential.setString(2, credential.getPassword());
            pstmtCredential.setInt(3, generatedCustomerId);

            rowsAffected = pstmtCredential.executeUpdate();

            if (rowsAffected != 1) {
                myConnection.rollback();
                return Either.left(new ErrorCCustomer("There was an error adding the credential", 0));
            }

            ResultSet generatedCredentialKeys = pstmtCredential.getGeneratedKeys();
            int generatedCredentialId;
            if (generatedCredentialKeys.next()) {
                generatedCredentialId = generatedCredentialKeys.getInt(1);
            } else {
                myConnection.rollback();
                return Either.left(new ErrorCCustomer("There was an error when obtaining the ID from the credential", 0));
            }

            pstmtCustomer.setInt(1, generatedCustomerId);
            pstmtCredential.setInt(2, generatedCredentialId);
            rowsAffected = pstmtCredential.executeUpdate();

            if (rowsAffected != 1) {
                myConnection.rollback();
                return Either.left(new ErrorCCustomer("Error", 0));
            }

            myConnection.commit();
            res = Either.right(generatedCustomerId);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new ErrorCCustomer(e.getMessage(), 0));
        }
        return res;
    }

    private List<Customer> readRS(ResultSet rs) throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(ConstantsDAO.ID);
            String firstName = rs.getString(ConstantsDAO.FIRST_NAME);
            String lastName = rs.getString(ConstantsDAO.LAST_NAME);
            String email = rs.getString(ConstantsDAO.EMAIL);
            int phone = rs.getInt(ConstantsDAO.PHONE);
            LocalDate date = null;
            Date dateFromDB = rs.getDate(ConstantsDAO.DATE_OF_BIRTH);
            if (dateFromDB != null) {
                date = dateFromDB.toLocalDate();
            }
            customerList.add(new Customer(id, firstName, lastName, email, phone, date, daOcredentials.get(id).get()));
        }
        return customerList;
    }
}

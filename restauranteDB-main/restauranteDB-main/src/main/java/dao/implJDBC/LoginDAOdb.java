package dao.implJDBC;

import common.Error;
import common.Queries;
import dao.LoginDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Credentials;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoginDAOdb implements LoginDAO {
    private final DBConnection db;
    @Inject
    public LoginDAOdb(DBConnection db) {
        this.db = db;
    }


    @Override
    public Either<Error,List<Credentials>> getAll() {
        Either<Error,List<Credentials>> either;
        try (Connection myConnection = db.getConnection();
             Statement statement = myConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(Queries.GETALL_credentials_QUERY);
            either= Either.right(readRS(resultSet));
        } catch (SQLException e) {
            either= Either.left(new Error("no fufa",1, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<Error,Credentials>  get(int id) {
        Either<Error,Credentials> either;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Queries.SELECT_credentials_QUERY)) {
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet rs = preparedStatement.executeQuery();
            either= Either.right(readRS(rs).stream().filter(credentials -> credentials.getId() == id).findFirst().orElse(null));
        } catch (SQLException e) {
            either= Either.left(new Error("no fufa",1, LocalDate.now()));
        }
        return either;
    }
    private List<Credentials> readRS(ResultSet resultSet) throws SQLException {
        List<Credentials> credentialsList= new ArrayList<>();
        while (resultSet.next()) {
            int id=resultSet.getInt("customer_id");
            String username = resultSet.getString("user_name");
            String password = resultSet.getString("password");
            credentialsList.add(new Credentials(username, password,id));
        }
        return credentialsList;
    }
}

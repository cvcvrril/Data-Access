package dao.connection;

import common.Configuration;
import dao.ConstantsDAO;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private Configuration config;

    @Inject
    public DBConnection(Configuration config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager
                .getConnection(config.getPropertySQL(ConstantsDAO.PATH_DB), config.getPropertySQL(ConstantsDAO.USER_DB), config.getPropertySQL(ConstantsDAO.PASS_DB));
    }

}

package dao.connection;

import common.Configuration;
import dao.ConstantsDao;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private Configuration config;

    @Inject
    public DbConnection(Configuration config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager
                .getConnection(config.getPropertySQL(ConstantsDao.PATH_DB), config.getPropertySQL(ConstantsDao.USER_DB), config.getPropertySQL(ConstantsDao.PASS_DB));
    }

}
